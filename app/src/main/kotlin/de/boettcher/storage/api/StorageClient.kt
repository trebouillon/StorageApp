package de.boettcher.storage.api

import de.boettcher.storage.persistence.IPersistenceService
import de.boettcher.storage.repository.ISettingsRepository
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.charset.Charset
import javax.inject.Inject

class StorageClient @Inject constructor(private val persistenceService: IPersistenceService) :
    IStorageClient {

    private val charset = Charset.forName("UTF-8")

    override fun send(payload: String): String {
        val address = persistenceService.get(ISettingsRepository.KEY_ENDPOINT)
            ?: Endpoints.STORAGE_SERVICE_ADDRESS

        val socketAddress = InetSocketAddress(address, Endpoints.STORAGE_SERVICE_PORT)

        val client = Socket()
        client.connect(socketAddress, Endpoints.TIMEOUT)

        val input = client.getInputStream()
        val output = client.getOutputStream()

        try {
            output.bufferedWriter(charset).use {
                it.write(payload)
                it.flush()
                return input.bufferedReader(charset).readLine()
            }
        } finally {
            output.close()
            input.close()
            client.close()
        }
    }

}
