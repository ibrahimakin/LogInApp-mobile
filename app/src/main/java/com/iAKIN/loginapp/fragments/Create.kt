package com.iAKIN.loginapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iAKIN.loginapp.data.DBHelper
import com.iAKIN.loginapp.data.Record
import com.iAKIN.loginapp.databinding.FragmentItemCreateBinding

class Create : Fragment() {

    private var _binding: FragmentItemCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemCreateBinding.inflate(inflater, container, false)

        val db = DBHelper(context!!)

        binding.add.setOnClickListener {
            val site = binding.editText1.text.toString()
            val email = binding.editText2.text.toString()

            if (site.isNotEmpty() && email.isNotEmpty()) {
                val record = Record(site, email, binding.editText3.text.toString(), binding.editText4.text.toString(), binding.editText5.text.toString())
                db.insert(record)
            } else {
                Toast.makeText(requireContext(), "Site and e-mail are required.", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}