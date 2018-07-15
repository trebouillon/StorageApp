package de.boettcher.storage.repository

import de.boettcher.storage.model.BarcodeData
import io.reactivex.Single

interface IBarcodeRepository {

  fun send(barcodeData: BarcodeData): Single<String>
  
}
