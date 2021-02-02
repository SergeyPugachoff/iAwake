package com.sergey.pugachov.iawake.tools.player

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

object PlayerCacheFactory {
    private const val CACHE_SIZE : Long = 100 * 1024 * 1024
    private const val CACHE_FOLDER = "media"

    private lateinit var cache: SimpleCache

    fun getCache(context: Context): SimpleCache {
        if (!::cache.isInitialized) {
            val cacheFolder = File(context.cacheDir, CACHE_FOLDER)
            val cacheEvictor = LeastRecentlyUsedCacheEvictor(CACHE_SIZE)
            val databaseProvider: DatabaseProvider = ExoDatabaseProvider(context)
            cache = SimpleCache(cacheFolder, cacheEvictor, databaseProvider)
        }
        return cache
    }
}