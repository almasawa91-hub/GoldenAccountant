package com.goldenaccountant.app.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao interface AccountDao{@Query("SELECT * FROM accounts WHERE active=1 ORDER BY name") fun observeAll():Flow<List<Account>>;@Insert suspend fun insert(a:Account);@Query("SELECT COUNT(*) FROM accounts") suspend fun count():Int}
@Dao interface JournalDao{@Insert suspend fun insertEntry(e:JournalEntry);@Insert suspend fun insertLines(l:List<JournalEntryLine>)}
@Dao interface ProductDao{@Query("SELECT * FROM products WHERE active=1 ORDER BY name") fun observeAll():Flow<List<Product>>;@Insert suspend fun insert(p:Product)}
@Dao interface CompanyDao{@Query("SELECT * FROM company_settings WHERE id=1 LIMIT 1") fun observe():Flow<CompanySettings?>;@Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun upsert(s:CompanySettings)}