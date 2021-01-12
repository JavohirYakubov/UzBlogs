package uz.ferganagroup.uzblogs.model

data class PostModel(
    val id: String,
    val owner: UserModel,
    val text: String,
    val publishDate: String,
    val image: String
)