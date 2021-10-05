package com.ybrflight552.fitnessapp.database.models

object TokenContract {
    const val TABLE_TOKEN = "token_database"
    object Columns {
        const val ATHLETE_ID = "id"
        const val EXPIRES_AT = "expires_at"
        const val REFRESH_TOKEN = "refresh_token"
        const val ACCESS_TOKEN = "access_token"
        const val SCOPE = "scope"
    }
}

object AthleteContract {
    const val TABLE_ATHLETE = "athlete_database"
    object Columns {
        const val ID = "id"
        const val USERNAME = "username"
        const val RESOURCE_STATE = "resource_state"
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"
        const val BIO = "bio"
        const val CITY = "city"
        const val STATE = "state"
        const val COUNTRY = "country"
        const val SEX = "sex"
        const val PREMIUM = "premium"
        const val SUMMIT = "summit"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val BADGE_TYPE_ID = "badge_type_id"
        const val WEIGHT = "weight"
        const val PROFILE_MEDIUM = "profile_medium"
        const val PROFILE = "profile"
        const val FRIEND = "friend"
        const val FOLLOWER = "follower"
        const val FOLLOWER_COUNT = "follower_count"
        const val FRIEND_COUNT = "friend_count"
        const val MUTUAL_FRIEND_COUNT = "mutual_friend_count"
        const val ATHLETE_TYPE = "athlete_type"
        const val DATE_PREFERENCE = "date_preference"
        const val MEASUREMENT_PREFERENCE = "measurement_preference"
        const val CLUBS = "clubs"
        const val FTP = "ftp"
        const val BIKES = "bikes"
        const val SHOES = "shoes"
    }
}

object BikeContract {
    const val TABLE_BIKE = "bike_database"
    object Columns {
       const val BIKE_ID = "id"
        const val PRIMARY = "primary"
        const val NAME = "name"
        const val RESOURCE_STATE = "resource_state"
        const val DISTANCE = "distance"
    }
}

object ShoesContract {
    const val TABLE_SHOES = "shoes_database"
    object Columns {
        const val SHOES_ID = "id"
        const val PRIMARY = "primary"
        const val NAME = "name"
        const val RESOURCE_STATE = "resource_state"
        const val DISTANCE = "distance"
    }
}

object ClubContract {
    const val TABLE_CLUB = "club_database"
    object Columns {
       const val CLUB_ID = "id"
        const val RESOURCE_STATE = "resource_state"
        const val NAME = "name"
        const val PROFILE_MEDIUM = "profile_medium"
        const val PROFILE = "profile"
        const val COVER_PHOTO = "cover_photo"
        const val COVER_PHOTO_SMALL = "cover_photo_small"
        const val SPORT_TYPE = "sport_type"
        const val CITY = "city"
        const val STATE = "state"
        const val COUNTRY = "country"
        const val PRIVATE = "private"
        const val MEMBER_COUNT = "member_count"
        const val FEATURED = "featured"
        const val VERIFIED = "verified"
        const val URL = "url"
        const val MEMBERSHIP = "membership"
        const val ADMIN = "admin"
        const val OWNER = "owner"
        const val DESCRIPTION = "description"
        const val CLUB_TYPE = "club_type"
        const val POST_COUNT = "post_count"
        const val OWNER_ID = "owner_id"
        const val FOLLOWING_COUNT = "following_count"
    }
}

object ActivityContract {
    const val ACTIVITY_TABLE = "activity_entity"
    object Columns {
        const val external_id = "external_id"
        const val server_id = "id"
        const val name = "name"
        const val data_type = "type"
        const val date_create = "date"
        const val description = "description"
        const val trainer = "trainer"
        const val commute = "commute"
        const val distance = "distance"
        const val elapsed_time = "time"
    }
}

object LocalContract {
    const val LOCAL_TABLE = "local_entity"
    object Columns {
        const val external_id = "external_id"
        const val server_id = "id"
        const val name = "name"
        const val data_type = "type"
        const val date_create = "date"
        const val description = "description"
        const val trainer = "trainer"
        const val commute = "commute"
        const val distance = "distance"
        const val elapsed_time = "time"
    }
}