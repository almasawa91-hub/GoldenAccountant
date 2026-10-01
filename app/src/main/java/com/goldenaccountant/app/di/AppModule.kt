package com.goldenaccountant.app.di
import android.content.Context
import androidx.room.Room
import com.goldenaccountant.app.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module @InstallIn(SingletonComponent::class) object AppModule{@Provides @Singleton fun database(@ApplicationContext c:Context):AppDatabase=Room.databaseBuilder(c,AppDatabase::class.java,"golden_accountant.db").build()}