package com.tibz.lpsimulation.common.dataModel

import com.tibz.lpsimulation.common.dataModel.DMTransactionHistory.Companion.sortAscending
import com.tibz.lpsimulation.common.dataModel.DMTransactionHistory.Companion.sortDescending

data class DMTransactionHistory(
    var name: String? = null,
    var description: String? = null,
    var isIncome: Boolean = false,
    var value: Long = 0,
    var gameTimeInterval: DMGameTime? = null
) {
    companion object {
        fun MutableList<DMTransactionHistory>.checkAndPush(transaction: DMTransactionHistory) {
            this.sortedBy {
                it.gameTimeInterval?.year
                it.gameTimeInterval?.month
            }.dropLast(0)
            this += transaction
        }

        fun List<DMTransactionHistory>.sortAscending(): List<DMTransactionHistory> {
            return this.sortedBy {
                it.gameTimeInterval?.year
                it.gameTimeInterval?.month
            }
        }

        fun List<DMTransactionHistory>.sortDescending(): List<DMTransactionHistory> {
            return this.sortedByDescending {
                it.gameTimeInterval?.year
                it.gameTimeInterval?.month
            }
        }
    }
}