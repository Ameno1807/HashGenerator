package com.example.hashgenerator

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class HashModel (val text: String) : Parcelable