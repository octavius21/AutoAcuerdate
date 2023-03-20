/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 12:10
 *   File: CirculationCard.kt
 *   Last modified: 22/01/23 12:10
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 12:10
 *     Description ->
 *
 * **************************************************************************
 */


package com.luocz.autoacuerdate.models

import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.annotations.SerializedName

/*data class CirculationCard(
    val name: String,
    val itin: String?,
    val exp_date: Date?,
    val efe_date: Date?

)*/
data class CirculationCardX(
    val circulation_cards: ArrayList<CirculationCard>?
)

data class CirculationCard(
    @SerializedName("efe_date")
    val efe_date: String? = null,
    @SerializedName("exp_date")
    val exp_date: String? = null,
    @SerializedName("id_car")
    val id_card: String? = null,
    @SerializedName("itin")
    val itin: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("origin")
    val origin: String? = null
)


