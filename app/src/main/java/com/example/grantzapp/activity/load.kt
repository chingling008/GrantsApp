package com.example.grantzapp.activity

import com.example.grantzapp.models.Receipt
import com.example.grantzapp.models.Students
import com.example.grantzapp.models.StudentsTable

interface load {
    fun onFirebaseLoadSuccess(spinnerList: MutableList<StudentsTable>)
    fun onFirebaseFailed(message:String)
}