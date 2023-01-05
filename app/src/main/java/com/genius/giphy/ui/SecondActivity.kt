package com.genius.giphy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.genius.giphy.R
import com.genius.giphy.adapter.ImageAdapter
import com.genius.giphy.adapter.image

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        setContentView(R.layout.activity_second)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView2)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager =
            LinearLayoutManager(this@SecondActivity, LinearLayoutManager.HORIZONTAL, true)

        var position: Int = intent.getIntExtra("position", 0)

        val adapter = ImageAdapter(this, image)

        recyclerView.adapter = adapter

        recyclerView.scrollToPosition(position)
        recyclerView.invalidate()

        position = 0

    }

}



