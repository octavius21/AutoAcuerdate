/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 06/02/23 11:38
 *   File: LocationDetailFragment.kt
 *   Last modified: 06/02/23 11:38
 *   Last change:
 *     Change Name ->
 *     Change Date -> 06/02/23 11:38
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
import com.bumptech.glide.Glide
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentLocationDetailBinding
import com.luocz.autoacuerdate.databinding.FragmentLocationListBinding
import com.luocz.autoacuerdate.models.Car
import com.luocz.autoacuerdate.models.CarApi
import com.luocz.autoacuerdate.models.Location
import com.luocz.autoacuerdate.utils.Constants
import com.luocz.autoacuerdate.utils.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LocationDetailFragment : Fragment(R.layout.fragment_location_detail) {

    private lateinit var binding: FragmentLocationDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationDetailBinding.bind(view)
        val id = arguments?.getString("ID_LOCATION","0")
        val call = RetrofitServices.getRetrofit().create(CarApi::class.java).getLocationDetail(id.toString())
        call.enqueue(object : Callback<Location>{
            override fun onResponse(call: Call<Location>, response: Response<Location>) {
                Log.d(Constants.LOGTAG_INFO, "Respuesta del servidor:${response.toString()}")
                Log.d(Constants.LOGTAG_INFO, "Datos:${response.body().toString()}")
                val location: Location? = response.body()
                binding.tvLocationName.text = location!!.location_name
                binding.tvCoordinatesLatitude.text = location!!.coordinates_latitude
                binding.tvCoordinatesLongitude.text = location!!.coordinates_longitude
            }

            override fun onFailure(call: Call<Location>, t: Throwable) {
                Toast.makeText(requireContext(),"${requireActivity().getString(R.string.failed)}${t.message}",
                    Toast.LENGTH_LONG).show()
            }

        })
    }

}