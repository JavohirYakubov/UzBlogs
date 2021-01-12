package uz.ferganagroup.uzblogs.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import uz.ferganagroup.uzblogs.model.PostModel
import uz.ferganagroup.uzblogs.model.UserModel

interface Api {
    @Headers("app-id:5ff1572be39494200dc8f0f6")
    @GET("user")
    fun getUsers(): Call<BaseResponse<List<UserModel>>>

    @Headers("app-id:5ff1572be39494200dc8f0f6")
    @GET("post")
    fun getPosts(): Call<BaseResponse<List<PostModel>>>

    @Headers("app-id:5ff1572be39494200dc8f0f6")
    @GET("user/{user_id}/post")
    fun getPostByUser(@Path("user_id") id: String): Call<BaseResponse<List<PostModel>>>
}