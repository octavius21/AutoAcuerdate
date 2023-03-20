/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 14:32
 *   File: CarApi.kt
 *   Last modified: 22/01/23 14:32
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 14:32
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.models

import retrofit2.Call
import retrofit2.http.*

interface CarApi {
    @GET
    fun getCars(
        @Url url: String?
    ): Call<CarX>

    @GET("cars")
    fun getCarDetail(
        @Query("id_car") id_car: String?,
    ):Call<Car>

    @GET
    fun getCards(
        @Url url: String?
    ):Call<CirculationCardX>

    @GET
    fun getCardDetail(
        @Query("id_card") id_car: String?,
    ):Call<CirculationCard>

    @GET
    fun getLocations(
        @Url url: String?
    ):Call<LocationX>
    @GET("locations")
    fun getLocationDetail(
        @Query("id_location") id_location: String?,
    ):Call<Location>
    /*@POST
    fun postLocation(
        @Body()
    ):Call<Location>*/


    /*@GET("/cars/{id_car}")
    fun getCar(
        @Path("id_car") id_car: String?,
    ):Call<Car>*/
}