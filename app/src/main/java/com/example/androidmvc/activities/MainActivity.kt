package com.example.androidmvc.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvc.R
import com.example.androidmvc.adapters.PostAdapter
import com.example.androidmvc.models.Post
import com.example.androidmvc.networking.retrofit.RetrofitHttp
import com.example.androidmvc.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    fun dialogForDeletePost(post: Post, position: Int) {
        val title = "Delete"
        val body = "Do you want to delete?"
        Utils.customDialog(this, title, body, object : Utils.DialogListener {
            override fun onPositiveClick() {
                apiPostDelete(post)
            }

            override fun onNegativeClick() {

            }
        })
    }

    private fun initViews() {
        context = this
        recyclerView = findViewById(R.id.main_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        apiPostList()
    }

    fun refreshAdapter(posters: ArrayList<Post>) {
        val adapter = PostAdapter(this, posters)
        recyclerView.adapter = adapter
    }

    private fun apiPostList() {
        RetrofitHttp.postService.listPost().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                refreshAdapter(response.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

            }
        })
    }

    private fun apiPostDelete(post: Post) {
        RetrofitHttp.postService.deletePost(post.id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                apiPostList()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }
        })
    }
}



