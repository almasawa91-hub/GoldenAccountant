package com.goldenaccountant.app.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goldenaccountant.app.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@Composable fun DashboardScreen(){Column(Modifier.fillMaxSize().padding(20.dp)){Text("المحاسب الذهبي",style=MaterialTheme.typography.headlineMedium);Text("لوحة التحكم",style=MaterialTheme.typography.titleLarge);Spacer(Modifier.height(16.dp));Text("الأرقام ستُقرأ من قاعدة البيانات الفعلية، بدون بيانات وهمية.")}}
@Composable fun AccountsScreen(vm:AccountsViewModel=hiltViewModel()){val xs by vm.items.collectAsStateWithLifecycle();Column(Modifier.fillMaxSize().padding(16.dp)){Text("الحسابات",style=MaterialTheme.typography.headlineMedium);LazyColumn{items(xs,key={it.id}){a->Card(Modifier.fillMaxWidth().padding(vertical=4.dp)){Column(Modifier.padding(16.dp)){Text(a.name,style=MaterialTheme.typography.titleMedium);Text(a.type);Text("الرصيد الافتتاحي: ${a.openingBalanceMinor}")}}}}}}}
@Composable fun InventoryScreen(vm:ProductsViewModel=hiltViewModel()){val xs by vm.items.collectAsStateWithLifecycle();Column(Modifier.fillMaxSize().padding(16.dp)){Text("المخزون",style=MaterialTheme.typography.headlineMedium);LazyColumn{items(xs,key={it.id}){p->Card(Modifier.fillMaxWidth().padding(vertical=4.dp)){Column(Modifier.padding(16.dp)){Text(p.name,style=MaterialTheme.typography.titleMedium);Text("SKU: ${p.sku}");Text("سعر البيع: ${p.salePriceMinor}")}}}}}}}
@Composable fun SalesScreen(){Column(Modifier.fillMaxSize().padding(20.dp)){Text("المبيعات",style=MaterialTheme.typography.headlineMedium);Spacer(Modifier.height(12.dp));Text("الفاتورة ستربط الأصناف والمخزون والحساب والقيد المحاسبي ضمن معاملة ذرية.")}}
@HiltViewModel class AccountsViewModel @Inject constructor(d:AccountDao):ViewModel(){val items=d.observeAll()}
@HiltViewModel class ProductsViewModel @Inject constructor(d:ProductDao):ViewModel(){val items=d.observeAll()}