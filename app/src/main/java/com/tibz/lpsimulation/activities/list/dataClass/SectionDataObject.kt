package com.tibz.lpsimulation.activities.list.dataClass

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.tibz.lpsimulation.common.helper.SwiftHelper.Companion.getJsonFile

class SectionDataObject {
    data class Root(
        var distanceMeter: String,
        var sections: Array<Sections> = arrayOf()
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Root

            if (distanceMeter != other.distanceMeter) return false
            if (!sections.contentEquals(other.sections)) return false

            return true
        }
        override fun hashCode(): Int {
            var result = distanceMeter.hashCode()
            result = 31 * result + sections.contentHashCode()
            return result
        }
    }

    data class Sections(
        var name: String,
        var description: String,
        var distance: Float
    )

    companion object {
        fun getSectionShop(fileName: String, context: Context, callback: (Root?) -> Unit) {
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