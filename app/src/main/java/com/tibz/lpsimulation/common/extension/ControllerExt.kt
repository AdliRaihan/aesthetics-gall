package com.tibz.lpsimulation.common.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.pushFragments(holder: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(holder, fragment)
        .commit()
}

fun Activity.navigation(to: Activity) {
    val intent = Intent(this, to::class.java)
    startActivity(intent)
    // overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
}

fun Activity.navigationData(to: Activity): Intent {
    return Intent(this, to::class.java)
}

fun Activity.navigateIntent(intent: Intent) {
    startActivity(intent)
    // overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
}

fun Activity.getExtraData(): Bundle? {
    return intent.extras
}

fun Activity.popController() {
    finish()
//    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
    // overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
}

fun Activity.endAnimation() {
    // overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
}