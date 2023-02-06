/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 04/02/23 19:46
 *   File: LocationFragment.kt
 *   Last modified: 04/02/23 19:46
 *   Last change:
 *     Change Name ->
 *     Change Date -> 04/02/23 19:46
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentLocationListBinding
import com.luocz.autoacuerdate.models.CarApi
import com.luocz.autoacuerdate.models.LocationX
import com.luocz.autoacuerdate.utils.Constants
import com.luocz.autoacuerdate.utils.RetrofitServices
import com.luocz.autoacuerdate.views.adapters.LocationAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LocationListFragment : Fragment(R.layout.fragment_location_list) {
    private lateinit var  binding: FragmentLocationListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationListBinding.bind(view)
        binding.fabLocations.setOnClickListener {
            Toast.makeText(requireContext(), Constants.TOAST_MSG_DEVELOPER, Toast.LENGTH_SHORT).show()
        }
        binding.pbConexion.visibility = View.VISIBLE
        val call = RetrofitServices.getRetrofit().create(CarApi::class.java)
            .getLocations("locations")
        call.enqueue(object  : Callback<LocationX>{
            override fun onResponse(call: Call<LocationX>, response: Response<LocationX>) {
                Log.d(Constants.LOGTAG_INFO, "Respuesta del servidor:${response.toString()}")
                Log.d(Constants.LOGTAG_INFO, "Datos:${response.body().toString()}")
                binding.rvLocations.layoutManager = GridLayoutManager(binding.root.context,2)
                binding.rvLocations.adapter = LocationAdapter(requireContext(), response.body()!!)
                binding.pbConexion.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<LocationX>, t: Throwable) {
                Toast.makeText(requireContext(),"${requireActivity().getString(R.string.failed)}:${t.message}",Toast.LENGTH_LONG).show()
            }

        })
    }
}