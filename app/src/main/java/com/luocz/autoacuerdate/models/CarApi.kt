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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CarApi {
    @GET
    fun getCars(
        @Url url: String?
    ): Call<CarX>

    @GET("cars")
    fun getCarDetail(
        @Query("id_car") id_car: String?,
    ):Call<Car>

    /*@GET("/cars/{id_car}")
    fun getCar(
        @Path("id_car") id_car: String?,
    ):Call<Car>*/
}