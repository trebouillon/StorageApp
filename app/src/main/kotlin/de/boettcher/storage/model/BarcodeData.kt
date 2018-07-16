package de.boettcher.storage.model

data class BarcodeData(
    val scanType: ScanType,
    val userId: String? = null,
    val barcodes: List<Barcode>
)

enum class ScanType {
    TAKE, PUT
}
