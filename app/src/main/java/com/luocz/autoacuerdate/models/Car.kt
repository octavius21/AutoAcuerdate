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




package com.luocz.autoacuerdate.models

import com.google.gson.annotations.SerializedName

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
    val image: String? = null,
    @SerializedName("left_pressure_tire_front")
    val left_pressure_tire_front: String? = null,
    @SerializedName("right_pressure_tire_front")
    val right_pressure_tire_front: String? = null,
    @SerializedName("left_pressure_tire_behavior")
    val left_pressure_tire_behavior: String? = null,
    @SerializedName("right_pressure_tire_behavior")
    val right_pressure_tire_behavior: String? = null
)