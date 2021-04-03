package afcethiopia.lifetruths
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener{

       private var lister : ImageView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val gridView = findViewById<GridView>(R.id.GV)
        val adapter = CustomAdapter(this, R.layout.customlayout, data)
        var mySelectedList = ""
         lister = findViewById(R.id.z_lister)
         registerForContextMenu(lister)
         lister!!.setOnClickListener(this)
         gridView.adapter = adapter
         gridView.isVerticalScrollBarEnabled = false


      gridView.onItemClickListener = OnItemClickListener { _, _, position, _ ->

          intent = Intent(this@MainActivity, MainList::class.java)
          when (position) {

              0 -> {
                  mySelectedList = "zOne"
              }
              1 -> {
                  mySelectedList = "zTwo"
              }
              2 -> {
                  mySelectedList = "zThree"
              }
              3 -> {
                  mySelectedList = "zFour"
              }
              4 -> {
                  mySelectedList = "zFive"
              }
              5 -> {
                  mySelectedList = "zSix"
              }
              6 -> {
                  mySelectedList = "zSeven"
              }
              7 -> {
                  mySelectedList = "zEight"
              }
              8 -> {
                  mySelectedList = "zNine"
              }
              9 -> {
                  mySelectedList = "zTen"
              }
             else -> {
                 Log.d("error", "Please check your code!")
             }
         }
          intent.putExtra("contentName", mySelectedList)
          intentController(intent)
     }

    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onClick(v: View?) {

        this.openContextMenu(v)
        val anim = AnimationSet(false)
        anim.addAnimation(TranslateAnimation(30f, 0f, 0f, 0f))
        anim.addAnimation(RotateAnimation(180f, 0f))
        lister!!.animation = anim
        anim.duration = 600
        lister!!.visibility = View.GONE

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.about -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ethiocoders.dev"))
                intentController(intent)
                return true
            }

            R.id.more_apps -> {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=4732824418136294157"))
                intentController(intent)
                return true
            }

            R.id.rate -> {

                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=afcethiopia.lifetruths"))
                    startActivity(intent)
                } catch (e: Throwable) {

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=afcethiopia.lifetruths"))
                    startActivity(intent)
                }
                return true
            }

            R.id.youtube_videos -> {

                val videoUrl = "https://youtu.be/VXYTs7rkqec"
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                    startActivity(intent)
                } catch (e: Throwable) {
                    print("Error in Connecting to the server.Please check at $e")
                }
                return true
            }
            R.id.action_privacy -> {

                val linkUrl = "https://ethiocoderzone.wordpress.com/2018/10/11/%E1%8B%A8%E1%88%85%E1%8B%AD%E1%8B%88%E1%89%B5-%E1%8A%A5%E1%8B%89%E1%8A%90%E1%89%B3-ethiopian-life-truth/"
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                    startActivity(intent)
                } catch (e: Throwable) {
                    print("Error in Connecting to the server.Please check at $e")
                }
                return true
            }

            else -> return super.onContextItemSelected(item)
        }

        }

    private val data: ArrayList<CustomLayout>
        get()

    {
        val itemList : ArrayList<CustomLayout> = ArrayList()
        itemList.add(CustomLayout(R.drawable.gone))
        itemList.add(CustomLayout(R.drawable.gtwo))
        itemList.add(CustomLayout(R.drawable.gthree))
        itemList.add(CustomLayout(R.drawable.gfour))
        itemList.add(CustomLayout(R.drawable.gfive))
        itemList.add(CustomLayout(R.drawable.gsix))
        itemList.add(CustomLayout(R.drawable.gseven))
        itemList.add(CustomLayout(R.drawable.geight))
        itemList.add(CustomLayout(R.drawable.gnine))
        itemList.add(CustomLayout(R.drawable.gten))
        return itemList

    }

    override fun onContextMenuClosed(menu: Menu) {
        super.onContextMenuClosed(menu)
        lister!!.visibility = View.VISIBLE
    }

    private  fun intentController(intent: Intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}

