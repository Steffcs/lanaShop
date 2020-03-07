package com.example.lanashop.presentation.ui.cart

import android.os.Bundle
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
import com.example.lanashop.databinding.FragmentShoppingBinding
import com.example.lanashop.domain.model.Product
import com.example.lanashop.presentation.commons.VerticalSpaceItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel


class ShoppingFragment : Fragment(),(Product, Int) -> Unit {
    private val productsViewModel: ProductsViewModel by viewModel()
    private var mAdapter: ProductsAdapter? = null
    private lateinit var fragmentShoppingBinding: FragmentShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity!!.finish()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentShoppingBinding = DataBindingUtil.inflate( inflater,
            R.layout.fragment_shopping, container, false)


        return fragmentShoppingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()
        initObservers()
    }


    private  fun init() {

        mAdapter =
            ProductsAdapter(this)
        fragmentShoppingBinding.shoppingViewLayout.productsRecyclerView.adapter = mAdapter
        fragmentShoppingBinding.shoppingViewLayout.productsRecyclerView.layoutManager = LinearLayoutManager(context)

        productsViewModel.getProducts()
        fragmentShoppingBinding.shoppingViewLayout.materialButton.setOnClickListener{
            productsViewModel.getCheckout()

            if(productsViewModel.count>0.0)
                 findNavController().navigate(R.id.action_shoppingFragment_to_ordersFragment)
        }

    }


    private fun initObservers(){
        productsViewModel.total.observe(this, Observer {
            fragmentShoppingBinding.shoppingViewLayout.txtTotal.text = it.toString()
        })

        productsViewModel.productsData.observe(this, Observer {

            fragmentShoppingBinding.emptyViewLayout.emptyViewContainer.visibility= View.GONE
            fragmentShoppingBinding.shoppingViewLayout.shoppingCartContainer.visibility= View.VISIBLE

            mAdapter?.mList = it
            mAdapter?.notifyDataSetChanged()
            productsViewModel.getDiscount()
        })

        productsViewModel.messageData.observe(this, Observer {
            setFailLayout(it)
        })

        productsViewModel.showProgressbar.observe(this, Observer { isVisible ->
            setEmptyState(isVisible)

        })

    }

    private fun  setEmptyState(state: Boolean){
        if(state){
            fragmentShoppingBinding.shoppingViewLayout.shoppingCartContainer.visibility= View.GONE
            fragmentShoppingBinding.emptyViewLayout.emptyViewContainer.visibility= View.VISIBLE
            fragmentShoppingBinding.emptyViewLayout.loadingBar.visibility = View.VISIBLE
            fragmentShoppingBinding.emptyViewLayout.txtMessage.visibility= View.GONE
        }else{
            fragmentShoppingBinding.shoppingViewLayout.shoppingCartContainer.visibility= View.VISIBLE
            fragmentShoppingBinding.emptyViewLayout.emptyViewContainer.visibility= View.GONE
            fragmentShoppingBinding.emptyViewLayout.loadingBar.visibility = View.GONE
            fragmentShoppingBinding.emptyViewLayout.txtMessage.visibility= View.GONE
        }

    }

    private fun setFailLayout(msj: String){
        fragmentShoppingBinding.shoppingViewLayout.shoppingCartContainer.visibility= View.GONE
        fragmentShoppingBinding.emptyViewLayout.emptyViewContainer.visibility= View.VISIBLE
        fragmentShoppingBinding.emptyViewLayout.loadingBar.visibility = View.GONE
        fragmentShoppingBinding.emptyViewLayout.ivEmptyView.visibility= View.VISIBLE
        fragmentShoppingBinding.emptyViewLayout.txtMessage.visibility= View.VISIBLE
        fragmentShoppingBinding.emptyViewLayout.txtMessage.text=msj

    }

    override fun invoke(product: Product, action: Int) {
        productsViewModel.updateProduct(product,action)

    }


}
