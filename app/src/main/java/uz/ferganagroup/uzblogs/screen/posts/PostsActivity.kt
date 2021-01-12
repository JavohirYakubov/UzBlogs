package uz.ferganagroup.uzblogs.screen.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.activity_posts.swipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ferganagroup.uzblogs.R
import uz.ferganagroup.uzblogs.adapter.PostAdapter
import uz.ferganagroup.uzblogs.api.ApiService
import uz.ferganagroup.uzblogs.api.BaseResponse
import uz.ferganagroup.uzblogs.model.PostModel
import uz.ferganagroup.uzblogs.model.UserModel

class PostsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        user = intent.getSerializableExtra("extra_data") as UserModel

        tvTitle.text = user.firstName + " " + user.lastName
        swipe.setOnRefreshListener(this)
        
        loadPosts()
    }

    override fun onRefresh() {
        loadPosts()
    }

    fun loadPosts(){
        swipe.isRefreshing = true

        ApiService.apiClient().getPostByUser(user.id).enqueue(object: Callback<BaseResponse<List<PostModel>>>{
            override fun onFailure(call: Call<BaseResponse<List<PostModel>>>, t: Throwable) {
                swipe.isRefreshing = false
                Toast.makeText(this@PostsActivity, t.localizedMessage, Toast.LENGTH_LONG)
            }

            override fun onResponse(
                call: Call<BaseResponse<List<PostModel>>>,
                response: Response<BaseResponse<List<PostModel>>>
            ) {
                swipe.isRefreshing = false
                recyclerPosts.layoutManager = LinearLayoutManager(this@PostsActivity)
                recyclerPosts.adapter = PostAdapter(response.body()?.data ?: listOf())
            }
        })
    }

}