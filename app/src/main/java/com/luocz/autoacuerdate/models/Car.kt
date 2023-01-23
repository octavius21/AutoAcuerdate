/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 14:13
 *   File: Car.kt
 *   Last modified: 22/01/23 14:01
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 14:13
 *     Description ->
 *
 * **************************************************************************
 */

/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 12:10
 *   File: Car.kt
 *   Last modified: 22/01/23 12:07
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 12:10
 *     Description ->
 *
 * **************************************************************************
 */


package com.luocz.autoacuerdate.models

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

/*data class Car(
    @SerializedName("id_car")
    val id_car: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("model")
    val model: Int? = null,
    @SerializedName("license_plate")
    val license_plate: String? = null,
    @SerializedName("color_plate")
    val color_plate: String? = null,
    @SerializedName("origin")
    val origin: String? = null,
    @SerializedName("image")
    val image: Bitmap? = null
)*/
data class CarX(
    val car: ArrayList<Car>?
)

data class Car(
    @SerializedName("id_car")
    val id_car: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("model")
    val model: Int? = null,
    @SerializedName("license_plate")
    val license_plate: String? = null,
    @SerializedName("color_plate")
    val color_plate: String? = null,
    @SerializedName("origin")
    val origin: String? = null,
    @SerializedName("image")
    val image: String? = null
)