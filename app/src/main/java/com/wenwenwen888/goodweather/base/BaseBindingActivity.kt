package com.wenwenwen888.goodweather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric

/**
 * Created by won on 2021/5/6.
 */
abstract class BaseBindingActivity<VB : ViewBinding>: AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
    }

}