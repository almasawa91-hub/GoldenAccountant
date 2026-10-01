package com.goldenaccountant.app.di
import android.content.Context
import androidx.room.Room
import com.goldenaccountant.app.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
 @Provides @Singleton fun database(@ApplicationContext context:Context):AppDatabase=Room.databaseBuilder(context,AppDatabase::class.java,"golden_accountant.db").addMigrations(AppDatabase.MIGRATION_1_2).build()
 @Provides fun accountDao(db:AppDatabase)=db.accounts()
 @Provides fun categoryDao(db:AppDatabase)=db.categories()
 @Provides fun productDao(db:AppDatabase)=db.products()
 @Provides fun journalDao(db:AppDatabase)=db.journals()
 @Provides fun invoiceDao(db:AppDatabase)=db.invoices()
 @Provides fun voucherDao(db:AppDatabase)=db.vouchers()
 @Provides fun companyDao(db:AppDatabase)=db.company()
 @Provides fun auditDao(db:AppDatabase)=db.audit()
}
