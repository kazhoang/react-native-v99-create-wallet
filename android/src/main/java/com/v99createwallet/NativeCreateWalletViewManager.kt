package com.v99createwallet

import android.view.LayoutInflater
import android.view.View
import com.facebook.react.bridge.*
import com.facebook.react.common.MapBuilder
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.google.android.material.button.MaterialButton


class NativeCreateWalletViewManager : SimpleViewManager<View>() {
  override fun getName() = "NativeCreateWalletView"

  private var reactContext: ReactContext? = null

  override fun createViewInstance(reactContext: ThemedReactContext): View {
    val inflater = LayoutInflater.from(reactContext)
    val parentLayout = inflater.inflate(R.layout.fragment_send_detail, null)

    this.reactContext = reactContext

    val btnCreateTest = parentLayout.findViewById<MaterialButton>(R.id.btnCreateTest)

    btnCreateTest.setOnClickListener {
      this.onReceiveNativeEvent(parentLayout)
    }

    return parentLayout
  }

  private fun onReceiveNativeEvent(parentLayout: View) {
    val params = Arguments.createMap()
    params.putString("data", "TEST")

    reactContext?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(
      parentLayout.id,
      "createButtonHit",
      params
    )
  }

  override fun getExportedCustomBubblingEventTypeConstants(): Map<String, Any> {
    return mapOf(
      "createButtonHit" to mapOf(
        "phasedRegistrationNames" to mapOf(
          "bubbled" to "onClickCreateButton"
        )
      )
    )
  }
}
