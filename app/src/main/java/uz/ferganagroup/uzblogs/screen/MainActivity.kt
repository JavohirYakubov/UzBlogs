package uz.ferganagroup.uzblogs.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ferganagroup.uzblogs.R
import uz.ferganagroup.uzblogs.adapter.PostAdapter
import uz.ferganagroup.uzblogs.adapter.UserAdapter
import uz.ferganagroup.uzblogs.adapter.UserAdapterListener
import uz.ferganagroup.uzblogs.api.ApiService
import uz.ferganagroup.uzblogs.api.BaseResponse
import uz.ferganagroup.uzblogs.model.PostModel
import uz.ferganagroup.uzblogs.model.UserModel
import uz.ferganagroup.uzblogs.screen.posts.PostsActivity

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipe.setOnRefreshListener(this)

        swipe.isRefreshing = true
        loadUsers()
        loadPosts()
    }

    fun loadUsers(){
        ApiService.apiClient().getUsers().enqueue(object: Callback<BaseResponse<List<UserModel>>>{
            override fun onFailure(call: Call<BaseResponse<List<UserModel>>>, t: Throwable) {
                swipe.isRefreshing = false
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG)
            }

            override fun onResponse(
                call: Call<BaseResponse<List<UserModel>>>,
                response: Response<BaseResponse<List<UserModel>>>
            ) {
                swipe.isRefreshing = false
                recyclerUsers.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                recyclerUsers.adapter = UserAdapter(response.body()?.data ?: emptyList(), object: UserAdapterListener{
                    override fun onClick(item: UserModel) {
                        val intent = Intent(this@MainActivity, PostsActivity::class.java)
                        intent.putExtra("extra_data", item)
                        startActivity(intent)
                    }
                })
            }
        })
    }

    fun loadPosts(){
        ApiService.apiClient().getPosts().enqueue(object: Callback<BaseResponse<List<PostModel>>>{
            override fun onFailure(call: Call<BaseResponse<List<PostModel>>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG)
            }

            override fun onResponse(
                call: Call<BaseResponse<List<PostModel>>>,
                response: Response<BaseResponse<List<PostModel>>>
            ) {
                recyclerPosts.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerPosts.adapter = PostAdapter(response.body()?.data ?: listOf())
            }
        })
    }

    override fun onRefresh() {
        loadUsers()
        loadPosts()
    }
}