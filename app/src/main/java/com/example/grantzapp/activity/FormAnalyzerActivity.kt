//package com.example.grantzapp.activity
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.azure.ai.formrecognizer.*
//import com.azure.ai.formrecognizer.models.*
//import com.azure.ai.formrecognizer.models.FormRecognizerOperationResult
//import com.azure.ai.formrecognizer.models.RecognizedForm
//import com.azure.ai.formrecognizer.training.*
//import com.azure.ai.formrecognizer.training.models.*
//import com.azure.core.util.polling.SyncPoller
//import com.example.grantzapp.R
//import java.util.List
//
//class FormAnalyzerActivity : AppCompatActivity() {
//    val key = "<replace-with-your-form-recognizer-key>"
//    val endpoint = "<replace-with-your-form-recognizer-endpoint>"
//
//    var formUrl = "{form_url}"
//    var modelId = "01787cb5-17d5-4bbf-9bab-ba55cc7b9bd5"
//
//    var recognizeFormPoller: SyncPoller<FormRecognizerOperationResult, List<RecognizedForm>> =
//        formRecognizerClient.beginRecognizeCustomFormsFromUrl(modelId, formUrl)
//
//    var recognizedForms: List<RecognizedForm> = recognizeFormPoller.getFinalResult()
//
//
////    val key = "b9652716e30540b4b0ace5aa673524df"
////    val endpoint = "https://grants.cognitiveservices.azure.com/"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_form_analyzer)
//    }
//
//}