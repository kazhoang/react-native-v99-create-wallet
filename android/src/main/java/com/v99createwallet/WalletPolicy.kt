package com.v99createwallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WalletPolicy.newInstance] factory method to
 * create an instance of this fragment.
 */
class WalletPolicy : Fragment(R.layout.fragment_wallet_policy) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    super.onViewCreated(view, savedInstanceState)

    val btnUsingPolicy: LinearLayoutCompat = view.findViewById<LinearLayoutCompat>(R.id.btnUsingPolicy)

    val btnSecurityPolicy: LinearLayoutCompat = view.findViewById<LinearLayoutCompat>(R.id.btnSecurityPolicy)

    val btnContinue: MaterialButton = view.findViewById<MaterialButton>(R.id.btnContinue)

    val checkBox: CheckBox =  view.findViewById<CheckBox>(R.id.checkBox)

    btnUsingPolicy.setOnClickListener {
      this.onButtonUsingPolicyPress()
    }
    btnSecurityPolicy.setOnClickListener {
      this.onButtonSecurityPolicyPress()
    }

    btnContinue.setOnClickListener {
      checkBox.isChecked = false
      this.onContinuePress()
    }

    checkBox.setOnClickListener {
      btnContinue.isEnabled = checkBox.isChecked
    }

  }
  private fun onButtonUsingPolicyPress() {
    findNavController().popBackStack()
  }

  private fun onButtonSecurityPolicyPress() {
    findNavController().popBackStack()
  }

  private fun onContinuePress() {
    findNavController().navigate(R.id.action_walletPolicy_to_createWalletStory)
  }
}
