/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 13:24
 *   File: CirculationCardViewHolder.kt
 *   Last modified: 22/01/23 13:24
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 13:24
 *     Description ->
 *
 * **************************************************************************
 */

package com.luocz.autoacuerdate.views.holders

import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.models.CirculationCard
import com.luocz.autoacuerdate.databinding.CirculationCardItemBinding


class CirculationCardViewHolder (val binding: CirculationCardItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(circulationCard: CirculationCard, onItemClick: ((CirculationCard) -> Unit)?){

    }
}
