package com.ao.e_commerce.data.di

import com.ao.e_commerce.data.data_source.DummyJsonDataSource
import com.ao.e_commerce.data.repository.ProductsRepository
import com.ao.e_commerce.data.repository.UserRepository
import com.ao.e_commerce.retrofit.ApiUtils
import com.ao.e_commerce.retrofit.DummyJsonDao
import com.ao.e_commerce.viewmodels.ProductsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDummyJsonDao(): DummyJsonDao = ApiUtils.getDummyJsonDao()

    @Provides
    @Singleton
    fun provideDummyJsonDataSource(dao: DummyJsonDao) = DummyJsonDataSource(dao)

    @Provides
    @Singleton
    fun provideUserRepository(dao: DummyJsonDao) = UserRepository(dao)

    @Provides
    @Singleton
    fun provideProductsRepository(dataSource: DummyJsonDataSource) = ProductsRepository(dataSource)

    @Provides
    @Singleton
    fun provideProductViewModel(repository: ProductsRepository) = ProductsViewModel(repository)


}