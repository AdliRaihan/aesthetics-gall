package com.tibz.lpsimulation.common.dataModel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

data class DMGame(
    var information: DMPlayerInformation? = null,
    var statistics: DMPlayerStatistics? = null,
    var gameTime: DMGameTime? = null,
    var property: MutableList<DMPlayerProperty> = mutableListOf(),
    var jobInformation: MutableList<DMPlayerJobInformation> = mutableListOf(),
    var relationship: MutableList<DMRelation> = mutableListOf(),
    var transactionHistory: MutableList<DMTransactionHistory> = mutableListOf()
) {
    companion object {
        fun AppCompatActivity.get(): DMGame? {
            return Gson().fromJson(
                getSharedPreferences(
                    "DMGameData", MODE_PRIVATE
                ).getString("GMValues", null),
                DMGame::class.java)
        }
    }
    fun AppCompatActivity.save() {
        getSharedPreferences("DMGameData", MODE_PRIVATE).edit()
            .also {
                it.putString("GMValues", Gson().toJson(this@DMGame))
            }.apply()
    }
}