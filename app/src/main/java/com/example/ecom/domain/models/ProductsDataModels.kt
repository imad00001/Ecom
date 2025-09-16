package com.example.ecom.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDataModels(

    val name : String = "",
    val description : String = "",
    val price : String = "",
    val finalPrice : String = "",
    val category : String = "",
    val image : String = "",
    val date : Long = System.currentTimeMillis(),
    val createBy : String = "",
    val availabileUints : Int = 0,
    val productId : String = "",

)
