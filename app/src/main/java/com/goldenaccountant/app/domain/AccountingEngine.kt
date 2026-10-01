package com.goldenaccountant.app.domain
import androidx.room.withTransaction
import com.goldenaccountant.app.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
data class DebitCredit(val accountId:String,val debitMinor:Long=0,val creditMinor:Long=0,val description:String="")
class AccountingEngine @Inject constructor(private val db:AppDatabase){
 suspend fun post(date:Long,referenceType:String,referenceId:String,description:String,lines:List<DebitCredit>)=withContext(Dispatchers.IO){
  require(lines.isNotEmpty()){"القيد لا يحتوي على أسطر"}
  require(lines.all{it.debitMinor>=0&&it.creditMinor>=0}){"المبالغ لا يمكن أن تكون سالبة"}
  require(lines.all{(it.debitMinor==0L) xor (it.creditMinor==0L)}){"السطر يجب أن يكون مدينًا أو دائنًا"}
  require(lines.sumOf{it.debitMinor}==lines.sumOf{it.creditMinor}){"القيد غير متوازن"}
  db.withTransaction{
   val now=System.currentTimeMillis();val jid=UUID.randomUUID().toString()
   db.journals().insertEntry(JournalEntry(jid,date,referenceType,referenceId,description,now))
   db.journals().insertLines(lines.map{JournalEntryLine(UUID.randomUUID().toString(),jid,it.accountId,it.debitMinor,it.creditMinor,it.description)})
   lines.forEach{db.accounts().insertTransaction(AccountTransaction(UUID.randomUUID().toString(),it.accountId,date,it.debitMinor,it.creditMinor,it.description,referenceType,referenceId))}
   db.audit().insert(AuditLog(UUID.randomUUID().toString(),now,"POST","JOURNAL",jid,description))
  }
 }
}
