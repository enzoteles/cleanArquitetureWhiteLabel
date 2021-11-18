package com.example.whitelabel.data.di

import com.example.whitelabel.data.FirebaseProductDataSource
import com.example.whitelabel.data.ProductDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindProductDataSource(dataSource: FirebaseProductDataSource) : ProductDataSource
}