package com.v99createwallet.entity

data class NetworkInfo(
  val name: String,
  val symbol: String,
  val rpcServerUrl: String,
  val backendUrl: String,
  val etherscanUrl: String,
  val chainId: Int,
  val isMainNetwork: Boolean
)
