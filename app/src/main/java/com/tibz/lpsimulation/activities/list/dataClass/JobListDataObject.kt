package com.tibz.lpsimulation.activities.list.dataClass

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.tibz.lpsimulation.common.helper.SwiftHelper.Companion.getJsonFile

/*

    {
      "position": "Travel Agent",
      "level": [
        {
          "positionLevel": "Any Level",
          "salaryRange": {
            "from": 30000,
            "to": 50000
          },
          "experience": 0
        }
      ],
      "type": "yearly",
      "skills": [],
      "educationLevel": ["Bachelor Degree"]
    }
 */
class JobListDataObject {
    data class Root(
        var jobs: Array<Jobs> = arrayOf()
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Root

            if (!jobs.contentEquals(other.jobs)) return false

            return true
        }

        override fun hashCode(): Int {
            return jobs.contentHashCode()
        }
    }
    data class Jobs(
        var position: String,
        var level: Level,
        var type: String,
        var skills: Array<String> = arrayOf(),
        var educationLevel: Array<String> = arrayOf()
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Jobs

            if (position != other.position) return false
            if (level != other.level) return false
            if (type != other.type) return false
            if (!skills.contentEquals(other.skills)) return false
            if (!educationLevel.contentEquals(other.educationLevel)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = position.hashCode()
            result = 31 * result + level.hashCode()
            result = 31 * result + type.hashCode()
            result = 31 * result + skills.contentHashCode()
            result = 31 * result + educationLevel.contentHashCode()
            return result
        }
    }
    data class Level(
        var positionLevel: String,
        var salaryRange: SalaryRange,
        var experience: Int
    )
    data class SalaryRange(
        var from: Long,
        var to: Long
    )

    companion object {
        fun getData(context: Context, callback: (Root?) -> Unit) {
            context.getJsonFile("jobListDetail.json") { success, value ->
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