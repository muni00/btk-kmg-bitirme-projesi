package com.muazzeznihalbahadir.yemekkitabi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muazzeznihalbahadir.yemekkitabi.R
import com.muazzeznihalbahadir.yemekkitabi.databinding.BesinRecyclerRowBinding
import com.muazzeznihalbahadir.yemekkitabi.model.Besin
import com.muazzeznihalbahadir.yemekkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*

class BesinRecyclerAdapter (val besinList : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>(),BesinClickListener {

    class  BesinViewHolder(var view : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        //burada yapılacaklar view kısmındaki görüntüyü adaptere bağlamak için kullanılacak
        //bildiğin gibi xml de bi şyi kt dosyasına bağlıyorsan inflater kullanırsın
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.besin_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater,R.layout.besin_recycler_row,parent,false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.view.besin = besinList[position]
        holder.view.listener=this

       /* holder.itemView.isim.text = besinList.get(position).besinIsim
        holder.itemView.kalori.text=besinList.get(position).besinKalori

        holder.itemView.setOnClickListener{
            val action = BesinListesiFregmentDirections.actionBesinListesiFregmentToBesinDetayFregment(besinList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.gorselIndir(besinList.get(position).besinGorsel, placeHolderYap(holder.itemView.context))*/
    }

    override fun getItemCount(): Int {
        return besinList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun besinListUpdate(yeniBesinList : List<Besin>){
        besinList.clear()
        besinList.addAll(yeniBesinList)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val uuid = view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinListesiFragmentDirections.actionBesinListesiFregmentToBesinDetayFregment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }
}