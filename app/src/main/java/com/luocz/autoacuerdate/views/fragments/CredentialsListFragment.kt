/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 13:56
 *   File: CredentialsListFragment.kt
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentCredentialsListBinding
import com.luocz.autoacuerdate.models.CarApi
import com.luocz.autoacuerdate.models.CirculationCard
import com.luocz.autoacuerdate.models.CirculationCardX
import com.luocz.autoacuerdate.utils.Constants
import com.luocz.autoacuerdate.utils.RetrofitServices
import com.luocz.autoacuerdate.views.adapters.CirculationCredentialAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CredentialsListFragment : Fragment(R.layout.fragment_credentials_list) {
    private lateinit var  binding: FragmentCredentialsListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCredentialsListBinding.bind(view)
        binding.fabCredentials.setOnClickListener {
            Toast.makeText(requireContext(), Constants.TOAST_MSG_DEVELOPER, Toast.LENGTH_SHORT).show()
        }
        binding.pbConexion.visibility = View.VISIBLE
        val call = RetrofitServices.getRetrofit().create(CarApi::class.java).getCards("cards")
        call.enqueue(object : Callback<CirculationCardX>{


            override fun onResponse(
                call: Call<CirculationCardX>,
                response: Response<CirculationCardX>
            ) {
                Log.d(Constants.LOGTAG_INFO, "Respuesta del servidor:${response.toString()}")
                Log.d(Constants.LOGTAG_INFO, "Datos:${response.body().toString()}")
                binding.rvCredentials.layoutManager = GridLayoutManager(binding.root.context,2)
                binding.rvCredentials.adapter = CirculationCredentialAdapter(requireContext(), response.body()!!)
                binding.rvCredentials.adapter = ConcatAdapter(CirculationCredentialAdapter(requireContext(), response.body()!!),CirculationCredentialAdapter(requireContext(), response.body()!!))
                binding.pbConexion.visibility = View.INVISIBLE
            }
            override fun onFailure(call: Call<CirculationCardX>, t: Throwable) {
                Toast.makeText(requireContext(),"${requireActivity().getString(R.string.failed)}:${t.message}",Toast.LENGTH_LONG).show()
            }

        })
    }

}

