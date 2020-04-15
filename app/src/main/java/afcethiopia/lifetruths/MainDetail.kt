    package afcethiopia.lifetruths
    import android.content.Intent
    import android.graphics.Typeface
    import android.os.Bundle
    import android.os.Handler
    import android.util.DisplayMetrics
    import android.view.View
    import android.widget.FrameLayout
    import android.widget.RelativeLayout
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.widget.NestedScrollView
    import com.google.android.gms.ads.*
    import com.google.android.material.floatingactionbutton.FloatingActionButton
    import com.google.firebase.perf.FirebasePerformance
    import com.google.firebase.perf.metrics.Trace


    /**
     * Created by user on 7/6/17.
     *
     */
    class MainDetail : AppCompatActivity() {

        private var mTextView: TextView? = null
        private var mScrollView: NestedScrollView? = null
        private var zTypeFace: Typeface? = null
        private lateinit var mInterstitialAd: InterstitialAd
        private lateinit var adContainerView: FrameLayout
        private lateinit var adView: AdView
        private val adSize: AdSize
            get() {
                val display = windowManager.defaultDisplay
                val outMetrics = DisplayMetrics()
                display.getMetrics(outMetrics)

                val density = outMetrics.density

                var adWidthPixels = adView.width.toFloat()
                if (adWidthPixels == 0f) {
                    adWidthPixels = outMetrics.widthPixels.toFloat()
                }
                val adWidth = (adWidthPixels / density).toInt()
                return AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, adWidth)

            }

        companion object {
            var detailControl = 0
            private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111" // TODO: This is a test banner ad!
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.detailview)
            MobileAds.initialize(this, "ca-app-pub-9156727777369518/1421205842")

            adContainerView = findViewById(R.id.myAdaptBanner)
            adView = AdView(this)
            adContainerView.addView(adView)
            loadBanner()
            mInterstitialAd = InterstitialAd(this)
            //  mInterstitialAd.adUnitId = "ca-app-pub-9156727777369518/1421205842"  // Real Ad
            mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"  // Test Ad
            mInterstitialAd.loadAd(AdRequest.Builder().build())

            val myButton = findViewById<FloatingActionButton>(R.id.butOne)
            val interstitialBreak = findViewById<RelativeLayout>(R.id.detailInterstitialBreak)

            myButton.show()

            mTextView = findViewById(R.id.myText)
            mScrollView = findViewById(R.id.zScroll)
            zTypeFace = Typeface.createFromAsset(assets, "fonts/amharic.ttf")
            mTextView!!.typeface = zTypeFace


            val intent = intent

            val myTrace : Trace = FirebasePerformance.getInstance().newTrace("This_is_a_test_trace") // This code is used to trace any code defects found.
            myTrace.start()

            if (intent.hasExtra("key")) {


                mTextView!!.text = intent?.getStringExtra("key")?.replace("\\n","\n")?.replace("\\t","\t")
                mScrollView!!.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->

                    if (scrollY >= oldScrollY) {

                        myButton.postDelayed({
                            myButton.hide()
                        }
                                , 1000)

                    } else {
                        myButton.show()
                    }
                }

            }

            myButton.setOnClickListener {


                detailControl += 1
                myButton.isSoundEffectsEnabled = true


                if(detailControl % 3 == 0){

                    interstitialBreak.visibility = View.VISIBLE
                    myButton.hide()

                    Handler().postDelayed({

                        if (mInterstitialAd.isLoaded) {

                            mInterstitialAd.show()
                        }

                    },3000)

                    Handler().postDelayed({

                        interstitialBreak.visibility = View.GONE
                        myButton.show()

                    },4000)

                }

                else {

                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_SEND
                    myIntent.type = "text/plain"               //   In java type is called as setType...
                    myIntent.putExtra(Intent.EXTRA_TEXT, mTextView!!.text.toString())
                    startActivity(Intent.createChooser(myIntent,resources.getText(R.string.title)))

                }

            }

            myTrace.stop()

        }

        private fun loadBanner() {
            adView.adUnitId = AD_UNIT_ID
            adView.adSize = adSize
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }















