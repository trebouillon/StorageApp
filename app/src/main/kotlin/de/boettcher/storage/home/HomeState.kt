package de.boettcher.storage.home

import de.boettcher.storage.model.Barcode
import de.boettcher.storage.model.ScanType
import de.boettcher.storage.store.IState

sealed class HomeState : IState {

    object Default : HomeState()

    object Error : HomeState()

    data class Scan(val barcodes: List<Barcode>, val scanType: ScanType) : HomeState()

}
