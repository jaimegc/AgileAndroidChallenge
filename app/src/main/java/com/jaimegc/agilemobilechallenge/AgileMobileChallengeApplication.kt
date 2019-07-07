package com.jaimegc.agilemobilechallenge

import android.app.Application
import com.jaimegc.agilemobilechallenge.di.KodeinModules
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class AgileMobileChallengeApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@AgileMobileChallengeApplication), allowOverride = true)

        val modules = KodeinModules(this@AgileMobileChallengeApplication)

        import(modules.database, allowOverride = true)
        import(modules.roomDaos, allowOverride = true)
        import(modules.repositories, allowOverride = true)
        import(modules.dataSources, allowOverride = true)
        import(modules.apiClients, allowOverride = true)
        import(modules.others, allowOverride = true)
    }
}
