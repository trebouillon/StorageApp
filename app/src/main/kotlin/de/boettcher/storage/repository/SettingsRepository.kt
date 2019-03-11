package de.boettcher.storage.repository

import de.boettcher.storage.interactor.settings.Endpoint
import de.boettcher.storage.persistence.IPersistenceService
import de.boettcher.storage.utils.TextUtils
import javax.inject.Inject

interface ISettingsRepository {

    val endpoint: Endpoint

    fun setEndpoint(endpoint: Endpoint)

    companion object {
        const val KEY_ENDPOINT = "key.endpoint"
    }

}

class SettingsRepository @Inject constructor(private val persistenceService: IPersistenceService) :
    ISettingsRepository {

    override val endpoint =
        persistenceService.get(ISettingsRepository.KEY_ENDPOINT) ?: TextUtils.EMPTY

    override fun setEndpoint(endpoint: Endpoint) =
        persistenceService.put(ISettingsRepository.KEY_ENDPOINT, endpoint)

}
