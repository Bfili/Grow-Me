package com.example.grow_me_v1_0

import android.R.attr.defaultValue
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.grow_me_v1_0.adder.AdderFragmentModel
import com.example.grow_me_v1_0.databinding.FragmentTemperatureBinding


class TemperatureFragment : Fragment(), SensorEventListener {
    private lateinit var binding : FragmentTemperatureBinding
    private lateinit var sensorManager : SensorManager
    private lateinit var tempSensor: Sensor
    private lateinit var viewModel : AdderFragmentModel

    var actualTemperature : Double = 25.0
    var optimalTemperature : Double = 25.5
    var isTempSensAvailable : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTemperatureBinding>(inflater,
            R.layout.fragment_temperature, container, false)

        sensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null){
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            isTempSensAvailable = true
        }
        else
        {
            Toast.makeText(activity, "Nie masz sensora temperatury, nie możesz skorzystać z tej funkcji", Toast.LENGTH_LONG).show()
            isTempSensAvailable = false
        }
        return binding.root
    }

    override fun onSensorChanged(sensorEvent: SensorEvent){
        binding.temperatureReadingText.text = sensorEvent.values[0].toString()
        actualTemperature = sensorEvent.values[0].toDouble()
        isTempGood()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        if(isTempSensAvailable){
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        if(isTempSensAvailable){
            sensorManager.unregisterListener(this)
        }
    }

    private fun isTempGood(){
        optimalTemperature = 25.5
        viewModel = ViewModelProvider(this).get(AdderFragmentModel::class.java)
        var(high, low) = viewModel.getTemperatureThreshold(optimalTemperature)
        if(actualTemperature < high && actualTemperature > low){
            binding.isTempGood.text = "Jest dobra"
        }
        else
        {
            binding.isTempGood.text = "Jest zła"
        }
    }
}