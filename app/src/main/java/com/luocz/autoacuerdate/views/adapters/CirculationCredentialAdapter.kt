/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 12:10
 *   File: CredentialAdapter.kt
 *   Last modified: 21/01/23 9:15
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 12:10
 *     Description ->
 *
 * **************************************************************************
 */



package com.luocz.autoacuerdate.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luocz.autoacuerdate.views.holders.CirculationCardViewHolder
import com.luocz.autoacuerdate.models.CirculationCard
import com.luocz.autoacuerdate.databinding.CirculationCardItemBinding
class CirculationCredentialAdapter (private val context: Context, private val circulationCards: ArrayList<CirculationCard>):
    RecyclerView.Adapter<CirculationCardViewHolder>() {

    var onItemClick:((CirculationCard) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CirculationCardViewHolder {
        val binding = CirculationCardItemBinding.inflate(LayoutInflater.from(context))
        return CirculationCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CirculationCardViewHolder, position: Int) {
        holder.bind(circulationCards[position], onItemClick)
    }

    override fun getItemCount(): Int = circulationCards.size
}