/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 05/02/23 13:36
 *   File: Location.kt
 *   Last modified: 05/02/23 13:36
 *   Last change:
 *     Change Name ->
 *     Change Date -> 05/02/23 13:36 
 *     Description ->
 *     
 * **************************************************************************
 */

package com.luocz.autoacuerdate.models

data class LocationX(
    val location: List<Location>?
)

data class Location(
    val address: String?,
    val coordinates_latitude: String?,
    val coordinates_longitude: String?,
    val id_location: String?,
    val location_name: String?,
    val nation: String?
)