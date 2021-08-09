package com.example.grantzapp.models

class Donated {
    var amount: String? = null
    var id: String? = null

    constructor(){}

    constructor(
        amount: String?,
        id:String?,
    ){
        this.amount = amount
        this.id=id

    }

    fun getamount2(): String? {
        return amount
    }
    fun setamount2(amount: String?){
        this.amount = amount
    }

}