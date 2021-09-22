package com.pcm.gamzigamzi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcm.gamzigamzi.DataManager
import com.pcm.gamzigamzi.ListAdapter
import com.pcm.gamzigamzi.ListViewModel
import com.pcm.gamzigamzi.R
import com.pcm.gamzigamzi.databinding.ActivityTextBinding

class TextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextBinding

    private lateinit var adapter: ListAdapter

    private val viewModel by lazy { ViewModelProvider(this).get(ListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)


        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //xml 보여지는부분
        binding.headerTitle.headerText.text = "연결된 센서 목록"
        binding.layoutFooter.btnSensors.text = "센서 추가 및 내용 변경"

        nextpage()

        //클릭된 부분의 id를 받자
        adapter = ListAdapter(this) {
            val rasid = it.id
            val rasaddress = it.address
            DataManager.rasid =rasid
            DataManager.address =rasaddress
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        observerData()

    }

    fun observerData(){
        viewModel.fetchData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

    fun nextpage(){
        binding.layoutFooter.btnSensors.setOnClickListener{
            val intent = Intent(this, AddRasActivity::class.java)
            startActivity(intent)
        }
    }
}