package de.boettcher.storage.interactor.settings

import de.boettcher.storage.repository.ISettingsRepository
import javax.inject.Inject

typealias Endpoint = String

interface IUpdateEndpointInteractor {

    fun getEndpoint(): String

    fun update(endpoint: Endpoint)

}

class UpdateEndpointInteractor @Inject constructor(private val settingsRepository: ISettingsRepository) :
    IUpdateEndpointInteractor {

    override fun getEndpoint(): String = settingsRepository.endpoint

    override fun update(endpoint: Endpoint) = when {
        endpoint.isBlank() -> settingsRepository.removeEndpoint()
        else -> settingsRepository.setEndpoint(endpoint)
    }

}
