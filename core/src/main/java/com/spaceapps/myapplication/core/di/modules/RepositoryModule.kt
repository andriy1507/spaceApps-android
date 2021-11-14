package com.spaceapps.myapplication.core.di.modules

import com.spaceapps.myapplication.core.di.CoreComponent
import com.spaceapps.myapplication.core.repositories.auth.AuthRepository
import com.spaceapps.myapplication.core.repositories.auth.AuthRepositoryImpl
import com.spaceapps.myapplication.core.repositories.devices.DevicesRepository
import com.spaceapps.myapplication.core.repositories.devices.DevicesRepositoryImpl
import com.spaceapps.myapplication.core.repositories.files.FilesRepository
import com.spaceapps.myapplication.core.repositories.files.FilesRepositoryImpl
import com.spaceapps.myapplication.core.repositories.locations.LocationsRepository
import com.spaceapps.myapplication.core.repositories.locations.LocationsRepositoryImpl
import com.spaceapps.myapplication.core.repositories.notifications.NotificationsRepository
import com.spaceapps.myapplication.core.repositories.notifications.NotificationsRepositoryImpl
import com.spaceapps.myapplication.core.repositories.signalr.SignalrRepository
import com.spaceapps.myapplication.core.repositories.signalr.SignalrRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn(CoreComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindLocationsRepository(impl: LocationsRepositoryImpl): LocationsRepository

    @Binds
    fun bindNotificationsRepository(impl: NotificationsRepositoryImpl): NotificationsRepository

    @Binds
    fun bindDevicesRepository(impl: DevicesRepositoryImpl): DevicesRepository

    @Binds
    fun bindSignalrRepository(impl: SignalrRepositoryImpl): SignalrRepository

    @Binds
    fun bindFilesRepository(impl: FilesRepositoryImpl): FilesRepository
}
