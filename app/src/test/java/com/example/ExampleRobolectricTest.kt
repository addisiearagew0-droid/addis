package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("የኢትዮጵያ ነገሥታት", appName)
    assertEquals("ይሄን አፕ የሰራው አዲሴ አራጋው ነው", context.getString(R.string.developer_credit_message))
    assertEquals("0942320888", context.getString(R.string.developer_phone_number))
    assertEquals("0942320888", context.getString(R.string.support_telebirr_account))
    assertEquals("1000282540634", context.getString(R.string.support_cbe_account))
    assertEquals("66833787", context.getString(R.string.support_abyssinia_account))
  }
}
