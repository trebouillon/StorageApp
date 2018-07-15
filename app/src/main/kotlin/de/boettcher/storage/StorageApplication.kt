package de.boettcher.storage

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import de.boettcher.storage.di.DaggerApplicationComponent

class StorageApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

}
