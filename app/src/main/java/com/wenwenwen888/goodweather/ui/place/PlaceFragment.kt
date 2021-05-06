package com.wenwenwen888.goodweather.ui.place

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wenwenwen888.goodweather.MainActivity
import com.wenwenwen888.goodweather.base.BaseBindingFragment
import com.wenwenwen888.goodweather.databinding.FragmentPlaceBinding
import com.wenwenwen888.goodweather.ui.weather.WeatherActivity
import com.wenwenwen888.goodweather.utils.showToast

/**
 * Created by won on 2021/4/29.
 */
class PlaceFragment : BaseBindingFragment<FragmentPlaceBinding>() {

    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is MainActivity && viewModel.isPlaceSaved()){
            val place = viewModel.getSavePlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlaceAdapter(this, viewModel.placeList)
        binding.recyclerView.adapter = adapter
        binding.searchPlaceEdit.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(this, Observer {
            val places = it.getOrNull()
            if (places != null){
                binding.recyclerView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                "未能查询到任何地点".showToast()
            }
        })

    }

}