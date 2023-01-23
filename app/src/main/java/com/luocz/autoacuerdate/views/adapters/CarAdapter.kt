/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 12:10
 *   File: CarAdapter.kt
 *   Last modified: 21/01/23 9:15
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 12:10
 *     Description ->
 *
 * **************************************************************************
 */


package com.luocz.autoacuerdate.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.views.holders.CarViewHolder
import com.luocz.autoacuerdate.models.Car
import com.luocz.autoacuerdate.databinding.CarItemBinding
import com.luocz.autoacuerdate.models.CarX
import com.luocz.autoacuerdate.views.activities.MainActivity

class CarAdapter(private val context: Context, private val cars: CarX):
    RecyclerView.Adapter<CarViewHolder>() {
//     Podria funcionar si tengo boton en el RV
//    var onItemClick:((Car) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = CarItemBinding.inflate(LayoutInflater.from(context))
        return CarViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
//       holder.bind(cars.car?.get(position)!!,onItemClick)
       holder.bind(cars.car?.get(position)!!)

        holder.itemView.setOnClickListener {
            if(context is MainActivity) context.selectedCar(cars.car?.get(position)!!)
        }
    }

    override fun getItemCount(): Int = cars.car?.size!!
}