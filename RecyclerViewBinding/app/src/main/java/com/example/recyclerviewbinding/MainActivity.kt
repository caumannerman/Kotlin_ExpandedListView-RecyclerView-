package com.example.recyclerviewbinding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    //db에서 불러오는 식으로, 프로필 data와 체크
    val data: MutableList<Profile> = loadProfile()
    //다 false로 초기화
    private var genderChecked: String = ""
    private var newName: String = "123"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CustomAdapter()
        //adapter에 데이터 전달
        adapter.listData = data


        binding.rcView.adapter = adapter
        binding.rcView.layoutManager = LinearLayoutManager(this)

        //main 위젯들
        binding.rgGender.setOnCheckedChangeListener { radioGroup, checkedId ->
            when(checkedId) {
                R.id.rbtn_male -> genderChecked = "male"
                R.id.rbtn_female -> genderChecked = "female"
            }
        }

        binding.etName.addTextChangedListener {
            newName = it.toString()
        }


        //추가 버튼

        binding.btnAdd.setOnClickListener {
            if ( genderChecked.isNotEmpty() && newName.isNotEmpty() ){
                val tempProfile = Profile(genderChecked,newName,"detail 추가된...")
                adapter.addProfile(tempProfile)
            }
        }

        //선택 삭제
        binding.btnDelete.setOnClickListener {
            adapter.deleteProfile()
        }

    }

    fun loadProfile(): MutableList<Profile> {
        val data: MutableList<Profile> = mutableListOf()
        for( no in 1..100){
            val name = "이름 $no"
            val gender =  if ((Math.random()*100) < 50 ){
                "male"
            }  else {
                "female"
            }
            val profile = Profile(gender ,name,"detail입니다")
            data.add(profile)
            }
        return data
        }



}