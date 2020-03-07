package com.example.lanashop.presentation.ui.checkout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lanashop.R
import com.example.lanashop.databinding.FragmentOrdersBinding
import com.example.lanashop.presentation.commons.VerticalSpaceItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel


class OrdersFragment : Fragment() {

    private val ordersViewModel: OrdersViewModel by viewModel()
    private var mAdapter: OrdersAdapter? = null
    private lateinit var ordersFragment: FragmentOrdersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       requireActivity().onBackPressedDispatcher.addCallback(this) {
          findNavController().popBackStack(R.id.shoppingFragment,false)
       }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ordersFragment = DataBindingUtil.inflate( inflater,
            R.layout.fragment_orders, container, false)

        return ordersFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()
        initObservers()
    }



    private  fun init() {


        mAdapter =
            OrdersAdapter()
        ordersFragment.orderRecyclerView.adapter = mAdapter
        ordersFragment.orderRecyclerView.layoutManager = LinearLayoutManager(context)
        ordersFragment.orderRecyclerView.addItemDecoration(
            VerticalSpaceItemDecoration(
                -1
            )
        )

        ordersViewModel.getUserOrders()

    }


    private fun initObservers(){

        ordersViewModel.ordersData.observe(this, Observer {
            mAdapter?.mList = it.reversed()
            mAdapter?.notifyDataSetChanged()

        })
    }
}
