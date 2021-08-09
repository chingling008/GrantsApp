package com.example.grantzapp.models

import kotlin.math.E

class Donors {

    var FirstName: String? = null
    var LastName: String? = null
    var Email: String? = null
    var Image:String? = null
    var uid : String? = null
    var dates: String? = null
    var time: String? = null
    var donorname: String? = null
    var donoremail: String? = null
    var studentId: String? = null
    var studentemail: String? = null
    constructor(){}

    constructor(
        FirstName: String?,
        LastName: String?,
        Email: String?,
        Image: String?,
        uid:String?,
        dates: String?,
        donorname: String?,
        donoremail: String?,
        time: String?,
//        studentId: String?,
        studentemail: String?,


        ){
        this.FirstName = FirstName
        this.LastName = LastName
        this.Email = Email
        this.Image = Image
        this.uid = uid
        this.dates = dates
        this.donorname = donorname
        this.donoremail = donoremail
        this.time = time
//        this.studentId = studentId
        this.studentemail = studentemail

    }

    fun getId(): String? {
        return uid
    }
    fun setId(id: String?){
        this.uid = id
    }
    fun getFirst(): String? {
        return FirstName
    }
    fun setFirst(FirstName: String?){
        this.FirstName = FirstName
    }

    fun getlast(): String? {
        return LastName
    }
    fun setlast(LastName: String?){
        this.LastName = LastName
    }


    fun getemail3(): String? {
        return Email
    }
    fun setemail3(Email: String?){
        this.Email = Email
    }

    fun getimage3(): String? {
        return Image
    }
    fun setimage3(Image: String?){
        this.Image = Image
    }

    fun gettime1(): String? {
        return time
    }

    fun ssettime1(time: String?) {
        this.time = time
    }

    fun getdates1(): String? {
        return dates
    }

    fun setdates1(dates: String?) {
        this.dates= dates
    }

    fun getdonoremail1(): String? {
        return donoremail
    }

    fun setdonoremail1(donoremail: String?) {
        this.donoremail= donoremail
    }

    fun getdonorname1(): String? {
        return donorname
    }

    fun setdonorname1(donorname: String?) {
        this.donorname= donorname
    }


    fun getstudentemail2(): String? {
        return studentemail
    }

    fun setstudentemail2(studentemail: String?) {
        this.studentemail= studentemail
    }

//    fun getstudentId4(): String? {
//        return studentId
//    }
//
//    fun setstudentId4(studentId: String?) {
//        this.studentId= studentId
//    }


}
