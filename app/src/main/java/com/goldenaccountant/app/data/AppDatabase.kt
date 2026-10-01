package com.goldenaccountant.app.data
import androidx.room.*
@Database(entities=[Account::class,JournalEntry::class,JournalEntryLine::class,Product::class,StockTransaction::class,CompanySettings::class],version=1,exportSchema=true)
abstract class AppDatabase:RoomDatabase(){abstract fun accounts():AccountDao;abstract fun journals():JournalDao;abstract fun products():ProductDao;abstract fun company():CompanyDao}