/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 20/03/23 20:14
 *   File: GeneralInformationFragment.kt
 *   Last modified: 20/03/23 20:14
 *   Last change:
 *     Change Name ->
 *     Change Date -> 20/03/23 20:14
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.FragmentGeneralInformationBinding
import com.luocz.autoacuerdate.databinding.FragmentUserBinding


class GeneralInformationFragment : Fragment(R.layout.fragment_general_information) {
    private lateinit var binding: FragmentGeneralInformationBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGeneralInformationBinding.bind(view)
        when(arguments?.getInt("opcion")){
            1 ->{
                binding.tvGeneralInfo.visibility = View.INVISIBLE
                binding.tvGeneralInfo2.visibility = View.VISIBLE
               binding.tvGeneralInfo2.text ="Contact:"
                binding.tvGeneralInfo3.visibility = View.VISIBLE
               binding.tvGeneralInfo3.text ="octavio.12@hotmail.com"
                binding.tvGeneralInfo4.visibility = View.INVISIBLE
            }
            2 ->{
                binding.tvGeneralInfo.visibility = View.VISIBLE
                binding.tvGeneralInfo2.visibility = View.VISIBLE
//                binding.tvGeneralInfo2.text ="Contact:"
                binding.tvGeneralInfo3.visibility = View.VISIBLE
//                binding.tvGeneralInfo3.text ="octavio.12@hotmail.com"
                binding.tvGeneralInfo4.visibility = View.VISIBLE
            }

        }
    }

}