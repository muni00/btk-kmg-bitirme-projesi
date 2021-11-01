package com.muazzeznihalbahadir.yemekkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.muazzeznihalbahadir.yemekkitabi.R
import com.muazzeznihalbahadir.yemekkitabi.databinding.FragmentBesinDetayFregmentBinding
import com.muazzeznihalbahadir.yemekkitabi.model.Besin
import com.muazzeznihalbahadir.yemekkitabi.util.gorselIndir
import com.muazzeznihalbahadir.yemekkitabi.util.placeHolderYap
import com.muazzeznihalbahadir.yemekkitabi.viewmodel.BesinDetayViewModel
import kotlinx.android.synthetic.main.fragment_besin_detay_fregment.*


class BesinDetayFragment : Fragment() {
    private lateinit var viewModel : BesinDetayViewModel
    private var besinid = 0
    private lateinit var dataBinding:FragmentBesinDetayFregmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detay_fregment,container,false)
        return dataBinding.root
        //inflater.inflate(R.layout.fragment_besin_detay_fregment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinid = BesinDetayFragmentArgs.fromBundle(it).besinid
            print(besinid)
        }
        viewModel = ViewModelProviders.of(this).get(BesinDetayViewModel::class.java)
        viewModel.roomVerisiniAl(besinid)


        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer {besin ->
            besin?.let {
                dataBinding.secilenBesin=it
                /*
                besinIsmi.text=it.besinIsim
                besinKalori.text=it.besinKalori
                besinKarbonhidrat.text=it.besinKarbonhidrat
                besinProtein.text=it.besinProtein
                besinYag.text=it.besinYag
                context?.let {
                    besinImage.gorselIndir(besin.besinGorsel, placeHolderYap(it))

                }*/
            }
        })
    }
}