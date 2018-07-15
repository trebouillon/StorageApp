package de.boettcher.storage.di

import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.Multibinds

@Module
abstract class AndroidInjectionModule {

    @Multibinds
    internal abstract fun activityInjectorFactories(): Map<Class<out Activity>, AndroidInjector.Factory<out Activity>>

    @Multibinds
    internal abstract fun fragmentInjectorFactories(): Map<Class<out Fragment>, AndroidInjector.Factory<out Fragment>>

    @Multibinds
    internal abstract fun supportFragmentInjectorFactories(): Map<Class<out android.support.v4.app.Fragment>, AndroidInjector.Factory<out android.support.v4.app.Fragment>>

    @Multibinds
    internal abstract fun serviceInjectorFactories(): Map<Class<out Service>, AndroidInjector.Factory<out Service>>

    @Multibinds
    internal abstract fun broadcastReceiverInjectorFactories(): Map<Class<out BroadcastReceiver>, AndroidInjector.Factory<out BroadcastReceiver>>

    @Multibinds
    internal abstract fun contentProviderInjectorFactories(): Map<Class<out ContentProvider>, AndroidInjector.Factory<out ContentProvider>>

}
