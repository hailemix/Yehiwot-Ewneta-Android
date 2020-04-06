package afcethiopia.lifetruths

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


/**
 * Created by user on 11/6/17.
 */

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)



        val splashImage = findViewById<ImageView>(R.id.splashPicture)
        val firstAnimation = AnimationUtils.loadAnimation(baseContext, R.anim.splashanim)

        Glide.with(this).load(R.drawable.splash).into(splashImage)
        splashImage.startAnimation(firstAnimation)

        firstAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationRepeat(p0: Animation?) = Unit
            override fun onAnimationStart(p0: Animation?) = Unit
            override fun onAnimationEnd(animation: Animation?) {

                finish()
                val intent = Intent(this@Splash, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }

}




