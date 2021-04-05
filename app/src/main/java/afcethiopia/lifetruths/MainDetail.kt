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
    class MainDetail : AppCompatActivity() {

        private var mTextView: TextView? = null
        private var mScrollView: NestedScrollView? = null
        private var zTypeFace: Typeface? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.detailview)
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
    }















