package com.flatcode.simpleadvancedapps.countries.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Country(
    @ColumnInfo(name = "name") @SerializedName("name") val countryName: String?,
    @ColumnInfo(name = "region") @SerializedName("region") val countryRegion: String?,
    @ColumnInfo(name = "capital") @SerializedName("capital") val countryCapital: String?,
    @ColumnInfo(name = "currency") @SerializedName("currency") val countryCurrency: String?,
    @ColumnInfo(name = "language") @SerializedName("language") val countryLanguage: String?,
    @ColumnInfo(name = "flag") @SerializedName("flag") val imageURL: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @IgnoredOnParcel
    var uuid: Int = 0
}