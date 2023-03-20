/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 12:23
 *   File: ViewHolder.kt
 *   Last modified: 22/01/23 12:23
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 12:23
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.holders

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.models.Car
import com.luocz.autoacuerdate.databinding.CarItemBinding
import com.luocz.autoacuerdate.utils.Constants

class CarViewHolder(val binding: CarItemBinding, val context: Context):RecyclerView.ViewHolder(binding.root) {

    fun bind(car: Car){
        binding.tvBrand.text = car.brand
        binding.tvModel.text = car.model.toString()
        binding.tvLicensePlate.text = car.license_plate
        Log.d(Constants.LOGTAG_DEV,"Obtenido API:${car.color_plate.toString().uppercase()}")
        Log.d(Constants.LOGTAG_DEV,"Obtenido context1:${context.getString(R.string.color_plate_yellow)}")
        Log.d(Constants.LOGTAG_DEV,"Obtenido context2:${context.getDrawable(R.color.color_plate_blue)}")
//        test
        Log.d(Constants.LOGTAG_DEV,"Obtenido context1:${R.color.color_plate_yellow}")

        Glide.with(context)
            .load(SelectedColor(car.color_plate.toString().uppercase()))
            .centerCrop()
            .fitCenter()
//            .placeholder(R.color.white)
            .into(binding.ivColorPlate)
        Glide.with(context)
            .load(car.image)
            .centerCrop()
            .fitCenter()
            .placeholder(R.drawable.ic_round_directions_car_24)
            .into(binding.ivCar)
    }
    private fun SelectedColor(color: String):Drawable?{
           return when(color){
                context.getString(R.string.color_plate_blue) -> context.getDrawable(R.color.color_plate_blue)
                context.getString(R.string.color_plate_yellow) -> context.getDrawable(R.color.color_plate_yellow)
                context.getString(R.string.color_plate_green) -> context.getDrawable(R.color.color_plate_green)
                context.getString(R.string.color_plate_pink) -> context.getDrawable(R.color.color_plate_pink)
               context.getString(R.string.color_plate_red) -> context.getDrawable(R.color.color_plate_red)
                else -> context.getDrawable(R.color.white)
            }
    }

}
