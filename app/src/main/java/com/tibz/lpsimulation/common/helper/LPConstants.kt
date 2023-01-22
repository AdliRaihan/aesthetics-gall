package com.tibz.lpsimulation.common.helper

class LPConstants {
    class FileID {
        companion object {
            const val activityJSON = "sectionActivities"
            const val travelJSON = "sectionTravel"
            const val shopJSON = "sectionShop"
        }
    }
    class NavigationName {
        companion object {
            const val headerActivity = "Activities"
            const val headerJobs = "Jobs"
            const val headerTravel = "Travel"
            const val headerOwnedProperty = "Owned Property"
            const val headerOwnedVehicle = "Owned Vehicle"
        }
    }
    class RouterName {
        companion object {
            const val phoneDealership: String = "phone dealership"
            const val carDealership: String = "car dealership"
            const val supermarket: String = "supermarket"
        }
    }
    class CollectionID {
        companion object {
            const val categoriesCell = "CategoriesCell"
            const val listsPhotoCell = "ListPhotoCell"
        }
    }
    class StaticInputLogic {
        companion object {
            const val filterName = "Filter"
        }
    }
}