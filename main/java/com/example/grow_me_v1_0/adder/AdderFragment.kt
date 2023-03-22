package com.example.grow_me_v1_0.adder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.grow_me_v1_0.R
import com.example.grow_me_v1_0.TemperatureFragment
import com.example.grow_me_v1_0.databinding.FragmentAdderBinding


class AdderFragment : Fragment() {
    private lateinit var binding: FragmentAdderBinding
    private lateinit var viewModel : AdderFragmentModel
    var temperatureLow : Double = 0.0
    var temperatureHigh :Double = 0.0
    var temp_threshold = Pair(temperatureHigh, temperatureLow)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentAdderBinding>(inflater,
            R.layout.fragment_adder, container, false)

        viewModel = ViewModelProvider(this).get(AdderFragmentModel::class.java)

        binding.doneButtonPlantType.setOnClickListener {
            doneName()
        }
        binding.changePlantNameButton.setOnClickListener {
            changeName()
        }

        binding.doneButtonPlantTemperature.setOnClickListener {
            doneTemp()
        }

        binding.changePlantTemperature.setOnClickListener {
            changeTemp()
        }
        return binding.root
    }

    fun getTemp() : Double{
        var temperature_value = binding.editTemperatureText.text.toString().toDouble()
        return temperature_value
    }

    fun getTemperatureThreshold(temperature_val : Double): Pair<Double, Double>{
        return viewModel.getTemperatureThreshold(temperature_val)
    }

    private fun doneName(){
        binding.apply {
            plantName.text = plantType.text.toString()
            doneButtonPlantType.visibility = View.INVISIBLE
            changePlantNameButton.visibility = View.VISIBLE
            plantType.visibility = View.GONE
            plantName.visibility = View.VISIBLE

            val imm =
                activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity!!.currentFocus
            if(view == null){
                view = View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private fun changeName(){
        binding.apply {
            doneButtonPlantType.visibility = View.VISIBLE
            changePlantNameButton.visibility = View.INVISIBLE
            plantType.visibility = View.VISIBLE
            plantName.visibility = View.GONE
        }
    }

    private fun doneTemp(){
        binding.apply {
            editTemperatureText.text = editTextNumberDecimal.text.toString()
            doneButtonPlantTemperature.visibility = View.INVISIBLE
            changePlantTemperature.visibility = View.VISIBLE
            editTextNumberDecimal.visibility = View.GONE
            editTemperatureText.visibility = View.VISIBLE
            temp_threshold = getTemperatureThreshold(getTemp())
        }
    }

    private fun changeTemp(){
        binding.apply {
            doneButtonPlantTemperature.visibility = View.VISIBLE
            changePlantTemperature.visibility = View.INVISIBLE
            editTextNumberDecimal.visibility = View.VISIBLE
            editTemperatureText.visibility = View.GONE
        }
    }
}