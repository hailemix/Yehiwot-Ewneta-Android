package afcethiopia.lifetruths

import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by user on 11/2/17.
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {

        Log.d(TAG, "From:" + p0.from)

        if(p0.data.isNotEmpty()){

            Log.d(TAG, "Notification payload: " + p0.data)


            if(p0.notification != null) {

                Log.d(TAG, "Message Notification Body: " + p0.notification!!.body)
            }
        }

    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("NEW_TOKEN",p0)
    }

    companion object {

        private const val TAG = "FCM SERVICE"
    }
}
