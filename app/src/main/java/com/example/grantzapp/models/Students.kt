package com.example.grantzapp.models

import android.media.Image

class  Students {

    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var school: String? = null
    var AdmNo: String? = null
    var uid : String? = null
    var image:String? = null
    var amount:String? = null
    var Dates:String? = null
    var Purpose:String? = null




    constructor(){}

    constructor(
            firstName:String?,
            lastName:String?,
            email:String?,
            school:String?,
            AdmNo:String?,
            uid:String?,
            image: Image?,
            amount: String?,
            Dates:String?,
            Purpose:String?,


    ){
        this.firstName = firstName
        this.lastName =lastName
        this.email = email
        this.school = school
        this.AdmNo = AdmNo
        this.uid = uid
        this.image= image.toString()
        this.amount = amount
        this.Purpose = Purpose
        this.Dates = Dates


    }

    fun getId(): String? {
        return uid
    }
    fun setId(id: String?){
        this.uid = id
    }

    fun getfirstName1(): String? {
        return firstName
    }
    fun setfirstName1(firstName: String?){
        this.firstName = firstName
    }

    fun getlastName1(): String? {
        return lastName
    }
    fun setlastName1(lastName: String?){
        this.lastName = lastName
    }

    fun getAdmno1(): String? {
        return AdmNo
    }
    fun setAdmno1(AdmNo: String?){
        this.AdmNo = AdmNo
    }

    fun getimage1(): String? {
        return image
    }
    fun setimage1(image: String?){
        this.image = image
    }

    fun getamount4(): String? {
        return amount
    }
    fun setamount4(amount: String?){
        this.amount= amount
    }
    fun getpurpose1(): String? {
        return Purpose
    }
    fun setpurpose1(purpose: String?){
        this.Purpose = purpose
    }
    fun getschool1(): String? {
        return school
    }
    fun setschool1(school: String?){
        this.school = school
    }

    fun getemail3(): String? {
        return email
    }
    fun setemail3(email: String?){
        this.email = email
    }
    fun getDates4(): String? {
        return Dates
    }
    fun setDates4(Dates: String?){
        this.Dates = Dates
    }
}
