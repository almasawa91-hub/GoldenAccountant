package com.goldenaccountant.app.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.goldenaccountant.app.ui.screens.*
import com.goldenaccountant.app.ui.theme.GoldenTheme

@Composable fun GoldenAccountantApp() {
    var selected by remember { mutableIntStateOf(0) }
    GoldenTheme {
        Scaffold(bottomBar = { NavigationBar {
            NavigationBarItem(selected == 0, { selected = 0 }, { Icon(Icons.Default.SpaceDashboard, null) }, label = { Text("الرئيسية") })
            NavigationBarItem(selected == 1, { selected = 1 }, { Icon(Icons.Default.PointOfSale, null) }, label = { Text("المبيعات") })
            NavigationBarItem(selected == 2, { selected = 2 }, { Icon(Icons.Default.AccountBalance, null) }, label = { Text("الحسابات") })
            NavigationBarItem(selected == 3, { selected = 3 }, { Icon(Icons.Default.Inventory2, null) }, label = { Text("المخزون") })
        }}) { p ->
            Box(Modifier.padding(p)) {
                when (selected) {
                    0 -> DashboardScreen()
                    1 -> SalesScreen()
                    2 -> AccountsScreen()
                    3 -> InventoryScreen()
                }
            }
        }
    }
}
