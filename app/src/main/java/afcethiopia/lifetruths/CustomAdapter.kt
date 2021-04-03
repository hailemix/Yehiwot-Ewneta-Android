package afcethiopia.lifetruths

import android.app.Activity
import android.widget.ArrayAdapter
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


/**
 * Created by user on 7/4/17.
 */
class CustomAdapter (private val getContext : Context ,private val CustomLayoutId: Int, private val custom_item : ArrayList<CustomLayout>) :
        ArrayAdapter<CustomLayout> (getContext,CustomLayoutId,custom_item) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var row = convertView    // mutable variable
        val theViewHolder: ViewHolder   // immutable variable


        if (row == null) {

            val inflater = (getContext as Activity).layoutInflater
            row = inflater.inflate(CustomLayoutId, parent, false)

            theViewHolder = ViewHolder()
            theViewHolder.img = row!!.findViewById(R.id.img)
            row.tag = theViewHolder

        } else {

            theViewHolder = row.tag as ViewHolder
        }

        val item = custom_item[position]
        theViewHolder.img!!.setImageResource(item.image)
        return row
    }

    class ViewHolder {
        internal var img: ImageView? = null
    }
}