package com.muazzeznihalbahadir.yemekkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.muazzeznihalbahadir.yemekkitabi.R
import com.muazzeznihalbahadir.yemekkitabi.adapter.BesinRecyclerAdapter
import com.muazzeznihalbahadir.yemekkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi_fregment.*

class BesinListesiFragment : Fragment() {
    private lateinit var viewModel: BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_besin_listesi_fregment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=recyclerBesinAdapter

        swipeRefreshLayout.setOnRefreshListener {

            textView.visibility=View.GONE
            recyclerView.visibility=View.GONE
            besinProgress.visibility=View.VISIBLE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerView.visibility=View.VISIBLE
                recyclerBesinAdapter.besinListUpdate(it)
            }
        })

        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    textView.visibility=View.VISIBLE
                    recyclerView.visibility=View.GONE
                    besinProgress.visibility=View.GONE
                }
                else
                    textView.visibility=View.GONE
            }
        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    textView.visibility=View.GONE
                    recyclerView.visibility=View.GONE
                    besinProgress.visibility=View.VISIBLE
                }
                else
                    besinProgress.visibility=View.GONE
            }
        })
    }
}