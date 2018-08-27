package de.boettcher.storage.scan

import de.boettcher.storage.store.IStore

interface IScanStore : IStore<ScanState> {

    fun take()

    fun put()

    fun sendBarcode(barcode: String)

}
