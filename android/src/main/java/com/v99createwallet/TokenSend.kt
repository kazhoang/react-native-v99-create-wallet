package com.v99createwallet

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.google.protobuf.ByteString
import com.v99createwallet.entity.NetworkInfo
import com.v99createwallet.util.BalanceUtils
import com.v99createwallet.util.BalanceUtils.baseToSubunit
import com.v99createwallet.util.BalanceUtils.getNonce
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import wallet.core.java.AnySigner
import wallet.core.jni.AnyAddress
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet
import wallet.core.jni.proto.Ethereum
import java.math.BigInteger
import java.util.function.Consumer


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class TokenSend : Fragment(R.layout.fragment_token_send) {
    init {
      System.loadLibrary("TrustWalletCore")
    }

    val defaultNetwork = NetworkInfo(
      "Ethereum", "ETH",
      "https://data-seed-prebsc-2-s1.binance.org:8545",
      "https://api.trustwalletapp.com/",
      "https://testnet.bscscan.com/", 97, false
    )

    val toTestnetAddress = "0x110F1586329D01b6C12F30588C6F8EECa93132f2"

    val web3j = Web3j.build(HttpService(defaultNetwork.rpcServerUrl));

    val testGasPrice = web3j.ethGasPrice().sendAsync().get().gasPrice


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val toInputLayout: TextInputLayout = view.findViewById<TextInputLayout>(R.id.to_input_layout)
      val toAddressText: EditText = view.findViewById<EditText>(R.id.send_to_address)

      val amountInputLayout: TextInputLayout = view.findViewById<TextInputLayout>(R.id.amount_input_layout)
      val amountText: EditText = view.findViewById<EditText>(R.id.send_amount)

      val sendNativeTokenBtn: Button = view.findViewById<Button>(R.id.send_native_token_button)
      val sendTokenBtn: Button = view.findViewById<Button>(R.id.send_token_button)

      val wallet = HDWallet("year casino derive milk attack buddy ball remind whale wash student common", "")
      println("Mnemonic: \n${wallet.mnemonic()}")

      val coinEth: CoinType = CoinType.ETHEREUM
      val addressEth = wallet.getAddressForCoin(coinEth)
      println("Default ETH address: \n$addressEth")

      val secretPrivateKey = wallet.getKeyForCoin(coinEth)

      val gasLimit = ByteString.copyFrom(BigInteger("5208", 16).toByteArray()) // decimal 21000
      val privateKey = ByteString.copyFrom(secretPrivateKey.data())

      sendNativeTokenBtn.setOnClickListener {
        println("Button hittted")
        var inputValid = true
        val to = toAddressText.text.toString()
        if (!isAddressValid(to, coinEth)) {
//          toInputLayout.error = getString(R.string.error_invalid_address)
          inputValid = false;

        }

        val amount = amountText.text.toString()
        if (!isValidAmount(amount)) {
//            amountInputLayout.error = getString(R.string.error_invalid_amount)
            inputValid = false;
        }


        println("isValid ${inputValid}")
//        toInputLayout.isErrorEnabled = false;
//        amountInputLayout.isErrorEnabled = false;

        val amountInSubunits = baseToSubunit(amount, 18)
        println("AMOUNT IN SUB UNITS ${amountInSubunits}")

        val result = this.createTransaction(addressEth, addressEth, amountInSubunits, testGasPrice, gasLimit, privateKey)
        println("RESULT TRansaction ${result}")
      }

      sendTokenBtn.setOnClickListener {
        println("Button hittted")
        var inputValid = true
        val to = toAddressText.text.toString()
        if (!isAddressValid(to, coinEth)) {
//          toInputLayout.error = getString(R.string.error_invalid_address)
          inputValid = false;

        }

        val amount = amountText.text.toString()
        if (!isValidAmount(amount)) {
//            amountInputLayout.error = getString(R.string.error_invalid_amount)
          inputValid = false;
        }

        println("isValid ${inputValid}")

        val amountInSubunits = baseToSubunit(amount, 18)
        println("AMOUNT IN SUB UNITS ${amountInSubunits}")

        this.createTokenTransfer(addressEth, addressEth, toTestnetAddress, amountInSubunits, testGasPrice, gasLimit)
      }
    }

  fun ByteArray.toHexString(lowerCase: Boolean = true): String {
    val stringBuilder = StringBuilder(size * 2)
    val digits = if (lowerCase) "0123456789abcdef" else "0123456789ABCDEF"
    for (byte in this) {
      val value = byte.toInt() and 0xFF
      stringBuilder.append(digits[value shr 4])
      stringBuilder.append(digits[value and 0x0F])
    }
    return stringBuilder.toString()
  }

  private fun isAddressValid(address: String, coinType: CoinType): Boolean {
    return AnyAddress.isValid(address, coinType)
  }

  fun isValidAmount(eth: String): Boolean {
    return try {
      val wei: String = BalanceUtils.ethToWei(eth)
      wei != null
    } catch (e: Exception) {
      false
    }
  }

  fun createTokenTransfer(from: String, to: String, contractAddress: String, amount: BigInteger, gasPrice: BigInteger, gasLimit: ByteString) {
    val params: List<Type<*>> = listOf(Address(to), Uint256(amount))
    val returnTypes: List<TypeReference<*>> = listOf(object : TypeReference<Bool>() {})
    val function = Function("transfer", params, returnTypes)
    val encodedFunction = FunctionEncoder.encode(function)
    val data =  Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(encodedFunction))

    println("Check_createTokenTransfer_Data ${data}")
//    createTransaction(from, contractAddress, BigInteger.valueOf(0), gasPrice, gasLimit, data)
  }

  fun createTransaction(
    from: String,
    toAddress: String,
    amount: BigInteger,
    gasPrice: BigInteger,
    gasLimit: ByteString,
    privateKey: ByteString
  ): String? {

    val transaction = Ethereum.Transaction.newBuilder().apply {
      this.transfer = Ethereum.Transaction.Transfer.newBuilder().apply {
        this.amount = ByteString.copyFrom(amount.toByteArray())
      }.build()
    }.build()

    println("transaction: ${transaction}")
    val ethGetTransactionCount = getNonce(web3j, from)
    println("WEB3J-ethGetTransactionCount: ${ethGetTransactionCount}")

    val signerInput = Ethereum.SigningInput.newBuilder().apply {
      this.chainId = ByteString.copyFrom(BigInteger("97").toByteArray())
      this.gasPrice = ByteString.copyFrom(testGasPrice.toByteArray())// decimal 3600000000
      this.gasLimit = gasLimit // decimal 21000
      this.toAddress = toAddress
      this.transaction = transaction
      this.privateKey = privateKey
      this.nonce = ByteString.copyFrom(ethGetTransactionCount?.toByteArray())
    }.build()

    val signerOutput = AnySigner.sign(signerInput, CoinType.ETHEREUM, Ethereum.SigningOutput.parser())
    println("Signed transaction: \n${signerOutput.encoded.toByteArray().toHexString(false)}")

    val raw = web3j.ethSendRawTransaction("0x" + signerOutput.encoded.toByteArray().toHexString(false))
      .sendAsync().get()

    println("RAWWW ${raw}")
    if (raw.hasError()) {
      println("Raw Error ${raw.error.message}")
    }
    println("transactionHash ${raw.transactionHash}")

    return raw.transactionHash
  }
}
