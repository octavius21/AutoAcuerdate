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

data class CirculationCard(
    val name: String,
    val itin: String?,
    val exp_date: Date?,
    val efe_date: Date?

)
