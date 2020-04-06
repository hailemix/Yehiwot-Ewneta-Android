package afcethiopia.lifetruths

import android.app.Application

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by user on 9/7/17.
 *
 */

class FiresafePersistence : Application() {


    override fun onCreate() {
        super.onCreate()
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }

    }





