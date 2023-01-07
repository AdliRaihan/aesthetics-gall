package com.tibz.lpsimulation.activities.controller.Setup

import java.lang.ref.WeakReference

class SetupVM {
    interface  ISetup {
        fun dataDidFailed(message: String?)
        fun dataDidSuccess()
    }
    data class SourceTransactions(
        var username: String? = null
    )
    var delegate: WeakReference<ISetup>? = null
    var source: SourceTransactions = SourceTransactions()
    fun validate() {
        // Guard
        source.username.isNullOrEmpty().let {
            if (it) {
                delegate?.get()?.dataDidFailed("Username must not be empty/blank.")
                return
            }
        }
        source.username?.length?.let {
            if (it > 18)
                delegate?.get()?.dataDidFailed("The username must be no more than 18 characters in length.")
            else
                delegate?.get()?.dataDidSuccess()
        }
    }
}