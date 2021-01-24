package com.sergey.pugachov.iawake.di.configuration

import com.sergey.pugachov.iawake.data.di.DbSettings
import com.sergey.pugachov.iawake.data.di.NetworkSettings

object Config : DbSettings, NetworkSettings {

    override val dbName: String
        get() = "IAwake.db"

    override val baseUrl: String
        get() = "https://api.iawaketechnologies.com/"

    override val timeout: Long
        get() = 30L

}