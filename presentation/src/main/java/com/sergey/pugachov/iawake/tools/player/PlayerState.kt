package com.sergey.pugachov.iawake.tools.player

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class PlayerState : Parcelable {
    @Parcelize
    object Buffering : PlayerState()

    @Parcelize
    object Playing : PlayerState()

    @Parcelize
    object Paused : PlayerState()

    @Parcelize
    object Stopped : PlayerState()

    @Parcelize
    object Error : PlayerState()
}
