package afcethiopia.lifetruths
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity(),View.OnClickListener{

    private var lister : ImageView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val gridView = findViewById<GridView>(R.id.GV)
        val adapter = CustomAdapter(this,R.layout.customlayout,data)


         lister = findViewById(R.id.z_lister)
         registerForContextMenu(lister)
         lister!!.setOnClickListener(this)
         gridView.adapter = adapter
         gridView.isVerticalScrollBarEnabled = false

      gridView.onItemClickListener = OnItemClickListener { _ ,_ , position ,_ ->

         var intent  = Intent()
         when (position) {

             0 -> {
                  intent = Intent(this@MainActivity, One::class.java)
             }
             1 -> {

                  intent = Intent(this@MainActivity, Two::class.java)
             }
             2 -> {

               intent = Intent(this@MainActivity,Three::class.java)
             }

             3 -> {

                  intent = Intent(this@MainActivity,Four::class.java)
             }
             4 -> {

                 intent = Intent(this@MainActivity,Five::class.java)

             }
             5 -> {

                  intent = Intent(this@MainActivity,Six::class.java)
             }
             6 -> {
                  intent = Intent (this@MainActivity,Seven::class.java)
             }

             7 -> {
                  intent = Intent(this@MainActivity,Eight::class.java)
                 intentController(intent)
             }

             8 -> {
                 intent = Intent(this@MainActivity,Nine::class.java)
             }
             9 -> {
                  intent = Intent (this@MainActivity,Ten::class.java)
             }
             else -> {
                 Log.d("error","Please check your code!")
             }
         }
          intentController(intent)
     }

    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main,menu)
    }

    override fun onClick(v: View?) {

        this.openContextMenu(v)
        val anim = AnimationSet(false)
        anim.addAnimation(TranslateAnimation(30f,0f,0f,0f))
        anim.addAnimation(RotateAnimation(180f,0f))
        lister!!.animation = anim
        anim.duration = 600
        lister!!.visibility = View.GONE

    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId) {
            R.id.about -> {
                val intent = Intent(this, AboutUs::class.java)
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
                    val intent = Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=afcethiopia.lifetruths"))
                    startActivity(intent)
                } catch (e : Throwable ){

                    val intent = Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=afcethiopia.lifetruths"))
                    startActivity(intent)
                }
                return true
            }

            R.id.action_privacy -> {

                val linkUrl = "https://ethiocoderzone.wordpress.com/2018/10/11/%E1%8B%A8%E1%88%85%E1%8B%AD%E1%8B%88%E1%89%B5-%E1%8A%A5%E1%8B%89%E1%8A%90%E1%89%B3-ethiopian-life-truth/"
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                    startActivity(intent)
                } catch(e : Throwable){
                 print("Error in Connecting to the server.Please check at $e")
                }
                return true
            }

            else -> return super.onContextItemSelected(item)
        }

        }

    val data : ArrayList <CustomLayout>
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

