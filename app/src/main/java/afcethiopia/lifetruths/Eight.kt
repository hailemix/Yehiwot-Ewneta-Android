package afcethiopia.lifetruths
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*

/**
 * Created by HaileApp on 9/4/17.
 *
 */

class Eight : AppCompatActivity() {


    private var mTypeFace :Typeface ?= null
    private lateinit var mInterstitialAd: InterstitialAd

    companion object {

        private var clickCounter = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)


        val myListView = findViewById<ListView>(R.id.LV)
        val noConnection = findViewById<TextView>(R.id.no_connection)
        val connectionProgress = findViewById<ProgressBar>(R.id.progress_bar)
        val zButton = findViewById<Button>(R.id.zConnect)
        val interstitialController = findViewById<RelativeLayout>(R.id.mInterstitialAdBreak)

        MobileAds.initialize(this, "ca-app-pub-9156727777369518/1421205842")

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9156727777369518/1421205842"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
       myListView.emptyView = noConnection

        val childArrayHolder = ArrayList<String>()
        val mainLister = ArrayList<String>()

        myListView.isVerticalScrollBarEnabled = false

        mTypeFace = Typeface.createFromAsset(assets,"fonts/list_font.ttf")

        val databaseReference = FirebaseDatabase.getInstance().getReference("zEight")
        databaseReference.keepSynced(true)

        fun so() {

            val arrayAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, mainLister) {


                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                    val textView = super.getView(position, convertView, parent) as TextView
                    textView.textSize = 20f
                    textView.typeface = mTypeFace
                    return textView
                }
            }

           myListView.adapter = arrayAdapter
        }

        zButton.setOnClickListener {

            noConnection.visibility = View.INVISIBLE
            connectionProgress.visibility = View.VISIBLE

            connectionProgress.postDelayed({
                if(connectionProgress.isShown){

                    zButton.visibility = View.INVISIBLE
                }

            },1000)

            zButton.postDelayed({

                if(!noConnection.isShown){

                    noConnection.visibility = View.VISIBLE
                    connectionProgress.visibility = View.INVISIBLE
                    zButton.visibility = View.VISIBLE
                }

            },10000)

        }

        databaseReference.addChildEventListener(object : ChildEventListener {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                val playerName: String? = p0.getValue(String::class.java)
                childArrayHolder.add(playerName!!)


                myListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

                    clickCounter += 1

                    if(clickCounter % 4 == 0){

                        interstitialController.visibility = View.VISIBLE


                        Handler().postDelayed({


                            if (mInterstitialAd.isLoaded) {

                                mInterstitialAd.show()

                            } else {

                                Log.d("TAG", "The interstitial ad is not ready yet")
                            }

                        }, 3000)

                        Handler().postDelayed({

                            interstitialController.visibility = View.GONE

                        },4000)

                    } else {


                        val intent = Intent(this@Eight, MainDetail::class.java)
                        intent.putExtra("key", childArrayHolder[position])
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                        try{

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@Eight).toBundle())
                            }
                            else {

                                startActivity(intent)
                            }

                        } catch(e: NoSuchMethodError){

                            startActivity(intent)
                        }
                    }
                }

            }
            override fun onCancelled(p0: DatabaseError) = Unit
            override fun onChildMoved(p0: DataSnapshot, p1: String?) = Unit
            override fun onChildChanged(p0: DataSnapshot, p1: String?) = Unit
            override fun onChildRemoved(p0: DataSnapshot) = Unit
        })

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                for (player in p0.children) {
                    println(player.key)
                    Log.i("zEight", player.key.toString())
                    mainLister.add(player.key.toString())
                }
                so()
            }

            override fun onCancelled(p0: DatabaseError): Unit =
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        })
    }
}






















