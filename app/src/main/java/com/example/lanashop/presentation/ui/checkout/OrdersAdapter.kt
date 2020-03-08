package com.example.lanashop.presentation.ui.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lanashop.R
import com.example.lanashop.databinding.OrderItemBinding
import kotlin.properties.Delegates
import com.example.lanashop.domain.model.OrdersResponse


class OrdersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: List<OrdersResponse> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val orderItemBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.order_item, parent, false
        )
        return OrdersViewHolder(orderItemBinding)
    }


    override fun getItemCount(): Int {
        return if (mList.isNullOrEmpty()) 0 else mList.size
    }

    private fun getItem(position: Int): OrdersResponse {
        return mList[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OrdersViewHolder).onBind(getItem(position))
    }

    inner class OrdersViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(
            order: OrdersResponse
        ) {
            (viewDataBinding as OrderItemBinding).order = order


        }
    }
}