package com.sergey.pugachov.iawake

private const val kotlinVersion = "1.4.21"
private const val roomVersion = "2.2.5"

object Dependencies {

    object Build {
        const val compileSdkVersion = 30
        const val buildToolsVersion = "30.0.3"
        const val minSdkVersion = 23
        const val targetSdkVersion = 30
    }

    object Kotlin {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    }

    object AndroidX {
            const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }

}