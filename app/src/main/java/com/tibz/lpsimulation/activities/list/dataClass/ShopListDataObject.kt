package com.tibz.lpsimulation.activities.list.dataClass

import android.content.Context
import com.google.gson.Gson
import com.tibz.lpsimulation.common.helper.SwiftHelper.Companion.getJsonFile

/*

    {
      "name": "Kitchen Knife",
      "description": "Tool for cooking",
      "price": 2,
      "imgName": ""
    },
 */
class ShopListDataObject {
    data class Root(
        var items: Array<Items> = arrayOf()
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Root

            if (!items.contentEquals(other.items)) return false

            return true
        }

        override fun hashCode(): Int {
            return items.contentHashCode()
        }
    }

    data class Items(
        var name: String,
        var description: String,
        var price: Long,
        var imgName: String
    )

    companion object {
        fun getItemLists(fileName: String, context: Context, callback: (Root?) -> Unit) {
            context.getJsonFile("${fileName}.json") { success, value ->
                if (success) {
                    val data = Gson().fromJson(value, Root::class.java)
                    callback.invoke(data)
                } else {
                    callback.invoke(null)
                }
            }
        }
    }
}