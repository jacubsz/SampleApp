package com.github.jacubsz.sampleapp.viewactions

import android.content.Context
import android.content.Intent
import androidx.annotation.AnyRes
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

class BasicUiTestActions(
    private val context: Context,
    private val uiDevice: UiDevice
) {

    companion object {
        private const val DEFAULT_TIMEOUT = 5000L
    }

    fun openApp() {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)

            uiDevice.wait(
                Until.hasObject(By.pkg(context.packageName).depth(0)),
                Companion.DEFAULT_TIMEOUT
            )
        }
    }

    @Throws(InterruptedException::class)
    fun waitForUiObject(resourceName: String, timeout: Long = DEFAULT_TIMEOUT): UiObject2 =
        uiDevice.wait(Until.findObject(By.res(resourceName)), timeout)

    @Throws(InterruptedException::class)
    fun findUiObjectWithText(text: String, timeout: Long = DEFAULT_TIMEOUT): UiObject2 =
        uiDevice.wait(Until.findObject(By.text(text)), timeout)

    @Throws(InterruptedException::class)
    fun checkIfUiObjectWithTextExists(text: String): UiObject2? =
        uiDevice.findObject(By.text(text))

    fun getResourceName(@AnyRes id: Int): String = context.resources.getResourceName(id)

}