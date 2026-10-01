package com.goldenaccountant.app.di

import android.content.Context
import androidx.room.Room
import com.goldenaccountant.app.data.AccountDao
import com.goldenaccountant.app.data.AppDatabase
import com.goldenaccountant.app.data.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "golden_accountant.db").build()

    @Provides
    fun accountDao(database: AppDatabase): AccountDao = database.accounts()

    @Provides
    fun productDao(database: AppDatabase): ProductDao = database.products()
}
