/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 13:56
 *   File: MapFragment.kt
 *   Last modified: 22/01/23 12:11
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 13:56
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.fragments


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.luocz.autoacuerdate.R

import com.luocz.autoacuerdate.databinding.FragmentMapBinding
import com.luocz.autoacuerdate.utils.Constants
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.luocz.autoacuerdate.models.CarApi
import com.luocz.autoacuerdate.models.LocationX
import com.luocz.autoacuerdate.utils.RetrofitServices
import com.luocz.autoacuerdate.views.activities.MainActivity
import com.luocz.autoacuerdate.views.adapters.LocationAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback, LocationListener{
    private lateinit var binding: FragmentMapBinding
//    Para googleMaps
    private lateinit var  map: GoogleMap
// Prueba para obtener un listener
    private lateinit var mapFragment: SupportMapFragment
//    Para los permisos
    private var coarseLocationPermissionGranted: Boolean = false
    private var fineLocationPermissionGranted: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync (this)
        Log.d(Constants.LOGTAG_INFO,"11->${requireActivity()}")
        Log.d(Constants.LOGTAG_INFO,"12->${requireContext()}")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.isTrafficEnabled = true
//        zoomUser()
//        createMarker()
        createMarkers()
        updateOrRequestPermissions()
        Log.d(Constants.LOGTAG_INFO,"9->${requireActivity()}")
        Log.d(Constants.LOGTAG_INFO,"10->${requireContext()}")
    }
    private fun updateOrRequestPermissions() {
//        Revision de permisos
        val hasCoarseLocationPermission: Boolean = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasFineLocationPermission: Boolean = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
//        "Asignando" los valores obtenidos
        coarseLocationPermissionGranted = hasCoarseLocationPermission
        fineLocationPermissionGranted = hasFineLocationPermission
//        Solicitando los permisos
        val permissionsToRequest = mutableListOf<String>()
        if(!hasCoarseLocationPermission)
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if(!hasFineLocationPermission)
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
//        Creo que deberia ser Empty
        if (permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toTypedArray(),
                Constants.PERMISO_LOCALIZACION
            )
        }else{
            map.isMyLocationEnabled = true
//            Dos posibles soluciones
//            val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE)
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10F,this)
                Log.d(Constants.LOGTAG_INFO,"Si GPS_PRovider-> true->${this}")
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10F, this);
                Log.d(Constants.LOGTAG_INFO,"SiNo GPS_PRovider-> true->${this}")
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            Constants.PERMISO_LOCALIZACION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Se obtuvo permisos
                    updateOrRequestPermissions()
                }else{
                    Log.d(Constants.LOGTAG_INFO,"1->${requireActivity()}")
                    Log.d(Constants.LOGTAG_INFO,"2->${requireContext()}")
                    if(shouldShowRequestPermissionRationale(permissions[0])){
                        AlertDialog.Builder(requireContext())
                            .setTitle(requireActivity().getString(R.string.request_permission_title))
                            .setMessage(requireActivity().getString(R.string.request_permission_msg))
                            .setPositiveButton(requireActivity().getString(R.string.request_permission_ok)
                            ) { _, _ ->
                                updateOrRequestPermissions()
                            }
                            .setNegativeButton(
                                requireActivity().getString(R.string.request_permission_cancel)
                            ) { dialog, _ ->
                                dialog.dismiss()
                                requireActivity().finish()
                            }
                            .create()
                            .show()
                    }else{
//                        Usuario rechazo dar permisos
                        Toast.makeText(requireContext(),
                            requireActivity().getString(R.string.request_permission_reject),
                                Toast.LENGTH_SHORT)
                            .show()
                        requireActivity().finish()
                    }
                }
            }
        }
    }
// Actualizacion de mi ubicacion
    override fun onLocationChanged(location: Location) {
    if (activity != null){
        map.clear()
        val coordinates = LatLng(location.latitude, location.longitude)
        val marker = MarkerOptions()
            .position(coordinates)
            .icon(
                bitmapDescriptorFromVector(
                    requireActivity(),
                    R.drawable.ic_baseline_navigation_24
                )
            )
        map.addMarker(marker)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 18f))
        createMarkers()
    }
    }
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
// Crear marcador
    fun createMarker(){
        //19.464089018712155, -99.14044295836308
        val coordinates = LatLng(19.322326, -99.184592)
        val marker = MarkerOptions()
            .position(coordinates)
            .title("DGTIC-UNAM")
            .snippet("Cursos y diplomados en TIC")
       //     .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_app))
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }

    // Crear marcadores
    fun createMarkers(){
        val call = RetrofitServices.getRetrofit().create(CarApi::class.java)
            .getLocations("locations")
        call.enqueue(object  : Callback<LocationX> {
            override fun onResponse(call: Call<LocationX>, response: Response<LocationX>) {
                Log.d(Constants.LOGTAG_INFO, "Respuesta del servidor:${response.toString()}")
                Log.d(Constants.LOGTAG_INFO, "Datos:${response.body().toString()}")
                if (response.body() == null){
                    Toast.makeText(binding.root.context,"Not load Markers",Toast.LENGTH_SHORT).show()
                }else{
                    val list_locations: LocationX = response.body()!!
                    for (i in list_locations.location!!){
                        val coordinates = LatLng(i.coordinates_latitude!!.toDouble(), i.coordinates_longitude!!.toDouble())
                        val marker = MarkerOptions()
                            .position(coordinates)
                            .title(i.location_name)
                            .icon(bitmapDescriptorFromVector(
                                binding.root.context,
                                R.drawable.ic_baseline_location_on_24
                            ))
                        map.addMarker(marker)
                    }
                }
            }
            override fun onFailure(call: Call<LocationX>, t: Throwable) {
                Toast.makeText(requireContext(),"${requireActivity().getString(R.string.failed)}:${t.message}",Toast.LENGTH_LONG).show()
            }

        })


    }



}