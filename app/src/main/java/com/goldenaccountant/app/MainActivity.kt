package com.goldenaccountant.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import dagger.hilt.android.AndroidEntryPoint
import com.goldenaccountant.app.ui.GoldenAccountantApp
@AndroidEntryPoint class MainActivity:ComponentActivity(){override fun onCreate(b:Bundle?){super.onCreate(b);setContent{CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){GoldenAccountantApp()}}}}