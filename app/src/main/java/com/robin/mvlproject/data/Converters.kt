package com.robin.mvlproject.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.data.entities.LabelType

class Converters {

    @TypeConverter
    fun toLabelType(value: Int) = enumValues<LabelType>()[value]

    @TypeConverter
    fun fromLabelType(value: LabelType) = value.ordinal

    @TypeConverter
    fun labelToJson(value: Label): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToLabel(value: String): Label = Gson().fromJson(value, Label::class.java)

}