package de.boettcher.storage.interactor.barcode

import de.boettcher.storage.model.Barcode
import de.boettcher.storage.model.ScanType

data class BarcodeData(val scanType: ScanType, val barcodes: List<Barcode>, val userId: String)
