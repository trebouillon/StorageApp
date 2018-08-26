package de.boettcher.storage.home

import de.boettcher.storage.model.Barcode
import de.boettcher.storage.model.ScanType
import de.boettcher.storage.store.IState

sealed class HomeState : IState {

    object Default : HomeState()

    data class Error(val type: ErrorType) : HomeState()

    data class Scan(val barcodes: List<Barcode>, val scanType: ScanType) : HomeState()

}

enum class ErrorType {
    NO_CONNECTION, GENERAL, AUTHENTICATION_FAILED
}
