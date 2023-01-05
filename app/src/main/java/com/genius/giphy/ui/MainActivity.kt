package com.genius.giphy.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.genius.giphy.PaginationScrollListener
import com.genius.giphy.adapter.GifsAdapter
import com.genius.giphy.adapter.image
import com.genius.giphy.databinding.ActivityMainBinding
import com.genius.giphy.model.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity: AppCompatActivity(),
    SearchView.OnQueryTextListener {

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false
    private var currentSearchPhrase = "hi"
    private var totalCountOfGifs = 0L


    private val mainViewModel: MainViewModel by viewModel()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val adapter = GifsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRecyclerView()

        setupSearchView()

        observeGifs()

        if (!checkForInternet(this)) {

            observeFavourites()

        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onTextSearched(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onTextSearched(newText)
        return true
    }

    private fun onTextSearched(text: String?) {

        image.clear()


        if (text != null)

            mainViewModel.search(text)

    }

    private fun setupRecyclerView() {


        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(_binding!!.root)

        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object: PaginationScrollListener( LinearLayoutManager(this)){
            override fun loadMoreItems() { binding.recyclerView
                isLoading = true
                observeGifs()
              //  loadData(currentSearchPhrase, currentPage * Companion.ITEMS_PER_PAGE_LIMIT, false);
                currentPage+=1
            }

            override fun isLoading(): Boolean = isLoading

            override fun isLastPage(): Boolean = isLastPage
        })

    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setOnQueryTextListener(this@MainActivity)
            searchView.isFocusable = false
            searchView.isIconified = false
            searchView.clearFocus()
        }

    }

    private fun  observeGifs(
    ) {

            mainViewModel.gifs.observe(
                this
            ) {

                it.let { resource ->
                    when (resource) {

                        is com.genius.giphy.data.remote.Result.Success -> {

                            image.clear()

                            adapter.submitList(resource.data)

                            for (i in resource.data) {

                                mainViewModel.saveFavourite(i)

                            }

                        }

                        else -> {}
                    }
                }
            }
        }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private  fun  observeFavourites(){
        mainViewModel.saveGifs.observe(this, {
            it?.let { list ->
                with(binding) {


                    if(list.isNullOrEmpty()) {

                    } else {

                        image.clear()

                        adapter.submitList(mainViewModel.saveGifs.value)
                    }
                }
            }
        })
    }

    companion object {
        const val ITEMS_PER_PAGE_LIMIT = 5
    }


}






