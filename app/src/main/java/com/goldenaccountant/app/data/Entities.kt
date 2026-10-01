package com.goldenaccountant.app.data
import androidx.room.*
@Entity(tableName="accounts",indices=[Index(value=["phone"]),Index(value=["type"])])
data class Account(@PrimaryKey val id:String,val name:String,val phone:String="",val address:String="",val email:String="",val taxNumber:String="",val notes:String="",val creditLimitMinor:Long=0,val currency:String="YER",val openingBalanceMinor:Long=0,val type:String,val active:Boolean=true,val createdAt:Long,val updatedAt:Long)
@Entity(tableName="journal_entries",indices=[Index(value=["referenceType","referenceId"])])
data class JournalEntry(@PrimaryKey val id:String,val date:Long,val referenceType:String,val referenceId:String,val description:String,val createdAt:Long)
@Entity(tableName="journal_entry_lines",indices=[Index(value=["journalEntryId"]),Index(value=["accountId"])])
data class JournalEntryLine(@PrimaryKey val id:String,val journalEntryId:String,val accountId:String,val debitMinor:Long,val creditMinor:Long,val description:String="")
@Entity(tableName="products",indices=[Index(value=["sku"],unique=true)])
data class Product(@PrimaryKey val id:String,val sku:String,val name:String,val unit:String="قطعة",val salePriceMinor:Long=0,val purchasePriceMinor:Long=0,val active:Boolean=true,val createdAt:Long,val updatedAt:Long)
@Entity(tableName="stock_transactions",indices=[Index(value=["productId","date"])])
data class StockTransaction(@PrimaryKey val id:String,val productId:String,val date:Long,val quantityMilli:Long,val unitCostMinor:Long,val type:String,val referenceId:String)
@Entity(tableName="company_settings")
data class CompanySettings(@PrimaryKey val id:Int=1,val companyName:String,val phone:String="",val address:String="",val taxNumber:String="",val baseCurrency:String="YER",val initialized:Boolean=true)