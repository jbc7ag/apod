package com.jbc7ag.apod.ui.search

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.jbc7ag.apod.R
import com.jbc7ag.apod.Utils
import com.jbc7ag.apod.databinding.FragmentSearchBinding
import com.jbc7ag.apod.ui.details.DetailActivity
import java.text.SimpleDateFormat
import java.util.*


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    enum class SearchType{ WORD, DATE}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.searchViewModel = searchViewModel

        binding.searchGrid.adapter = SearchAdapter(SearchAdapter.OnClickListener {
            openDetailActivity(it.data[0].title, it.data[0].description508, it.links?.get(0)?.href, it.data[0].dataCreated);
        })

        setGridSpan(resources.configuration)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.searchByTerm(query)
                setSearchTermText(SearchType.WORD, query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //searchViewModel.searchByTerm(newText)
                return false
            }
        })

        searchViewModel.openDateSelector.observe(viewLifecycleOwner, Observer { open ->
            showDateSelector()
        })

        searchViewModel.showApodDetails.observe(viewLifecycleOwner, Observer { show ->
            val apodProperty = searchViewModel.apodProperty.value
            if (apodProperty != null) {
                openDetailActivity(apodProperty.title, apodProperty.description, apodProperty.url, apodProperty.date);
            }
        })


        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setGridSpan(newConfig)
    }

    fun setGridSpan(config: Configuration){

        val spanCount: Int? = context?.let { Utils.calculateNoOfColumns(it, 200f) }
        binding.searchGrid.setLayoutManager(GridLayoutManager(context, spanCount ?: 2))

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.searchGrid.setLayoutManager(GridLayoutManager(context, 4))
        }else{
            binding.searchGrid.setLayoutManager(GridLayoutManager(context, 2))
        }
    }

    private fun showDateSelector() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val simpledateformate = SimpleDateFormat("dd/MM/yyyy")
        val minDate: Long = simpledateformate.parse("16/06/1995").time
        val maxDate: Long = Date().time

        val dpd = context?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    val date = "$dayOfMonth /$monthOfYear /$year";
                    setSearchTermText(SearchType.DATE, date)
                    searchViewModel.searchByDate(
                        dayOfMonth.toString(),
                        monthOfYear.toString(),
                        year.toString()
                    )
                },
                year,
                month,
                day
            )
        }
        dpd?.datePicker?.minDate = minDate
        dpd?.datePicker?.maxDate = maxDate
        dpd?.show()
    }

    private fun setSearchTermText(type: SearchType, term: String){

        val typeSearch = when(type) {
            SearchType.DATE -> getString(R.string.txt_date)
            SearchType.WORD -> getString(R.string.txt_word)
        }

        val searchBy = String.format(getString(R.string.txt_search_term), typeSearch)

        binding.txtSearchTerm.visibility = View.VISIBLE
        binding.txtSearchTerm.text = term
        binding.txtSearch.text = searchBy
    }

    private fun openDetailActivity(title: String?, description: String?, url: String?, date: String?){
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("title", title ?: "")
        intent.putExtra("content", description ?: "")
        intent.putExtra("url", url ?: "")
        intent.putExtra("date", date ?: "")
        startActivity(intent)
    }
}