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

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
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

                Glide.with(this@CarDetailFragment)
                    .load(response.body()?.image)
                    .into(binding.ivImage)

                /*val description = requireActivity().getString(R.string.descripcion,
                    response.body()?.model.toString(),
                    response.body()?.color_plate.toString(),
                    response.body()?.license_plate.toString(),
                    response.body()?.origin.toString())*/



                binding.apply {
                    tvBrand.text = requireActivity().getString(R.string.model,car?.brand.toString())
                    tvNationalityCarDetail.text = requireActivity().getString(R.string.nacionality, car?.origin)
                    tvYear.text = requireActivity().getString(R.string.year,car?.model.toString())
                    Glide.with(requireContext())
                        .load(SelectedColor(car?.color_plate.toString().uppercase()))
                        .centerCrop()
                        .fitCenter()
                        .into(binding.ivColorPlateCarDetail)
                    tvLicensePlateCarDetail.text = response.body()?.license_plate.toString()
//                  TODO: Falta implementarlo en el API y la propiedad faltante
                    tvLeftTireFront.text = requireActivity().getString(R.string.left_pressure_tire,response.body()?.left_pressure_tire_front.toString())
                    tvRightTireFront.text = requireActivity().getString(R.string.left_pressure_tire,response.body()?.right_pressure_tire_front.toString())
                    tvLeftTireBehavior.text = requireActivity().getString(R.string.left_pressure_tire,response.body()?.left_pressure_tire_behavior.toString())
                    tvRightTireBehavior.text = requireActivity().getString(R.string.left_pressure_tire,response.body()?.right_pressure_tire_behavior.toString())
//                    tvMileageCarDetail.text = requireActivity().getString(R.string.left_pressure_tire,response.body()?.mileage.toString())
                    /*tvLeftTireFront.text = requireActivity().getString(R.string.left_pressure_tire,"10PA")
                    tvRightTireFront.text = requireActivity().getString(R.string.left_pressure_tire,"10PA")
                    tvLeftTireBehavior.text = requireActivity().getString(R.string.left_pressure_tire,"10PA")
                    tvRightTireBehavior.text = requireActivity().getString(R.string.left_pressure_tire,"10PA")
                    tvMileageCarDetail.text = requireActivity().getString(R.string.mileage,"118000 KM")*/
                    tv.text = "proipiedad faltante"
                    ivtireLF.visibility = View.VISIBLE
                    ivtireRF.visibility = View.VISIBLE
                    ivtireLB.visibility = View.VISIBLE
                    ivtireRB.visibility = View.VISIBLE

                    pbConexion.visibility = View.INVISIBLE
                    pbConexion.isEnabled = false
                }

            }

            override fun onFailure(call: Call<Car>, t: Throwable) {
                Toast.makeText(requireContext(),"${requireActivity().getString(R.string.failed)}${t.message}",Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun SelectedColor(color: String): Drawable?{
        Toast.makeText(requireContext(), color,Toast.LENGTH_LONG).show()
        return when(color){
            requireContext().getString(R.string.color_plate_blue) -> getDrawable(requireContext(),R.color.color_plate_blue)
            requireContext().getString(R.string.color_plate_red) -> getDrawable(requireContext(),R.color.color_plate_red)
            requireContext().getString(R.string.color_plate_yellow) -> getDrawable(requireContext(),R.color.color_plate_yellow)
            requireContext().getString(R.string.color_plate_pink) -> getDrawable(requireContext(),R.color.color_plate_pink)
            requireContext().getString(R.string.color_plate_green) -> getDrawable(requireContext(),R.color.color_plate_green)
            else -> getDrawable(requireContext(),R.color.white)
        }
    }

}