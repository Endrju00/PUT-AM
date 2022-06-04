package com.example.listdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val routes: Array<String>,private val context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val route = routes[position]
        holder.itemTitle.text = route

        val number = route.filter { it.isDigit() }.toInt()

        if (number % 2 == 0)
            holder.itemImage.setImageResource(R.drawable.route)
        else
            holder.itemImage.setImageResource(R.drawable.route2)
    }

    override fun getItemCount(): Int {
        return routes.size
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)

            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val route = routes[position]
                val description = DB(context).getDescription(route)
                val imageId = if ((route.filter{ it.isDigit() }).toInt()%2==0) 1 else 2
                replaceFragment(DetailFragment.newInstance(route, description.toString(), imageId))
            }
        }

        private fun replaceFragment(fragment: Fragment) {
            val smallestWidth = itemView.resources.configuration.smallestScreenWidthDp
            val manager = (itemView.context as FragmentActivity).supportFragmentManager
            val fragmentTransaction = manager.beginTransaction()

            if (smallestWidth < 720)
                fragmentTransaction.replace(R.id.fragment_container, fragment)
            else
                fragmentTransaction.replace(R.id.fragment_container2, fragment)

            fragmentTransaction.commit()
        }
    }
}