package storage.boettcher.de.storageapp.api

import java.net.Socket
import java.nio.charset.Charset

class StorageClient : IStorageClient {

  companion object {
    private const val INET_ADDRESS = "192.168.179.28"
    private const val INET_PORT = 12345
  }

  override fun send(barcode: String): String {
    val client = Socket(INET_ADDRESS, INET_PORT)
    val input = client.getInputStream()
    val output = client.getOutputStream()

    try {
      output.bufferedWriter(Charset.forName("UTF-8")).use {
        it.write(barcode)
        it.flush()
        return input.bufferedReader(Charset.forName("UTF-8")).readLine()
      }
    } catch (e: Exception) {
      return "error"
    } finally {
      output.close()
      input.close()
      client.close()
    }
  }

}
