/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 13:57
 *   File: RetrofitServices.kt
 *   Last modified: 22/01/23 13:57
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 13:57
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {
    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_DUMMY_APIARY_CAR)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}