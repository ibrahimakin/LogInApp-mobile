package com.iAKIN.loginapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iAKIN.loginapp.database.DBHelper
import com.iAKIN.loginapp.database.Record
import com.iAKIN.loginapp.databinding.FragmentItemCreateBinding

class Create : Fragment() {

    private var _binding: FragmentItemCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemCreateBinding.inflate(inflater, container, false)

        var db = DBHelper(context!!)

        binding.add.setOnClickListener{ view ->
            var site = binding.editText1.text.toString()
            var email = binding.editText2.text.toString()
            var username = binding.editText3.text.toString()
            var hint = binding.editText4.text.toString()
            var tags = binding.editText5.text.toString()

            if (site.isNotEmpty() && email.isNotEmpty()) {
                var record = Record(site, email, username, hint, tags)
                db.insert(record);
            }
            else {
                Toast.makeText(requireContext(), "Site and e-mail are required.", Toast.LENGTH_LONG).show();
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}