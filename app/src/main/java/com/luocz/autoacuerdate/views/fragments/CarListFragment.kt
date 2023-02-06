/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 13:56
 *   File: CarListFragment.kt
 *   Last modified: 22/01/23 12:11
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 13:56
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentCarListBinding
import com.luocz.autoacuerdate.models.CarApi
import com.luocz.autoacuerdate.models.CarX
import com.luocz.autoacuerdate.utils.Constants
import com.luocz.autoacuerdate.utils.RetrofitServices
import com.luocz.autoacuerdate.views.adapters.CarAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CarListFragment : Fragment(R.layout.fragment_car_list) {
    private lateinit var  binding : FragmentCarListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarListBinding.bind(view)
        binding.fabCars.setOnClickListener {
            Toast.makeText(requireContext(), Constants.TOAST_MSG_DEVELOPER, Toast.LENGTH_SHORT).show()
        }
        binding.pbConexion.visibility = View.VISIBLE
        val call = RetrofitServices.getRetrofit().create(CarApi::class.java)
            .getCars("cars")
        call.enqueue(object : Callback<CarX>{
            override fun onResponse(
                call: Call<CarX>,
                response: Response<CarX>
            ) {
                Log.d(Constants.LOGTAG_INFO, "Respuesta del servidor:${response.toString()}")
                Log.d(Constants.LOGTAG_INFO, "Datos:${response.body().toString()}")
                binding.rvCars.layoutManager = LinearLayoutManager(binding.root.context,RecyclerView.VERTICAL, false)
                binding.rvCars.adapter = CarAdapter(binding.root.context,response.body()!!)
                binding.pbConexion.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<CarX>, t: Throwable) {
                Toast.makeText(requireContext(),"${requireActivity().getString(R.string.failed)}:${t.message}",Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()

    }
}