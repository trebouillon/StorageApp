package de.boettcher.storage.scan

import de.boettcher.storage.interactor.auth.IGetUserTokenInteractor
import de.boettcher.storage.interactor.barcode.BarcodeData
import de.boettcher.storage.interactor.barcode.ISendBarcodeInteractor
import de.boettcher.storage.model.Barcode
import de.boettcher.storage.model.ErrorType
import de.boettcher.storage.model.ScanType
import de.boettcher.storage.store.BaseStore
import de.boettcher.storage.utils.resolveStorageType
import io.reactivex.Observable
import javax.inject.Inject

class ScanStore @Inject constructor(
    private val getUserTokenInteractor: IGetUserTokenInteractor,
    private val sendBarcodeInteractor: ISendBarcodeInteractor
) :
    BaseStore<ScanState>(ScanState.Idle), IScanStore {

    override fun take() {
        buildState(initState(), onScanError())
    }

    private fun initState(): RecreateStateFunction<ScanState> {
        return object : RecreateStateFunction<ScanState> {

            override fun recreate(stateProvider: StateProvider<ScanState>): Observable<ScanState> {
                return checkLoggedIn()
            }

            fun checkLoggedIn(): Observable<ScanState> {
                val userId = getUserTokenInteractor.getUserToken().blockingGet()
                return when (userId) {
                    null -> Observable.just(
                        ScanState.Error(
                            ErrorType.AUTHENTICATION_FAILED,
                            false
                        )
                    )
                    else -> Observable.just(ScanState.Take(userId))
                }
            }

        }
    }

    private fun onScanError(): RecreateStateFunctionOnError<ScanState> {
        return object : RecreateStateFunctionOnError<ScanState> {
            override fun recreate(
                stateProvider: StateProvider<ScanState>,
                throwable: Throwable
            ): Observable<ScanState> {
                // todo check throwable
                return Observable.just(ScanState.Error(ErrorType.GENERAL, false))
            }
        }
    }

    override fun put() {
        buildState(initState(), onScanError())
    }

    override fun sendBarcode(barcode: String) {

        val sendState = object : RecreateStateFunction<ScanState> {

            override fun recreate(stateProvider: StateProvider<ScanState>): Observable<ScanState> {

                val currentState = stateProvider.currentState
                return when (currentState) {
                    is ScanState.Take -> {
                        val data = BarcodeData(
                            ScanType.TAKE,
                            listOf(Barcode(barcode.resolveStorageType(), barcode)),
                            currentState.userId
                        )
                        sendBarcodeInteractor.send(data).toObservable().map { ScanState.Finish }
                    }
                    else -> Observable.just(ScanState.Finish)
                }

            }

        }

        buildState(sendState, onScanError())

    }

}
