package com.tibz.lpsimulation

import com.tibz.lpsimulation.common.dataModel.DMTransactionHistory
import com.tibz.lpsimulation.common.dataModel.DMTransactionHistory.Companion.checkAndPush
import com.tibz.lpsimulation.common.extension.divideEqual
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
    @Test
    fun testValueInt() {
        val sutShouldTrue = 3 * 10
        val sutShouldFalse = (3 * 10) - 1
        assert(sutShouldTrue.divideEqual() == 10) {
            sutShouldTrue.divideEqual().toString()
        }
        assert(sutShouldFalse.divideEqual() == 10) {
            sutShouldFalse.divideEqual().toString()
        }
    }
}