package afcethiopia.lifetruths

import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.firebase.database.*
import java.util.*

/**
 * Created by HaileApp on 9/4/17.
 *
 */
class MainList : AppCompatActivity() {

    private var mTypeFace : Typeface ?= null
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var adContainerView : FrameLayout
    private lateinit var adView: AdView
    private val adSize: AdSize
        get(){
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = adView.width.toFloat()
            if(adWidthPixels == 0f){
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, adWidth)
        }
    companion object {
        private var clickCounter = 0
        private const val AD_UNIT_ID = "ca-app-pub-9156727777369518/1043244979"  // Real Banner Ad
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)

        MobileAds.initialize(this)
        adContainerView = findViewById(R.id.myAdaptiveBanner)
        adView = AdView(this)
        adContainerView.addView(adView)
        loadBanner()

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9156727777369518/1421205842"   // Real Interstitial Ad
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        val myListView = findViewById<ListView>(R.id.LV)
        val noConnection = findViewById<TextView>(R.id.no_connection)
        val connectionProgress = findViewById<ProgressBar>(R.id.progress_bar)
        val zButton = findViewById<Button>(R.id.zConnect)
        val interstitialController = findViewById<RelativeLayout>(R.id.mInterstitialAdBreak)
        val childArrayHolder = ArrayList<String>()
        val mainLister = ArrayList<String>()
        myListView.emptyView = noConnection
        myListView.isVerticalScrollBarEnabled = false
        mTypeFace = Typeface.createFromAsset(assets,"fonts/list_font.ttf")
        noConnection.typeface = mTypeFace
        zButton.typeface = mTypeFace

        val databaseReference = FirebaseDatabase.getInstance().getReference(intent.getStringExtra("contentName")!!)
        databaseReference.keepSynced(true)

        fun contentList() {

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
                    if (clickCounter % 4 == 0) {
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
                        val intent = Intent(this@MainList, MainDetail::class.java)
                        intent.putExtra("key", childArrayHolder[position])

                        try{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                startActivity(intent, makeSceneTransitionAnimation(this@MainList).toBundle())
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
                    mainLister.add(player.key.toString())
                }
                contentList()
            }
            override fun onCancelled(p0: DatabaseError) = Unit
        })
    }
    private fun loadBanner(){
        adView.adUnitId = AD_UNIT_ID
        adView.adSize = adSize
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}






















