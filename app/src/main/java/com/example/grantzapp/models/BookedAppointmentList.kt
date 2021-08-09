package com.example.grantzapp.models

class BookedAppointmentList {

    private var Date: String? = null
    private var Time: String? = null
    private var Donor_ID: String? = null
    private var Student_ID: String? = null


    constructor() {}

    constructor(
        Date: String?,
        Time: String?,
        Donor_ID: String?,
        Student_ID: String?,


        ) {
        this.Date = Date
        this.Time = Time
        this.Donor_ID = Donor_ID
        this.Student_ID = Student_ID

    }

    fun getId(): String? {
        return Donor_ID
    }
    fun setId(id: String?){
        this.Donor_ID = id
    }
    fun getId2(): String? {
        return Student_ID
    }
    fun setId2(id: String?){
        this.Student_ID= id
    }

}


