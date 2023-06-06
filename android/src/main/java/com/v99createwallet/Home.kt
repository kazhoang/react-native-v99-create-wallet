package com.v99createwallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment(R.layout.fragment_home) {
    init {
      System.loadLibrary("TrustWalletCore")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val btnCreate: Button = view.findViewById<Button>(R.id.btnCreate)

      val btnImport: Button = view.findViewById<Button>(R.id.btnImport)

      btnCreate.setOnClickListener {
        this.onButtonCreatePress()
      }
      btnImport.setOnClickListener {
        this.onButtonImportPress()
      }
    }

  private fun onButtonCreatePress() {
      val wallet = HDWallet(128, "")
      // Handle button click event
      // You can add your custom logic here or emit an event to the React Native side
      val addressBTC = wallet.getAddressForCoin(CoinType.BITCOIN)
      val addressETH = wallet.getAddressForCoin(CoinType.ETHEREUM)
      val addressBNB = wallet.getAddressForCoin(CoinType.BINANCE)

      findNavController().navigate(R.id.action_home2_to_walletPolicy2)
      println("-----------------------CREATED---------------------------")

      println("addressBTC: $addressBTC")
      println("addressETH: $addressETH")
      println("addressBNB: $addressBNB")
    }

  private fun onButtonImportPress() {
      val wallet = HDWallet("ripple scissors kick mammal hire column oak again sun offer wealth tomorrow wagon turn fatal", "")
      val addressBTC = wallet.getAddressForCoin(CoinType.BITCOIN)
      val addressETH = wallet.getAddressForCoin(CoinType.ETHEREUM)
      val addressBNB = wallet.getAddressForCoin(CoinType.BINANCE)

      println("-----------------------IMPORTED---------------------------")

    println("addressBTC: $addressBTC")
    println("addressETH: $addressETH")
    println("addressBNB: $addressBNB")

//     findNavController().navigate(R.id.action_home2_to_walletPolicy2)

      // Handle button click event
      // You can add your custom logic here or emit an event to the React Native side
    }
}
