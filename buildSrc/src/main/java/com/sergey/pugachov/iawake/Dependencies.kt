package com.sergey.pugachov.iawake

object Versions {
    const val kotlin = "1.4.21"
    const val koin = "2.2.1"
    const val room = "2.2.5"
    const val coil = "1.1.0"
    const val exoplayer = "2.12.2"
    const val material = "1.2.1"
    const val constraintlayout = "2.0.4"
    const val navigation = "2.3.2"
    const val androidXCore = "1.3.2"
    const val appcompat = "1.2.0"
    const val swiperefreshlayout = "1.1.0"
    const val localbroadcastmanager = "1.0.0"
    const val retrofit = "2.9.0"
    const val okHttp = "4.9.0"
    const val lifecycle = "2.2.0"
    const val stetho = "1.5.1"
    const val androidXJunit = "1.1.2"
    const val androidXEspresso = "3.3.0"
    const val junit = "4.13.1"
    const val gson = "2.8.6"
}

object Dependencies {

    object Build {
        const val compileSdkVersion = 30
        const val buildToolsVersion = "30.0.3"
        const val minSdkVersion = 23
        const val targetSdkVersion = 30
    }

    object Kotlin {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:${Versions.androidXCore}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
        const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
        const val localbroadcastmanager = "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.localbroadcastmanager}"

        object Lifecycle{
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            const val java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        }

        object Navigation{
            const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }

        object Room{
            const val room = "androidx.room:room-ktx:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
            const val testing = "androidx.room:room-testing:${Versions.room}"
        }

        object Test{
            const val junit = "androidx.test.ext:junit:${Versions.androidXJunit}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidXEspresso}"
        }
    }

    object Squareup{
        object Retrofit{
            const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
            const val converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        }
        object OkHttp{
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        }
    }

    object Facebook{
        const val stetho = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    }

    object Koin {
        const val koin = "org.koin:koin-android:${Versions.koin}"
        const val koinScope = "org.koin:koin-android-scope:${Versions.koin}"
        const val koinAndroidViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:${Versions.coil}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val exoPlayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayer}"
        const val gson ="com.google.code.gson:gson:${Versions.gson}"
    }

    object Test{
        const val junit = "junit:junit:${Versions.junit}"
    }
}