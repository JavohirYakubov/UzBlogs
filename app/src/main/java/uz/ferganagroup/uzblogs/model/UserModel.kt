package uz.ferganagroup.uzblogs.model

import java.io.Serializable


data class UserModel(
    val id: String,
    val firstName: String,
    val lastName: String,
    val picture: String
): Serializable