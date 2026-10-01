package com.goldenaccountant.app.data

import androidx.room.*

@Entity(tableName="company_settings")
data class CompanySettings(@PrimaryKey val id:Int=1,val companyName:String,val phone:String="",val address:String="",val taxNumber:String="",val baseCurrency:String="YER",val initialized:Boolean=true)

@Entity(tableName="accounts",indices=[Index(value=["phone"]),Index(value=["type"]),Index(value=["active"])])
data class Account(@PrimaryKey val id:String,val name:String,val phone:String="",val address:String="",val email:String="",val taxNumber:String="",val notes:String="",val creditLimitMinor:Long=0,val currency:String="YER",val openingBalanceMinor:Long=0,val type:String,val active:Boolean=true,val createdAt:Long,val updatedAt:Long)

@Entity(tableName="account_categories",indices=[Index(value=["name"],unique=true)])
data class AccountCategory(@PrimaryKey val id:String,val name:String,val sortOrder:Int=0,val active:Boolean=true)
@Entity(tableName="account_category_links",primaryKeys=["accountId","categoryId"],indices=[Index("categoryId")])
data class AccountCategoryLink(val accountId:String,val categoryId:String)
@Entity(tableName="account_transactions",indices=[Index(value=["accountId","date"]),Index(value=["referenceType","referenceId"])])
data class AccountTransaction(@PrimaryKey val id:String,val accountId:String,val date:Long,val debitMinor:Long,val creditMinor:Long,val description:String,val referenceType:String,val referenceId:String)

@Entity(tableName="journal_entries",indices=[Index(value=["referenceType","referenceId"],unique=true)])
data class JournalEntry(@PrimaryKey val id:String,val date:Long,val referenceType:String,val referenceId:String,val description:String,val createdAt:Long)
@Entity(tableName="journal_entry_lines",indices=[Index("journalEntryId"),Index("accountId")])
data class JournalEntryLine(@PrimaryKey val id:String,val journalEntryId:String,val accountId:String,val debitMinor:Long,val creditMinor:Long,val description:String="")

@Entity(tableName="units",indices=[Index(value=["name"],unique=true)])
data class Unit(@PrimaryKey val id:String,val name:String,val symbol:String="",val active:Boolean=true)
@Entity(tableName="products",indices=[Index(value=["sku"],unique=true),Index(value=["active"])])
data class Product(@PrimaryKey val id:String,val sku:String,val name:String,val unitId:String="",val unit:String="قطعة",val salePriceMinor:Long=0,val purchasePriceMinor:Long=0,val active:Boolean=true,val createdAt:Long,val updatedAt:Long)
@Entity(tableName="product_units",primaryKeys=["productId","unitId"])
data class ProductUnit(val productId:String,val unitId:String,val multiplierMilli:Long=1000)

@Entity(tableName="invoices",indices=[Index(value=["number"],unique=true),Index("customerAccountId"),Index("date")])
data class Invoice(@PrimaryKey val id:String,val number:String,val type:String,val date:Long,val customerAccountId:String?,val subtotalMinor:Long,val discountMinor:Long,val taxMinor:Long,val totalMinor:Long,val paidMinor:Long,val status:String="POSTED",val notes:String="",val createdAt:Long)
@Entity(tableName="invoice_items",indices=[Index("invoiceId"),Index("productId")])
data class InvoiceItem(@PrimaryKey val id:String,val invoiceId:String,val productId:String,val quantityMilli:Long,val unitPriceMinor:Long,val discountMinor:Long,val totalMinor:Long)

@Entity(tableName="vouchers",indices=[Index(value=["number"],unique=true),Index("date")])
data class Voucher(@PrimaryKey val id:String,val number:String,val type:String,val date:Long,val fromAccountId:String,val toAccountId:String,val amountMinor:Long,val description:String,val status:String="POSTED",val createdAt:Long)
@Entity(tableName="stock_transactions",indices=[Index(value=["productId","date"]),Index(value=["referenceType","referenceId"])])
data class StockTransaction(@PrimaryKey val id:String,val productId:String,val date:Long,val quantityMilli:Long,val unitCostMinor:Long,val type:String,val referenceType:String,val referenceId:String)
@Entity(tableName="stock_adjustments",indices=[Index("date")])
data class StockAdjustment(@PrimaryKey val id:String,val date:Long,val productId:String,val quantityMilli:Long,val unitCostMinor:Long,val reason:String,val status:String="POSTED")
@Entity(tableName="audit_logs",indices=[Index("date"),Index(value=["entityType","entityId"])])
data class AuditLog(@PrimaryKey val id:String,val date:Long,val action:String,val entityType:String,val entityId:String,val details:String)
