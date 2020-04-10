    package afcethiopia.lifetruths
    import android.content.Intent
    import android.graphics.Typeface
    import android.os.Bundle
    import android.os.Handler
    import android.view.View
    import android.widget.RelativeLayout
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.widget.NestedScrollView
    import com.google.android.gms.ads.AdRequest
    import com.google.android.gms.ads.AdView
    import com.google.android.gms.ads.InterstitialAd
    import com.google.android.gms.ads.MobileAds
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
        private lateinit var mAdView : AdView

        companion object {

            var detailControl = 0
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.detailone)
            MobileAds.initialize(this, "ca-app-pub-9156727777369518/1421205842")

            mInterstitialAd = InterstitialAd(this)
            mInterstitialAd.adUnitId = "ca-app-pub-9156727777369518/1421205842"
            mInterstitialAd.loadAd(AdRequest.Builder().build())

            mAdView = findViewById(R.id.myBanner)
            mAdView.loadAd(AdRequest.Builder().build())


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
    }















