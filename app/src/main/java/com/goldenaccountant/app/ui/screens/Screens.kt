package com.goldenaccountant.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldenaccountant.app.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.util.UUID

private fun money(v:Long)=String.format("%.2f",v/100.0)
private fun now()=System.currentTimeMillis()

@Composable fun DashboardScreen(vm:DashboardViewModel=hiltViewModel()){
 val company by vm.company.collectAsState(initial=null)
 val accounts by vm.accounts.collectAsState(initial=emptyList())
 val products by vm.products.collectAsState(initial=emptyList())
 Column(Modifier.fillMaxSize().padding(16.dp)){
  Text(company?.companyName ?: "المحاسب الذهبي",style=MaterialTheme.typography.headlineMedium)
  Text("لوحة التحكم",style=MaterialTheme.typography.titleLarge)
  Spacer(Modifier.height(12.dp))
  Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)){
   StatCard("الحسابات",accounts.size.toString(),Modifier.weight(1f))
   StatCard("الأصناف",products.size.toString(),Modifier.weight(1f))
  }
  Spacer(Modifier.height(12.dp))
  Text("الأرصدة والحركات مصدرها قاعدة البيانات المحلية. لا توجد بيانات تجريبية.")
 }
}
@Composable private fun StatCard(title:String,value:String,modifier:Modifier)=Card(modifier){Column(Modifier.padding(14.dp)){Text(title);Text(value,style=MaterialTheme.typography.headlineSmall)}}

@Composable fun AccountsScreen(vm:AccountsViewModel=hiltViewModel()){
 val accounts by vm.items.collectAsState(initial=emptyList());var show by remember{mutableStateOf(false)}
 Column(Modifier.fillMaxSize().padding(16.dp)){
  Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Text("الحسابات",style=MaterialTheme.typography.headlineMedium);Button({show=true}){Text("إضافة")}}
  Spacer(Modifier.height(8.dp))
  LazyColumn{items(accounts,key={it.id}){a->Card(Modifier.fillMaxWidth().padding(vertical=3.dp)){Column(Modifier.padding(14.dp)){Text(a.name,style=MaterialTheme.typography.titleMedium);Text(a.type+" • "+a.currency);Text("الرصيد الافتتاحي: "+money(a.openingBalanceMinor))}}}}
 }
 if(show)AccountDialog({show=false}){name,phone,type->vm.add(name,phone,type);show=false}
}
@Composable private fun AccountDialog(close:()->Unit,save:(String,String,String)->Unit){
 var name by remember{mutableStateOf("")};var phone by remember{mutableStateOf("")};var type by remember{mutableStateOf("عميل")}
 AlertDialog(onDismissRequest=close,title={Text("حساب جديد")},text={Column(verticalArrangement=Arrangement.spacedBy(8.dp)){OutlinedTextField(name,{name=it},label={Text("الاسم")});OutlinedTextField(phone,{phone=it},label={Text("الهاتف")});OutlinedTextField(type,{type=it},label={Text("النوع")})}},confirmButton={Button(enabled=name.isNotBlank(),onClick={save(name,phone,type)}){Text("حفظ")}},dismissButton={TextButton(close){Text("إلغاء")}})
}

@Composable fun InventoryScreen(vm:ProductsViewModel=hiltViewModel()){
 val products by vm.items.collectAsState(initial=emptyList());var show by remember{mutableStateOf(false)}
 Column(Modifier.fillMaxSize().padding(16.dp)){
  Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Text("المخزون",style=MaterialTheme.typography.headlineMedium);Button({show=true}){Text("إضافة صنف")}}
  Spacer(Modifier.height(8.dp))
  LazyColumn{items(products,key={it.id}){p->Card(Modifier.fillMaxWidth().padding(vertical=3.dp)){Column(Modifier.padding(14.dp)){Text(p.name,style=MaterialTheme.typography.titleMedium);Text("SKU: "+p.sku);Text("سعر البيع: "+money(p.salePriceMinor))}}}}
 }
 if(show)ProductDialog({show=false}){sku,name,sale,purchase->vm.add(sku,name,sale,purchase);show=false}
}
@Composable private fun ProductDialog(close:()->Unit,save:(String,String,Long,Long)->Unit){
 var sku by remember{mutableStateOf("")};var name by remember{mutableStateOf("")};var sale by remember{mutableStateOf("")};var purchase by remember{mutableStateOf("")}
 AlertDialog(onDismissRequest=close,title={Text("صنف جديد")},text={Column(verticalArrangement=Arrangement.spacedBy(6.dp)){OutlinedTextField(sku,{sku=it},label={Text("SKU")});OutlinedTextField(name,{name=it},label={Text("اسم الصنف")});OutlinedTextField(sale,{sale=it},label={Text("سعر البيع")});OutlinedTextField(purchase,{purchase=it},label={Text("سعر الشراء")})}},confirmButton={Button(enabled=name.isNotBlank()&&sku.isNotBlank(),onClick={save(sku,name,(sale.toDoubleOrNull()?.times(100))?.toLong()?:0,(purchase.toDoubleOrNull()?.times(100))?.toLong()?:0)}){Text("حفظ")}},dismissButton={TextButton(close){Text("إلغاء")}})
}

@Composable fun SalesScreen(vm:SalesViewModel=hiltViewModel()){
 val invoices by vm.invoices.collectAsState(initial=emptyList());var show by remember{mutableStateOf(false)}
 Column(Modifier.fillMaxSize().padding(16.dp)){
  Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Text("المبيعات",style=MaterialTheme.typography.headlineMedium);Button({show=true}){Text("فاتورة")}}
  Spacer(Modifier.height(8.dp))
  LazyColumn{items(invoices,key={it.id}){i->Card(Modifier.fillMaxWidth().padding(vertical=3.dp)){Column(Modifier.padding(14.dp)){Text(i.number,style=MaterialTheme.typography.titleMedium);Text(i.type+" • الإجمالي "+money(i.totalMinor)+" • المدفوع "+money(i.paidMinor))}}}}
 }
 if(show)InvoiceDialog({show=false}){number,total->vm.add(number,total);show=false}
}
@Composable private fun InvoiceDialog(close:()->Unit,save:(String,Long)->Unit){
 var number by remember{mutableStateOf("")};var total by remember{mutableStateOf("")}
 AlertDialog(onDismissRequest=close,title={Text("فاتورة مبيعات")},text={Column{OutlinedTextField(number,{number=it},label={Text("رقم الفاتورة")});OutlinedTextField(total,{total=it},label={Text("الإجمالي")})}},confirmButton={Button(enabled=number.isNotBlank(),onClick={save(number,(total.toDoubleOrNull()?.times(100))?.toLong()?:0)}){Text("حفظ")}},dismissButton={TextButton(close){Text("إلغاء")}})
}

@HiltViewModel class DashboardViewModel @Inject constructor(db:AppDatabase):ViewModel(){val company=db.company().observe();val accounts=db.accounts().observeAll();val products=db.products().observeAll()}
@HiltViewModel class AccountsViewModel @Inject constructor(private val dao:AccountDao):ViewModel(){val items=dao.observeAll();fun add(name:String,phone:String,type:String){viewModelScope.launch{runCatching{dao.insert(Account(UUID.randomUUID().toString(),name,phone=phone,type=type,createdAt=now(),updatedAt=now()))}}}}
@HiltViewModel class ProductsViewModel @Inject constructor(private val dao:ProductDao):ViewModel(){val items=dao.observeAll();fun add(sku:String,name:String,sale:Long,purchase:Long){viewModelScope.launch{runCatching{dao.insert(Product(UUID.randomUUID().toString(),sku,name,salePriceMinor=sale,purchasePriceMinor=purchase,createdAt=now(),updatedAt=now()))}}}}
@HiltViewModel class SalesViewModel @Inject constructor(private val db:AppDatabase):ViewModel(){val invoices=db.invoices().observeAll();fun add(number:String,total:Long){viewModelScope.launch{runCatching{db.invoices().insert(Invoice(UUID.randomUUID().toString(),number,"SALE",now(),null,total,0,0,total,0,createdAt=now()))}}}}
