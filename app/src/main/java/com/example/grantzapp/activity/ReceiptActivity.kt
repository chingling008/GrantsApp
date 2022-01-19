package com.example.grantzapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.grantzapp.R
import com.example.grantzapp.models.Receipt
import com.example.grantzapp.models.Students
import com.example.grantzapp.models.StudentsTable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_donor.*

class ReceiptActivity : AppCompatActivity(), load {

    lateinit var database: DatabaseReference
    lateinit var load: load
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var description: TextView
    private lateinit var spinner: Spinner

    var spinnerList: List<StudentsTable> = ArrayList<StudentsTable>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        database = FirebaseDatabase.getInstance().reference
        database = FirebaseDatabase.getInstance().getReference("StudentsTable")

        load = this

        spinner = findViewById(R.id.SearchableSpinner2)

        bottomSheetDialog = BottomSheetDialog(this)
        val bottom_sheet_view = layoutInflater.inflate(R.layout.layout_donor, null)
        description = bottom_sheet_view.findViewById(R.id.details) as TextView
        imageView = bottom_sheet_view.findViewById(R.id.imageVieww) as ImageView
        textView = bottom_sheet_view.findViewById(R.id.txtnew)
        bottomSheetDialog.setContentView(bottom_sheet_view)

        bottomSheetDialog.setContentView(bottom_sheet_view)

        var isFirstTimeClick = true
        database.addValueEventListener(object : ValueEventListener {
            var spinnerList: MutableList<StudentsTable> = ArrayList()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (spinnerSnapshot in snapshot.children)
                    spinnerList.add(spinnerSnapshot.getValue(StudentsTable::class.java)!!)
                (load as ReceiptActivity).onFirebaseLoadSuccess(spinnerList)
            }

            override fun onCancelled(error: DatabaseError) {
                load.onFirebaseFailed(error.message)
            }
        })
        spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (!isFirstTimeClick) {
                    val spinner = spinnerList[p2]
                    description.text = spinner.purpose2
                    textView.text = spinner.admno2
                    Picasso.get().load(spinner.image2).into(imageView)

                    bottomSheetDialog.show()

                } else {
                    isFirstTimeClick = false
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })
    }
    override fun onFirebaseLoadSuccess(spinnerList: MutableList<StudentsTable>) {
        this.spinnerList = spinnerList
        val students_admno = getstudentsAdmno(spinnerList)
        val adapters = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,students_admno)
        spinner.adapter = adapters
    }
    private fun getstudentsAdmno(spinnerList: List<StudentsTable>): List<String> {
        val admno = ArrayList<String>()
        for (AdmNo in spinnerList)
            AdmNo.AdmNo?.let { admno.add(it) }
        return admno
    }
    override fun onFirebaseFailed(message: String) {

    }
}