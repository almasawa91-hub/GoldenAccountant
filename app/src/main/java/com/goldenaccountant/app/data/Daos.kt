package com.goldenaccountant.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao interface AccountDao {
 @Query("SELECT * FROM accounts WHERE active=1 ORDER BY name") fun observeAll():Flow<List<Account>>
 @Query("SELECT * FROM accounts WHERE id=:id LIMIT 1") suspend fun get(id:String):Account?
 @Insert suspend fun insert(a:Account)
 @Update suspend fun update(a:Account)
 @Query("UPDATE accounts SET active=0,updatedAt=:now WHERE id=:id") suspend fun disable(id:String,now:Long)
 @Query("SELECT COUNT(*) FROM accounts") suspend fun count():Int
 @Query("SELECT COALESCE(SUM(debitMinor-creditMinor),0) FROM account_transactions WHERE accountId=:id") suspend fun transactionBalance(id:String):Long
 @Insert suspend fun insertTransaction(t:AccountTransaction)
 @Query("SELECT * FROM account_transactions WHERE accountId=:id ORDER BY date,id") fun observeTransactions(id:String):Flow<List<AccountTransaction>>
}
@Dao interface CategoryDao {
 @Query("SELECT * FROM account_categories WHERE active=1 ORDER BY sortOrder,name") fun observeAll():Flow<List<AccountCategory>>
 @Insert suspend fun insert(c:AccountCategory)
 @Update suspend fun update(c:AccountCategory)
 @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun link(l:AccountCategoryLink)
 @Query("SELECT categoryId FROM account_category_links WHERE accountId=:accountId") suspend fun categoriesFor(accountId:String):List<String>
 @Query("DELETE FROM account_category_links WHERE accountId=:accountId AND categoryId=:categoryId") suspend fun unlink(accountId:String,categoryId:String)
}
@Dao interface JournalDao {
 @Insert suspend fun insertEntry(e:JournalEntry)
 @Insert suspend fun insertLines(l:List<JournalEntryLine>)
 @Query("SELECT * FROM journal_entries ORDER BY date DESC,createdAt DESC LIMIT :limit") fun recent(limit:Int):Flow<List<JournalEntry>>
 @Query("SELECT COALESCE(SUM(debitMinor),0)-COALESCE(SUM(creditMinor),0) FROM journal_entry_lines WHERE accountId=:accountId") suspend fun balance(accountId:String):Long
}
@Dao interface ProductDao {
 @Query("SELECT * FROM products WHERE active=1 ORDER BY name") fun observeAll():Flow<List<Product>>
 @Query("SELECT * FROM products WHERE id=:id LIMIT 1") suspend fun get(id:String):Product?
 @Insert suspend fun insert(p:Product)
 @Update suspend fun update(p:Product)
 @Query("SELECT COALESCE(SUM(quantityMilli),0) FROM stock_transactions WHERE productId=:productId") suspend fun stock(productId:String):Long
 @Insert suspend fun insertStock(t:StockTransaction)
 @Query("SELECT * FROM stock_transactions WHERE productId=:id ORDER BY date,id") fun observeStock(id:String):Flow<List<StockTransaction>>
}
@Dao interface InvoiceDao {
 @Query("SELECT * FROM invoices ORDER BY date DESC,createdAt DESC") fun observeAll():Flow<List<Invoice>>
 @Insert suspend fun insert(i:Invoice)
 @Insert suspend fun insertItems(items:List<InvoiceItem>)
}
@Dao interface VoucherDao { @Query("SELECT * FROM vouchers ORDER BY date DESC,createdAt DESC") fun observeAll():Flow<List<Voucher>>; @Insert suspend fun insert(v:Voucher) }
@Dao interface CompanyDao { @Query("SELECT * FROM company_settings WHERE id=1 LIMIT 1") fun observe():Flow<CompanySettings?>; @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun upsert(s:CompanySettings) }
@Dao interface AuditDao { @Insert suspend fun insert(log:AuditLog) }
