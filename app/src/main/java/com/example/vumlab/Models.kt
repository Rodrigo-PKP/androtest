package com.example.vumlab

data class User(val userID: String? = null, val firstName: String? = null, val lastName: String? = null, val currentBill: String? = null, val email: String? = null, val currentOrder: String? = null, val partyID : String? = null, val profileImageURI: String? = null, val userData: HashMap<String, *>? = null)


data class Partner(val partnerID: String? = null, val ownerID: String? = null, val name: String? = null, val description: String? = null)
data class PartnerMenu(val categories: Array<Category>? = null)
data class Category(val categoryID: String? = null, val categoryName: String? = null, val categoryBannerURI: String? = null)
data class Product(val productID: String, val availability: Boolean, val categoryID: String, val sectionID: String, val portion: String, val price: Long, val portionsAndPrices: HashMap<*, *>)
data class Item(val itemID: String, val name: String, val quantity: Long, val price: Long, val portion: String, val variant: String, val state: String)