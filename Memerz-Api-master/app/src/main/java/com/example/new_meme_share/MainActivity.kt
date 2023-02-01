package com.example.new_meme_share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private val url = "https://meme-api.com/gimme"

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button= findViewById(R.id.shareButton)
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra("Share this", "this app")
            val chooser = Intent.createChooser(intent,"Share this to....")
            startActivity(chooser)
        }

        imageView = findViewById(R.id.imageView)
        loadImageFromApi()
    }

    private fun loadImageFromApi() {
        val queue =Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, this.url, null,
            {response ->
                Log.d("result", response.toString())
                Picasso.get().load(response.get("url").toString()).placeholder(R.drawable.loading1).into(imageView)
            },
            {
                Log.e("error", it.toString())
                Toast.makeText(applicationContext,
                    "Error in loading the meme from the server",
                    Toast.LENGTH_SHORT).show()
                })
                    queue.add(request)

    }

    fun changeImage(view: View) {
        this.loadImageFromApi()
    }
}
