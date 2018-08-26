package de.boettcher.storage.profile

import de.boettcher.storage.interactor.auth.IGetUserTokenInteractor
import de.boettcher.storage.interactor.auth.IRemoveUserTokenInteractor
import de.boettcher.storage.interactor.auth.ISaveUserTokenInteractor
import de.boettcher.storage.store.BaseStore
import de.boettcher.storage.utils.TextUtils
import io.reactivex.Observable
import javax.inject.Inject

class ProfileStore @Inject constructor(
    private val getUserTokenInteractor: IGetUserTokenInteractor,
    private val saveUserTokenInteractor: ISaveUserTokenInteractor,
    private val removeUserTokenInteractor: IRemoveUserTokenInteractor
) : BaseStore<ProfileState>(ProfileState.LoggedOut), IProfileStore {

    override fun initialize() {
        val initState = object : RecreateStateFunction<ProfileState> {

            override fun recreate(stateProvider: StateProvider<ProfileState>): Observable<ProfileState> {
                return Observable.fromCallable<ProfileState> {
                    val userId = getUserTokenInteractor.getUserToken().blockingGet()
                    when (userId) {
                        null, TextUtils.EMPTY -> ProfileState.LoggedOut
                        else -> ProfileState.LoggedIn(userId)
                    }
                }
            }

        }

        buildState(initState, getErrorState())
    }

    override fun login(userId: String) {
        val loginState = getLoginState(userId)
        buildState(loginState, getErrorState())
    }

    private fun getLoginState(userId: String): RecreateStateFunction<ProfileState> {
        return object : RecreateStateFunction<ProfileState> {

            override fun recreate(stateProvider: StateProvider<ProfileState>): Observable<ProfileState> {
                return Observable.fromCallable<ProfileState> {
                    saveUserTokenInteractor.saveUserToken(userId).blockingAwait()
                    ProfileState.LoggedIn(userId)
                }
            }

        }
    }

    private fun getErrorState(): RecreateStateFunctionOnError<ProfileState> {
        return object : RecreateStateFunctionOnError<ProfileState> {

            override fun recreate(
                stateProvider: StateProvider<ProfileState>,
                throwable: Throwable
            ): Observable<ProfileState> {
                return Observable.just(ProfileState.LoggedOut)
            }

        }
    }

    override fun logout() {
        val logoutState = getLogoutState()
        buildState(logoutState, getErrorState())
    }

    private fun getLogoutState(): RecreateStateFunction<ProfileState> {
        return object : RecreateStateFunction<ProfileState> {

            override fun recreate(stateProvider: StateProvider<ProfileState>): Observable<ProfileState> {
                return Observable.fromCallable<ProfileState> {
                    removeUserTokenInteractor.removeUserToken().blockingAwait()
                    ProfileState.LoggedOut
                }
            }

        }
    }
}
