package afcethiopia.lifetruths
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


const val mobileNumber = "*806*0908766344*1%23"

class SupportUsClass : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          setContentView(R.layout.support_us)
        val supportUsImage = findViewById<ImageView>(R.id.supportPicture)

        Glide.with(this).load(R.drawable.support_us).into(supportUsImage)
        val supportButton = findViewById<Button>(R.id.support_now)
         supportButton.setOnClickListener {
             val mobileNumber = mobileNumber
             val intent = Intent()
             intent.action = Intent.ACTION_DIAL
             intent.data = Uri.parse("tel: $mobileNumber")
             startActivity(intent)
         }
    }
}