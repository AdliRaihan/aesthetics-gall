package com.tibz.lpsimulation

import com.tibz.lpsimulation.common.dataModel.DMTransactionHistory
import com.tibz.lpsimulation.common.dataModel.DMTransactionHistory.Companion.checkAndPush
import org.junit.Test

class DMHistoryTransactionTest {
    @Test
    fun testCheckAndPush() {
        var sutDM = mutableListOf<DMTransactionHistory>()
        val target = DMTransactionHistory()
        target.description = "Joe Mum is hot"
         sutDM.checkAndPush(target)
        assert(sutDM.size == 1)

        assert(sutDM.first().description == "Joe Mum is hot")
    }
}