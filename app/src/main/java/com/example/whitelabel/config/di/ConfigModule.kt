package com.example.whitelabel.config.di

import com.example.whitelabel.presentation.ui.viewmodel.config.ConfigImpl
import com.example.whitelabel.config.Config
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ConfigModule {

    @Binds
    fun bindConfig(config: ConfigImpl): Config
}