/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 19:14
 *   File: CarDetailFragment.kt
 *   Last modified: 22/01/23 19:14
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 19:14
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentCarDetailBinding
import com.luocz.autoacuerdate.models.Car
import com.luocz.autoacuerdate.models.CarApi
import com.luocz.autoacuerdate.models.CarX
import com.luocz.autoacuerdate.utils.Constants
import com.luocz.autoacuerdate.utils.RetrofitServices
import com.luocz.autoacuerdate.views.adapters.CarAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CarDetailFragment : Fragment(R.layout.fragment_car_detail) {
private lateinit var  binding: FragmentCarDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarDetailBinding.bind(view)

        val id = arguments?.getString("ID_CAR","0")
        Toast.makeText(requireContext(),"${id.toString()}",Toast.LENGTH_SHORT).show()

        val call = RetrofitServices.getRetrofit().create(CarApi::class.java).getCarDetail(id.toString())
        call.enqueue(object : Callback<Car> {
            override fun onResponse(
                call: Call<Car>,
                response: Response<Car>
            ) {
                Log.d(Constants.LOGTAG_INFO, "Respuesta del servidor:${response.toString()}")
                Log.d(Constants.LOGTAG_INFO, "Datos:${response.body().toString()}")
                val car: Car? = response.body()
                val model= requireActivity().getString(R.string.model,car?.brand.toString())
                val description = requireActivity().getString(R.string.descripcion,
                    response.body()?.model.toString(),
                    response.body()?.color_plate.toString(),
                    response.body()?.license_plate.toString(),
                    response.body()?.origin.toString())
                Glide.with(this@CarDetailFragment)
                    .load(response.body()?.image)
                    .into(binding.ivImage)
                binding.apply {
                    tvBrand.text = model
                    tvLongDesc.text = description
                    pbConexion.visibility = View.INVISIBLE
                    pbConexion.isEnabled = false
                }

            }

            override fun onFailure(call: Call<Car>, t: Throwable) {
                Toast.makeText(requireContext(),"${requireActivity().getString(R.string.failed)}${t.message}",Toast.LENGTH_LONG).show()
            }

        })
    }

}