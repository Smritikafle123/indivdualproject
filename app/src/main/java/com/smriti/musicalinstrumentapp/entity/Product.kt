package com.smriti.musicalinstrumentapp.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
        @PrimaryKey
        var _id: String = "",
        var pname: String? = null,
        var pdesc: String? = null,
        var pprice: String? = null,
        var pimage: String? = null,
        var prating: String? = null
):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString()!!,
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(_id)
                parcel.writeString(pname)
                parcel.writeString(pdesc)
                parcel.writeString(pprice)
                parcel.writeString(pimage)
                parcel.writeString(prating)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Product> {
                override fun createFromParcel(parcel: Parcel): Product {
                        return Product(parcel)
                }

                override fun newArray(size: Int): Array<Product?> {
                        return arrayOfNulls(size)
                }
        }
}