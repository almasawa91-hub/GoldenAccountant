package com.goldenaccountant.app.data

import androidx.room.*

@Database(entities=[CompanySettings::class,Account::class,AccountCategory::class,AccountCategoryLink::class,AccountTransaction::class,JournalEntry::class,JournalEntryLine::class,Unit::class,Product::class,ProductUnit::class,Invoice::class,InvoiceItem::class,Voucher::class,StockTransaction::class,StockAdjustment::class,AuditLog::class],version=2,exportSchema=true)
abstract class AppDatabase:RoomDatabase(){
 abstract fun accounts():AccountDao
 abstract fun categories():CategoryDao
 abstract fun journals():JournalDao
 abstract fun products():ProductDao
 abstract fun invoices():InvoiceDao
 abstract fun vouchers():VoucherDao
 abstract fun company():CompanyDao
 abstract fun audit():AuditDao
 companion object { val MIGRATION_1_2=object:Migration(1,2){override fun migrate(db:SupportSQLiteDatabase){
 db.execSQL("CREATE INDEX IF NOT EXISTS index_accounts_active ON accounts(active)")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_products_active ON products(active)")
 db.execSQL("DROP INDEX IF EXISTS index_journal_entries_referenceType_referenceId")
 db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_journal_entries_referenceType_referenceId ON journal_entries(referenceType,referenceId)")
 db.execSQL("CREATE TABLE IF NOT EXISTS account_categories (id TEXT NOT NULL, name TEXT NOT NULL, sortOrder INTEGER NOT NULL, active INTEGER NOT NULL, PRIMARY KEY(id))")
 db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_account_categories_name ON account_categories(name)")
 db.execSQL("CREATE TABLE IF NOT EXISTS account_category_links (accountId TEXT NOT NULL, categoryId TEXT NOT NULL, PRIMARY KEY(accountId,categoryId))")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_account_category_links_categoryId ON account_category_links(categoryId)")
 db.execSQL("CREATE TABLE IF NOT EXISTS account_transactions (id TEXT NOT NULL, accountId TEXT NOT NULL, date INTEGER NOT NULL, debitMinor INTEGER NOT NULL, creditMinor INTEGER NOT NULL, description TEXT NOT NULL, referenceType TEXT NOT NULL, referenceId TEXT NOT NULL, PRIMARY KEY(id))")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_account_transactions_accountId_date ON account_transactions(accountId,date)")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_account_transactions_referenceType_referenceId ON account_transactions(referenceType,referenceId)")
 db.execSQL("CREATE TABLE IF NOT EXISTS units (id TEXT NOT NULL,name TEXT NOT NULL,symbol TEXT NOT NULL,active INTEGER NOT NULL,PRIMARY KEY(id))")
 db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_units_name ON units(name)")
 db.execSQL("CREATE TABLE IF NOT EXISTS product_units (productId TEXT NOT NULL,unitId TEXT NOT NULL,multiplierMilli INTEGER NOT NULL,PRIMARY KEY(productId,unitId))")
 db.execSQL("CREATE TABLE IF NOT EXISTS invoices (id TEXT NOT NULL,number TEXT NOT NULL,date INTEGER NOT NULL,type TEXT NOT NULL,customerAccountId TEXT,subtotalMinor INTEGER NOT NULL,discountMinor INTEGER NOT NULL,taxMinor INTEGER NOT NULL,totalMinor INTEGER NOT NULL,paidMinor INTEGER NOT NULL,status TEXT NOT NULL,notes TEXT NOT NULL,createdAt INTEGER NOT NULL,PRIMARY KEY(id))")
 db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_invoices_number ON invoices(number)")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_invoices_customerAccountId ON invoices(customerAccountId)")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_invoices_date ON invoices(date)")
 db.execSQL("CREATE TABLE IF NOT EXISTS invoice_items (id TEXT NOT NULL,invoiceId TEXT NOT NULL,productId TEXT NOT NULL,quantityMilli INTEGER NOT NULL,unitPriceMinor INTEGER NOT NULL,discountMinor INTEGER NOT NULL,totalMinor INTEGER NOT NULL,PRIMARY KEY(id))")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_invoice_items_invoiceId ON invoice_items(invoiceId)")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_invoice_items_productId ON invoice_items(productId)")
 db.execSQL("CREATE TABLE IF NOT EXISTS vouchers (id TEXT NOT NULL,number TEXT NOT NULL,type TEXT NOT NULL,date INTEGER NOT NULL,fromAccountId TEXT NOT NULL,toAccountId TEXT NOT NULL,amountMinor INTEGER NOT NULL,description TEXT NOT NULL,status TEXT NOT NULL,createdAt INTEGER NOT NULL,PRIMARY KEY(id))")
 db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_vouchers_number ON vouchers(number)")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_vouchers_date ON vouchers(date)")
 db.execSQL("CREATE TABLE IF NOT EXISTS stock_adjustments (id TEXT NOT NULL,date INTEGER NOT NULL,productId TEXT NOT NULL,quantityMilli INTEGER NOT NULL,unitCostMinor INTEGER NOT NULL,reason TEXT NOT NULL,status TEXT NOT NULL,PRIMARY KEY(id))")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_stock_adjustments_date ON stock_adjustments(date)")
 db.execSQL("CREATE TABLE IF NOT EXISTS audit_logs (id TEXT NOT NULL,date INTEGER NOT NULL,action TEXT NOT NULL,entityType TEXT NOT NULL,entityId TEXT NOT NULL,details TEXT NOT NULL,PRIMARY KEY(id))")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_audit_logs_date ON audit_logs(date)")
 db.execSQL("CREATE INDEX IF NOT EXISTS index_audit_logs_entityType_entityId ON audit_logs(entityType,entityId)")
 }}}}
}
