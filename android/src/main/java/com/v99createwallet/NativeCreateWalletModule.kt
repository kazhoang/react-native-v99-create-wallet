package com.v99createwallet

import android.content.Intent
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class NativeCreateWalletModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "NativeCreateWallet"
  }

  @ReactMethod
  fun showView(promise: Promise) {
    val intent = Intent(reactApplicationContext, CreateWalletActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    reactApplicationContext.startActivity(intent)
    promise.resolve(true)
  }

  @ReactMethod
  fun showViewNavigateTo(text: String? = "", promise: Promise) {
    val intent = Intent(reactApplicationContext, CreateWalletActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtra("screenId", text)
    reactApplicationContext.startActivity(intent)
    promise.resolve(true)
  }
}
