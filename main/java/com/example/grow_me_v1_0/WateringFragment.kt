package com.example.grow_me_v1_0

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.grow_me_v1_0.databinding.FragmentWateringBinding

class WateringFragment : Fragment() {
    private lateinit var binding : FragmentWateringBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentWateringBinding>(inflater, R.layout.fragment_watering, container, false)

        binding.wateringGoButton.setOnClickListener {
            calendarAction()
        }

        return binding.root
    }

    fun calendarAction(){
        if(!binding.inputEventName.text.toString().isEmpty()
            && !binding.editWater.text.toString().isEmpty()){

            var intent : Intent = Intent(Intent.ACTION_INSERT)
            intent.setData(CalendarContract.Events.CONTENT_URI)
            intent.putExtra(CalendarContract.Events.TITLE, binding.inputEventName.text.toString())
            intent.putExtra(CalendarContract.Events.DESCRIPTION, binding.editWater.text.toString())
            intent.putExtra(CalendarContract.Events.ALL_DAY, true)

            if(intent.resolveActivity(activity!!.packageManager) != null){
                startActivity(intent)
            }
            else
            {
                Toast.makeText(activity, "Nie ma aplikacji, która mogłaby obsłużyć to działanie", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(activity, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show()
        }
        val imm =
            activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus
        if(view == null){
            view = View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}