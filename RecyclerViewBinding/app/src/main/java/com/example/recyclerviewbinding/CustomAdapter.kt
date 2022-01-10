package com.example.recyclerviewbinding

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.forEach
import androidx.core.util.keyIterator
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewbinding.databinding.ItemRecyclerBinding

class CustomAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var listData = mutableListOf<Profile>()
    var checkedData = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if( holder is Holder) {
            val profile = listData[position]

            holder.setProfile(profile)
        }
    }

    override fun getItemCount(): Int {
       return listData.size
    }

    fun addProfile(profile: Profile){
        listData.add(profile)
        notifyItemInserted(listData.size-1)
    }

    fun deleteProfile(){
        var temp:Int = 0
        checkedData.forEach { i, _ ->
            listData.removeAt(i - temp)
            temp += 1
        }
        checkedData.clear()
        notifyDataSetChanged()
    }



   inner class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        fun setProfile(profile: Profile) {

            binding.ivGender.apply {
                if (profile.gender == "male") {
                    setImageResource(R.drawable.ic_launcher_foreground)
                } else {
                    setImageResource(R.drawable.ic_launcher_background)
                }
            }
            binding.tvName.text = "${profile.name}"
            binding.tvName.setOnClickListener {
                Toast.makeText( binding.root.context,"${binding.tvName.text} text Clicked",Toast.LENGTH_SHORT).show()
            }

            //상세 버튼 click
            binding.btnDetail.setOnClickListener {
                Toast.makeText( binding.root.context,"${binding.tvName.text} button Clicked",Toast.LENGTH_SHORT).show()
            }
            //해당 position의 설정
            binding.cbCheck.isChecked = checkedData[adapterPosition]?:false

            binding.cbCheck.setOnClickListener {
                if (!binding.cbCheck.isChecked) {
                    checkedData.delete(adapterPosition)
                    //Log.d("boolean", "${checkedData}")
                }
                else
                    checkedData.put(adapterPosition, true)
                Log.d("boolean", "${checkedData.indexOfValue(true)}")

                notifyItemChanged(adapterPosition)

            }

            binding.llRow.setOnClickListener {
                Log.d("row click", "row Click!! ${binding.tvName.text}")
                Toast.makeText( binding.root.context,"${binding.tvName.text} low Clicked",Toast.LENGTH_SHORT).show()

            }
        }
    }
}

