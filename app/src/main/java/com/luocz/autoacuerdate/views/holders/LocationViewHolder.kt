/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 06/02/23 13:54
 *   File: LocationViewHolder.kt
 *   Last modified: 06/02/23 11:57
 *   Last change:
 *     Change Name ->
 *     Change Date -> 06/02/23 13:54
 *     Description ->
 *
 * **************************************************************************
 */



package com.luocz.autoacuerdate.views.holders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.databinding.LocationItemBinding
import com.luocz.autoacuerdate.models.Location


class LocationViewHolder(val binding: LocationItemBinding, val context: Context):
    RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location){
            binding.tvLocationName.text = location.location_name
            binding.tvLocationAddress.text = location.address
            binding.tvCountry.text = location.nation
        }
}
