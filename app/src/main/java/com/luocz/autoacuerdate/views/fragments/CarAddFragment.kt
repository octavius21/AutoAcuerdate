/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 19/03/23 22:45
 *   File: CarAddFragment.kt
 *   Last modified: 19/03/23 22:45
 *   Last change:
 *     Change Name ->
 *     Change Date -> 19/03/23 22:45
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.fragments

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentCarAddBinding
import com.google.android.material.resources.MaterialResources;

class CarAddFragment : Fragment(R.layout.fragment_car_add), AdapterView.OnItemClickListener {
    private lateinit var binding: FragmentCarAddBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarAddBinding.bind(view)
        dropDownListSetUp()
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnDone.setOnClickListener {
            findNavController().navigate(R.id.action_carAddFragment_to_carListFragment)
        }
    }

    private fun dropDownListSetUp() {
        val models = resources.getStringArray(R.array.Models)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            models
        )
        val nations = resources.getStringArray(R.array.Nationality)
        val adapterNations = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            nations
        )
        val colors = resources.getStringArray(R.array.ColorPlates)
        val adapterColors = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            colors
        )

        val pressure = (29..41).toList()
        val adapterPressure = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            pressure
        )

        with(binding.autoCompleteTextView) {
            setAdapter(adapter)
            onItemClickListener = this@CarAddFragment
        }
        with(binding.atcvNationality) {
            setAdapter(adapterNations)
            onItemClickListener = this@CarAddFragment
        }
        with(binding.atcvColorPlate) {
            setAdapter(adapterColors)
            onItemClickListener = this@CarAddFragment
        }
        with(binding.atcvTireUpLeft) {
            setAdapter(adapterPressure)
            onItemClickListener = this@CarAddFragment
        }
        with(binding.atcvTireUpRight) {
            setAdapter(adapterPressure)
            onItemClickListener = this@CarAddFragment
        }
        with(binding.atcvTireDownLeft) {
            setAdapter(adapterPressure)
            onItemClickListener = this@CarAddFragment
        }
        with(binding.atcvTireDownRight) {
            setAdapter(adapterPressure)
            onItemClickListener = this@CarAddFragment
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(), item,Toast.LENGTH_LONG).show()
    }




}