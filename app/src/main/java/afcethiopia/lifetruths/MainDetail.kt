    package afcethiopia.lifetruths
    import android.content.Intent
    import android.graphics.Typeface
    import android.os.Build
    import android.os.Bundle
    import android.util.DisplayMetrics
    import android.widget.FrameLayout
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
    class MainDetail(bannerDetailId: BannerIdCode = BannerImpl()) : AppCompatActivity(), BannerIdCode by bannerDetailId {

        private var mTextView: TextView? = null
        private var mScrollView: NestedScrollView? = null
        private var zTypeFace: Typeface? = null
        private lateinit var bannerAdView : FrameLayout
        private lateinit var adView: AdView

       private val adSize: AdSize
            get() {

                val outMetrics = DisplayMetrics()
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    val display = display
                    display?.getRealMetrics(outMetrics)
                } else {
                    @Suppress("DEPRECATION")
                    val display = windowManager.defaultDisplay
                    @Suppress("DEPRECATION")
                    display.getRealMetrics(outMetrics)
                }
                val density = outMetrics.density
                var adWidthPixels = adView.width.toFloat()
                if(adWidthPixels == 0f){
                    adWidthPixels = outMetrics.widthPixels.toFloat()
                }
                val adWidth = (adWidthPixels / density).toInt()
                return AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, adWidth)
            }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.detailview)
            bannerAdView = findViewById(R.id.detailAdaptiveBanner)
            adView = AdView(this)
            bannerAdView.addView(adView)
            loadBanner()
            val myButton = findViewById<FloatingActionButton>(R.id.butOne)
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
                myButton.isSoundEffectsEnabled = true

                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_SEND
                    myIntent.type = "text/plain"              //   In java type is called as setType...
                    myIntent.putExtra(Intent.EXTRA_TEXT, mTextView!!.text.toString())
                    startActivity(Intent.createChooser(myIntent,resources.getText(R.string.title)))
            }
            myTrace.stop()
        }

        private fun loadBanner() {
            adView.adUnitId = getBannerId()
            adView.adSize = adSize
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }















