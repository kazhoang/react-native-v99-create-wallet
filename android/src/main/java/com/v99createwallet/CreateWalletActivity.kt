package com.v99createwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController

class CreateWalletActivity: AppCompatActivity() {
  lateinit var nav: NavController
  override fun onSupportNavigateUp(): Boolean = nav.navigateUp()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.view_layout)
    setSupportActionBar(findViewById(R.id.toolbar))

    nav = Navigation.findNavController(this, R.id.fragment_nav)
//    supportActionBar?.hide()

    setupActionBarWithNavController(nav)
    supportActionBar?.hide()

    when (intent.getStringExtra("screenId")) {
      "first" -> nav.navigate(R.id.home)
      "second" -> nav.navigate(R.id.walletPolicy)
      "third" -> nav.navigate(R.id.createWalletStory)
    }
  }
}
