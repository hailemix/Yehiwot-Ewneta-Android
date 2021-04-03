package afcethiopia.lifetruths
import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.FirebaseDatabase

open class MyApplication : Application() {
    private var appOpenManager: AppOpenManager? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        MobileAds.initialize(this) { }
        appOpenManager = AppOpenManager(this)
    }
}