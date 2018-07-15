package de.boettcher.storage.repository

import de.boettcher.storage.model.BarcodeData

interface IBarcodeRepository {
 
  fun send(barcodeData: BarcodeData)
  
}
