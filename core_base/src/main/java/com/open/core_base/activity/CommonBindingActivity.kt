package com.open.core_base.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.open.core_base.coroutine.launch

abstract class CommonBindingActivity<T : ViewDataBinding> : AppCompatActivity() {

    private var binding: T? = null

    abstract fun initialBinding(): T

    protected fun requireBinding(): T = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configUIThemes()

        binding = initialBinding()
        binding?.lifecycleOwner = this

        setContentView(binding?.root)

        launch {
            initViews()

            loadData()
        }
    }

    open suspend fun initViews() {}

    open suspend fun loadData() {}

    protected open fun configUIThemes() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (decorView.systemUiVisibility
            .or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            .or(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            .or(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)).let {
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                    it.or(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
                }
                it
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }
}