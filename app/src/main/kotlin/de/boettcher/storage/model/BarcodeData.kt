package de.boettcher.storage.model

data class BarcodeData(val scanType: ScanType, val barcodes: List<Barcode>, val userId: String)
