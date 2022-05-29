package com.example.listdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val routes: Array<String>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private val descriptions = mapOf(
        "Droga 1" to "Opis drogi 1\nDługość trasy: 2h\nTrasa przebiega przez miasta: Poznań",
        "Droga 2" to "Opis drogi 2\nDługość trasy: 1h\nTrasa przebiega przez miasta: Poznań, Luboń",
        "Droga 3" to "Opis drogi 3\nDługość trasy: 1.5h\nTrasa przebiega przez miasta: Warszawa",
        "Droga 4" to "Opis drogi 4\nDługość trasy: 1h\nTrasa przebiega przez miasta: Kraków",
        "Droga 5" to "Opis drogi 5\nDługość trasy: 2.5h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot",
        "Droga 6" to "Opis drogi 6\nDługość trasy: 0.5h\nTrasa przebiega przez miasta: Poznań",
        "Droga 7" to "Opis drogi 7\nDługość trasy: 3.2h\nTrasa przebiega przez miasta: Poznań, Luboń",
        "Droga 8" to "Opis drogi 8\nDługość trasy: 0.2h\nTrasa przebiega przez miasta: Warszawa",
        "Droga 9" to "Opis drogi 9\nDługość trasy: 3h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot",
        "Droga 10" to "Opis drogi 10\nDługość trasy: 0.5h\nTrasa przebiega przez miasta: Poznań",
        "Droga 11" to "Opis drogi 11\nDługość trasy: 3h\nTrasa przebiega przez miasta: Poznań, Luboń",
        "Droga 12" to "Opis drogi 12\nDługość trasy: 1h\nTrasa przebiega przez miasta: Warszawa",
        "Droga 13" to "Opis drogi 13\nDługość trasy: 3.5h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot",
        "Droga 14" to "Opis drogi 14\nDługość trasy: 2h\nTrasa przebiega przez miasta: Poznań",
        "Droga 15" to "Opis drogi 15\nDługość trasy: 4h\nTrasa przebiega przez miasta: Poznań, Luboń",
        "Droga 16" to "Opis drogi 16\nDługość trasy: 2h\nTrasa przebiega przez miasta: Warszawa",
        "Droga 17" to "Opis drogi 17\nDługość trasy: 4.5h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot",
        "Droga 18" to "Opis drogi 18\nDługość trasy: 1.5h\nTrasa przebiega przez miasta: Poznań",
        "Droga 19" to "Opis drogi 19\nDługość trasy: 5h\nTrasa przebiega przez miasta: Poznań, Luboń",
        "Droga 20" to "Opis drogi 20\nDługość trasy: 1.2h\nTrasa przebiega przez miasta: Warszawa",
    )

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
                val description = descriptions[route]
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