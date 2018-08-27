package de.boettcher.storage.scan

import de.boettcher.storage.model.ErrorType
import de.boettcher.storage.store.IState

sealed class ScanState : IState {

    object Idle : ScanState()

    object Finish : ScanState()

    data class Take(val userId: String) : ScanState()

    data class Put(val userId: String, val barcode: String? = null) : ScanState()

    data class Error(val errorType: ErrorType, val consumed: Boolean) : ScanState()

}
