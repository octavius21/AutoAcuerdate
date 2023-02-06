/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 06/02/23 10:08
 *   File: LocationAdapter.kt
 *   Last modified: 06/02/23 10:08
 *   Last change:
 *     Change Name ->
 *     Change Date -> 06/02/23 10:08
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.databinding.LocationItemBinding
import com.luocz.autoacuerdate.models.LocationX
import com.luocz.autoacuerdate.views.activities.MainActivity
import com.luocz.autoacuerdate.views.holders.LocationViewHolder

class LocationAdapter(private val context: Context, private val locations: LocationX):
    RecyclerView.Adapter<LocationViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(context))
        return LocationViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations.location?.get(position)!!)
        holder.itemView.setOnClickListener{
            if(context is MainActivity) context.selectedLocation(locations.location?.get(position)!!)
        }
    }

    override fun getItemCount(): Int = locations.location?.size!!
}