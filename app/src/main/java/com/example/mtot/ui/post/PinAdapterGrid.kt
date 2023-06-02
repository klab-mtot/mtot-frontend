package com.example.mtot.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.R

class PinAdapterGrid(var list: ArrayList<Int>): RecyclerView.Adapter<PinAdapterGrid.GridAdapter>() {

    class GridAdapter(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.pin_image_items, parent, false)
        return GridAdapter(view)
    }

    override fun onBindViewHolder(holder: GridAdapter, position: Int) {
    }

    override fun getItemCount(): Int {
        return list.size
    }
}