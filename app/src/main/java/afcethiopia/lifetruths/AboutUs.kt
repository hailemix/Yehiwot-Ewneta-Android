package afcethiopia.lifetruths
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

/**
 * Created by user on 10/27/17.
 */

class AboutUs : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us)

        val imageView = findViewById<ImageView>(R.id.Us)
        Glide.with(this).load(R.drawable.about_us).into(imageView)

    }

}
