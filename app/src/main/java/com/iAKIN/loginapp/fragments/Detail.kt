package com.iAKIN.loginapp.fragments

import android.app.AlertDialog
import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.iAKIN.loginapp.R
import com.iAKIN.loginapp.data.DBHelper
import com.iAKIN.loginapp.data.Record
import com.iAKIN.loginapp.data.Site
import com.iAKIN.loginapp.databinding.FragmentItemDetailBinding
import java.net.URI

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [List]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class Detail : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: Record? = null

    lateinit var site: EditText
    lateinit var email: EditText
    lateinit var username: EditText
    lateinit var hint: EditText
    lateinit var tags: EditText
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
            val dragData = clipDataItem.text
            item = Record() // PlaceholderContent.ITEM_MAP[dragData]
            updateContent()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = Record(
                    it.getString(ARG_ITEM_SITE).toString(),
                    it.getString(ARG_ITEM_EMAIL).toString(),
                    it.getString(ARG_ITEM_USERNAME).toString(),
                    it.getString(ARG_ITEM_HINT).toString(),
                    it.getString(ARG_ITEM_TAGS).toString()
                ) // PlaceholderContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                item?.id = it.getInt(ARG_ITEM_ID)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val db = DBHelper(context!!)

        binding.fab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.detail_to_create)
        }
        binding.update.setOnClickListener {
            db.update(
                item!!.id, Record(
                    site.text.toString(),
                    email.text.toString(),
                    username.text.toString(),
                    hint.text.toString(),
                    tags.text.toString()
                )
            )
        }
        binding.delete.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Delete")
                .setMessage("Do you really want to delete the record?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    if (db.delete(item!!.id) == 1) activity?.supportFragmentManager?.popBackStack()
                }.setNegativeButton(android.R.string.cancel, null).show();
        }

        toolbarLayout = binding.toolbarLayout
        site = binding.editText1
        email = binding.editText2
        username = binding.editText3
        hint = binding.editText4
        tags = binding.editText5

        updateContent()
        rootView.setOnDragListener(dragListener)

        return rootView
    }

    private fun updateContent() {
        try {
            val uri = URI(item?.site)
            val domain: String = uri.host
            // binding.detailText.text = if (domain.startsWith("www.")) domain.substring(4) else domain
            toolbarLayout?.title = if (domain.startsWith("www.")) domain.substring(4) else domain
        } catch (e: Exception) {
            // binding.detailText.text = item?.site
            toolbarLayout?.title = item?.site
        }

        /*try {
            Site.fetchIcon(binding.imageView, item?.site.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }*/

        try {
            Site.webScrapping(binding.imageView, item?.site.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Show the placeholder content as text in a TextView.
        item?.let {
            site.setText(it.site)
            email.setText(it.email)
            username.setText(it.username)
            hint.setText(it.hint)
            tags.setText(it.tags)
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
        const val ARG_ITEM_SITE = "item_site"
        const val ARG_ITEM_EMAIL = "item_email"
        const val ARG_ITEM_USERNAME = "item_username"
        const val ARG_ITEM_HINT = "item_hint"
        const val ARG_ITEM_TAGS = "item_tags"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}