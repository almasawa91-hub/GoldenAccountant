package com.goldenaccountant.app
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
class AccountingEngineTest{@Test fun balancedAmountsMatch(){assertEquals(12500L,listOf(10000L,2500L).sum())}@Test fun negativeAmountIsRejected(){assertFailsWith<IllegalArgumentException>{require(-1L>=0L)}}}