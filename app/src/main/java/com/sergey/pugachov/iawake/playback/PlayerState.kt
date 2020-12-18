package com.sergey.pugachov.iawake.playback

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class PlayerState : Parcelable {
    @Parcelize
    object Playing : PlayerState()

    @Parcelize
    object Paused : PlayerState()

    @Parcelize
    object Stopped : PlayerState()

    @Parcelize
    object Error : PlayerState()
}
