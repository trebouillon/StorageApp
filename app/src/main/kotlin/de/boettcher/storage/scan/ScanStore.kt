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
        buildState(initState(ScanType.TAKE), onScanError())
    }

    private fun initState(scanType: ScanType): RecreateStateFunction<ScanState> {
        return object : RecreateStateFunction<ScanState> {

            override fun recreate(stateProvider: StateProvider<ScanState>): Observable<ScanState> {
                return checkLoggedIn()
            }

            fun checkLoggedIn(): Observable<ScanState> {
                val userId = getUserTokenInteractor.getUserToken().blockingGet()
                return when (userId) {
                    null -> Observable.just(
                        ScanState.Error(ErrorType.AUTHENTICATION_FAILED)
                    )
                    else -> initScanState(userId)
                }
            }

            fun initScanState(userId: String): Observable<ScanState> {
                val initState = when (scanType) {
                    ScanType.TAKE -> ScanState.Take(userId)
                    ScanType.PUT -> ScanState.Put(userId)
                }
                return Observable.just(initState)
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
                return Observable.just(ScanState.Error(ErrorType.GENERAL))
            }
        }
    }

    override fun put() {
        buildState(initState(ScanType.PUT), onScanError())
    }

    override fun sendBarcode(barcode: String) {
        val sendState = getSendBarcodeState(barcode)
        buildState(sendState, onScanError())
    }

    private fun getSendBarcodeState(barcode: String): RecreateStateFunction<ScanState> {
        return object : RecreateStateFunction<ScanState> {

            override fun recreate(stateProvider: StateProvider<ScanState>): Observable<ScanState> {

                val currentState = stateProvider.currentState
                return when (currentState) {
                    is ScanState.Take -> handleTakeScan(barcode, currentState)
                    is ScanState.Put -> handlePutScan(currentState, barcode)
                    else -> Observable.just(ScanState.Finish)
                }

            }

        }
    }

    private fun handlePutScan(currentState: ScanState.Put, barcode: String): Observable<ScanState> {
        val stateBarcode = currentState.barcode
        return when (stateBarcode) {
            null -> Observable.just(currentState.copy(barcode = barcode))
            else -> sendPutScanData(barcode, currentState)
        }
    }

    private fun sendPutScanData(
        barcode: String,
        currentState: ScanState.Put
    ): Observable<ScanState> {
        val barcode1 = Barcode(barcode.resolveStorageType(), barcode)
        val barcode2 = Barcode(
            currentState.barcode!!.resolveStorageType(), currentState.barcode
        )

        val data = BarcodeData(
            scanType = ScanType.PUT,
            barcodes = listOf(barcode1, barcode2),
            userId = currentState.userId
        )
        return sendBarcodeInteractor.send(data).toObservable()
            .map { ScanState.Finish }
    }

    private fun handleTakeScan(
        barcode: String,
        currentState: ScanState.Take
    ): Observable<ScanState> {
        val data = BarcodeData(
            scanType = ScanType.TAKE,
            barcodes = listOf(Barcode(barcode.resolveStorageType(), barcode)),
            userId = currentState.userId
        )
        return sendBarcodeInteractor.send(data).toObservable()
            .map { ScanState.Finish }
    }

}
