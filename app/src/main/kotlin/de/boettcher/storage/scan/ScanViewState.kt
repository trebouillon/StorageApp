package de.boettcher.storage.scan

sealed class ScanViewState {

    object None : ScanViewState()
    object Loading : ScanViewState()
    object TokenSaved : ScanViewState()
    object NoToken : ScanViewState()
    data class Error(val message: String) : ScanViewState()
    data class Success(val result: String) : ScanViewState()

}
