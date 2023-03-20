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
import com.luocz.autoacuerdate.models.CirculationCardX
import com.luocz.autoacuerdate.views.activities.MainActivity

class CirculationCredentialAdapter (private val context: Context, private val circulationCards: CirculationCardX):
    RecyclerView.Adapter<CirculationCardViewHolder>() {
    var onItemClick:((CirculationCard) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CirculationCardViewHolder {
        val binding = CirculationCardItemBinding.inflate(LayoutInflater.from(context))
        return CirculationCardViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: CirculationCardViewHolder, position: Int) {
        holder.bind(circulationCards.circulation_cards?.get(position)!!, onItemClick)
        holder.itemView.setOnClickListener {
            if(context is MainActivity) context.selectedCard(circulationCards.circulation_cards?.get(position)!!)
        }
    }

    override fun getItemCount(): Int = circulationCards.circulation_cards?.size!!
}