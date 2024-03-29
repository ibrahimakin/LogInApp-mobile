package com.iAKIN.loginapp.fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.iAKIN.loginapp.R
import com.iAKIN.loginapp.data.DBHelper
import com.iAKIN.loginapp.data.Record
import com.iAKIN.loginapp.data.Sync
import com.iAKIN.loginapp.databinding.FragmentItemListBinding
import com.iAKIN.loginapp.databinding.ItemListContentBinding

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class List : Fragment() {
    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    private val unhandledKeyEventListenerCompat = ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
        if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
            Toast.makeText(v.context, "Undo (Ctrl + Z) shortcut triggered", Toast.LENGTH_LONG).show()
            true
        } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
            Toast.makeText(v.context, "Find (Ctrl + F) shortcut triggered", Toast.LENGTH_LONG).show()
            true
        }
        false
    }

    private var _binding: FragmentItemListBinding? = null
    private lateinit var adapter: SimpleItemRecyclerViewAdapter
    private var search: SearchView? = null
    private var searchText = ""


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)

        binding.fabList.setOnClickListener { view -> view.findNavController().navigate(R.id.list_to_create) }

        binding.fabRead.setOnClickListener {
            // Request code for creating a PDF document.
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/json"
            }
            startActivityForResult(intent, 1)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data != null && data.data != null) {
                Sync.readFile(data.data, this.requireContext())
                setupRecyclerView(binding.itemList, null)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

        val recyclerView: RecyclerView = binding.itemList

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = null
        // view.findViewById(R.id.item_detail_nav_container)

        setupRecyclerView(recyclerView, itemDetailFragmentContainer)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search) as MenuItem
        search = searchItem.actionView as SearchView
        search?.isIconifiedByDefault = false
        search?.isIconified = false
        search?.queryHint = "Search"

        if (searchText.isNotEmpty()) {
            search?.setQuery(searchText, true)
            searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
            searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            searchItem.expandActionView()
            adapter.filter(searchText)
        }

        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean = false

            override fun onQueryTextChange(p0: String): Boolean {
                searchText = p0
                adapter.filter(p0)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, itemDetailFragmentContainer: View?) {
        adapter = SimpleItemRecyclerViewAdapter(DBHelper(this.requireContext()).read(), itemDetailFragmentContainer)
        recyclerView.adapter = adapter
    }

    class SimpleItemRecyclerViewAdapter(
        values: MutableList<Record>, private var itemDetailFragmentContainer: View?
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private var allValues: MutableList<Record> = ArrayList()
        private var values: MutableList<Record> = ArrayList()

        init {
            this.values.addAll(values)
            allValues.addAll(values)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val i = values[position]
            holder.idView.text = i.id.toString()
            holder.contentView.text = i.site

            with(holder.itemView) {
                tag = i
                setOnClickListener { itemView ->
                    val item = itemView.tag as Record
                    val bundle = Bundle()
                    bundle.putInt(Detail.ARG_ITEM_ID, item.id)
                    bundle.putString(Detail.ARG_ITEM_SITE, item.site)
                    bundle.putString(Detail.ARG_ITEM_EMAIL, item.email)
                    bundle.putString(Detail.ARG_ITEM_USERNAME, item.username)
                    bundle.putString(Detail.ARG_ITEM_HINT, item.hint)
                    bundle.putString(Detail.ARG_ITEM_TAGS, item.tags)
                    if (itemDetailFragmentContainer != null) {
                        itemDetailFragmentContainer!!.findNavController().navigate(R.id.show_item_detail, bundle)
                    } else {
                        itemView.findNavController().navigate(R.id.show_item_detail, bundle)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    /**
                     * Context click listener to handle Right click events
                     * from mice and trackpad input to provide a more native
                     * experience on larger screen devices
                     */
                    setOnContextClickListener { v ->
                        val item = v.tag as Record
                        Toast.makeText(v.context, "Context click of item " + item.id, Toast.LENGTH_LONG).show()
                        true
                    }
                }

                setOnLongClickListener { v ->
                    // Setting the item id as the clip data so that the drop target is able to
                    // identify the id of the content
                    val clipItem = ClipData.Item(i.id.toString())
                    val dragData = ClipData(v.tag as? CharSequence, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), clipItem)

                    if (Build.VERSION.SDK_INT >= 24) {
                        v.startDragAndDrop(dragData, View.DragShadowBuilder(v), null, 0)
                    } else {
                        v.startDrag(dragData, View.DragShadowBuilder(v), null, 0)
                    }
                }
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(binding: ItemListContentBinding) : RecyclerView.ViewHolder(binding.root) {
            val idView: TextView = binding.idText
            val contentView: TextView = binding.content
        }

        @SuppressLint("NotifyDataSetChanged")
        fun filter(text: String) {
            values.clear()
            if (text.isEmpty()) {
                values.addAll(allValues)
            } else {
                for (item in allValues) {
                    if (item.site.lowercase().contains(text.lowercase())) {
                        values.add(item)
                    }
                }
            }
            notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}