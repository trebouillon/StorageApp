package de.boettcher.storage.api

import java.net.Socket
import java.nio.charset.Charset
import javax.inject.Inject

class StorageClient @Inject constructor() : IStorageClient {

    private val charset = Charset.forName("UTF-8")

    override fun send(payload: String): String {
        val client = Socket(
            Endpoints.STORAGE_SERVICE_ADDRESS, Endpoints.STORAGE_SERVICE_PORT
        )

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
