package com.tibz.lpsimulation.common.dataModel

import com.tibz.lpsimulation.common.dataModel.DMTransactionHistory.Companion.checkAndPush

class DMGameUtils {
    companion object {
        fun DMGame.useMoney(
            value: Long,
            transactionName: String,
            transactionDescription: String
        ): Boolean {
            this.information?.cash?.let {
                if (it < value)
                    return false
            }

            val tempTransaction = DMTransactionHistory()

            tempTransaction.isIncome = value >= 0
            tempTransaction.name = transactionName
            tempTransaction.description = transactionDescription
            tempTransaction.gameTimeInterval = this.gameTime

            this.transactionHistory.checkAndPush(tempTransaction)
            return true
        }
    }
}