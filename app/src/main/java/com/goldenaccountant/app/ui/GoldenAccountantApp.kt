package com.goldenaccountant.app.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goldenaccountant.app.ui.screens.*
import com.goldenaccountant.app.ui.theme.GoldenTheme
@Composable fun GoldenAccountantApp(){var s by remember{mutableIntStateOf(0)};val screens=listOf<@Composable()->Unit>({DashboardScreen()},{SalesScreen()},{AccountsScreen()},{InventoryScreen()});GoldenTheme{Scaffold(bottomBar={NavigationBar{NavigationBarItem(s==0,{s=0},icon={Icon(Icons.Default.SpaceDashboard,null)},label={Text("الرئيسية")});NavigationBarItem(s==1,{s=1},icon={Icon(Icons.Default.PointOfSale,null)},label={Text("المبيعات")});NavigationBarItem(s==2,{s=2},icon={Icon(Icons.Default.AccountBalance,null)},label={Text("الحسابات")});NavigationBarItem(s==3,{s=3},icon={Icon(Icons.Default.Inventory2,null)},label={Text("المخزون")})}}){p->Box(Modifier.padding(p)){screens[s]()}}}}