package com.open.core_base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.open.core_base.coroutine.launch
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.core_base.utils.system.StatusBarUtil

abstract class CommonBindingFragment<B : ViewDataBinding> : CommonFragment() {
    private var firstInit: Boolean = true

    private var binding: B? = null

    abstract fun initialBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): B

    protected fun requireBinding(): B = binding!!

    override fun getRootView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initialBinding(layoutInflater, parent, savedInstanceState)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onStart() {
        if (firstInit) {
            launch {
                initViewModel()
            }
            firstInit = false
        }
        super.onStart()
    }

    protected open suspend fun initViewModel(){}
}