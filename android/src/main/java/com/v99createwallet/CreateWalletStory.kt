package com.v99createwallet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet

class CreateWalletStory : Fragment(R.layout.fragment_create_wallet_story) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    super.onViewCreated(view, savedInstanceState)

    val note1: LinearLayout = view.findViewById(R.id.note1)
    val note2: LinearLayout = view.findViewById(R.id.note2)
    val note3: LinearLayout = view.findViewById(R.id.note3)
    val note4: LinearLayout = view.findViewById(R.id.note4)

    val message2: LinearLayout = view.findViewById(R.id.message2)

    val addressField: TextView = view.findViewById(R.id.addressField)
    val btnComplete: MaterialButton = view.findViewById(R.id.btnComplete)

    btnComplete.setOnClickListener {
      this.onButtonCompletePress()
    }

    val wallet = HDWallet(128, "")

    val addressETH = wallet.getAddressForCoin(CoinType.ETHEREUM)

    addressField.text = formatEllipsisAddressToken(addressETH)

    val duration = 500L
    val delay = 2000L

    val fadeInAnimation = AnimationUtils.makeInAnimation(context, false)
    fadeInAnimation.duration = duration

    note1.startAnimation(fadeInAnimation)
    note1.visibility = View.VISIBLE

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      note2.visibility = View.VISIBLE
      note2.startAnimation(fadeInAnimation)
    }, delay)

    handler.postDelayed({
      note3.visibility = View.VISIBLE
      note3.startAnimation(fadeInAnimation)
    }, delay * 2)

    handler.postDelayed({
      note4.visibility = View.VISIBLE
      note4.startAnimation(fadeInAnimation)
    }, delay * 3)

    handler.postDelayed({
      message2.visibility = View.VISIBLE
      message2.startAnimation(fadeInAnimation)

      btnComplete.visibility = View.VISIBLE
      btnComplete.startAnimation(fadeInAnimation)
    }, delay * 4)
  }

  private fun formatEllipsisAddressToken(address: String, limit: Int = 20): String {
    val lengthEndAddressEllipsis = 4
    return if (address.length > limit) {
      address.substring(0, limit) + "..." + address.substring(address.length - lengthEndAddressEllipsis, address.length)
    } else {
      address
    }
  }

  private fun onButtonCompletePress() {
    findNavController().navigate(R.id.action_createWalletStory_to_home2)
  }
}
