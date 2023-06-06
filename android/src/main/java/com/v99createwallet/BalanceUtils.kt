package com.v99createwallet.util

import org.ethereum.geth.*
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode


object BalanceUtils {
  private const val weiInEth = "1000000000000000000"
  private val keyStore: KeyStore? = null

  fun weiToEth(wei: BigInteger): BigDecimal {
    return Convert.fromWei(BigDecimal(wei), Convert.Unit.ETHER)
  }

  @Throws(Exception::class)
  fun weiToEth(wei: BigInteger, sigFig: Int): String {
    val eth = weiToEth(wei)
    val scale = sigFig - eth.precision() + eth.scale()
    val ethScaled = eth.setScale(scale, RoundingMode.HALF_UP)
    return ethScaled.toString()
  }

  fun ethToUsd(priceUsd: String, ethBalance: String): String {
    var usd = BigDecimal(ethBalance).multiply(BigDecimal(priceUsd))
    usd = usd.setScale(2, RoundingMode.CEILING)
    return usd.toString()
  }

  @Throws(Exception::class)
  fun ethToWei(eth: String): String {
    val wei = BigDecimal(eth).multiply(BigDecimal(weiInEth))
    return wei.toBigInteger().toString()
  }

  fun weiToGweiBI(wei: BigInteger): BigDecimal {
    return Convert.fromWei(BigDecimal(wei), Convert.Unit.GWEI)
  }

  fun weiToGwei(wei: BigInteger): String {
    return Convert.fromWei(BigDecimal(wei), Convert.Unit.GWEI).toPlainString()
  }

  fun gweiToWei(gwei: BigDecimal): BigInteger {
    return Convert.toWei(gwei, Convert.Unit.GWEI).toBigInteger()
  }

  /**
   * Base - taken to mean default unit for a currency e.g. ETH, DOLLARS
   * Subunit - taken to mean subdivision of base e.g. WEI, CENTS
   *
   * @param baseAmountStr - decimal amount in base unit of a given currency
   * @param decimals - decimal places used to convert to subunits
   * @return amount in subunits
   */
  fun baseToSubunit(baseAmountStr: String, decimals: Int): BigInteger {
    assert(decimals >= 0)
    val baseAmount = BigDecimal(baseAmountStr)
    val subunitAmount = baseAmount.multiply(BigDecimal.valueOf(10).pow(decimals))
    return try {
      subunitAmount.toBigIntegerExact()
    } catch (ex: ArithmeticException) {
      assert(false)
      subunitAmount.toBigInteger()
    }
  }

  /**
   * @param subunitAmount - amount in subunits
   * @param decimals - decimal places used to convert subunits to base
   * @return amount in base units
   */
  fun subunitToBase(subunitAmount: BigInteger, decimals: Int): BigDecimal {
    assert(decimals >= 0)
    return BigDecimal(subunitAmount).divide(BigDecimal.valueOf(10).pow(decimals))
  }

  fun getNonce(web3j: Web3j, address: String?): BigInteger? {
    val ethGetTransactionCount =
      web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get()
    return ethGetTransactionCount.transactionCount
  }

}
