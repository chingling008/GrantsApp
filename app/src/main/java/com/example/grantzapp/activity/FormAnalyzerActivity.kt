//package com.example.grantzapp.activity
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//
//import com.example.grantzapp.R
//import java.io.ByteArrayInputStream
//import java.io.File
//import java.io.InputStream
//import java.nio.file.Files
//import java.util.List
//
//
//
//
//class FormAnalyzerActivity : AppCompatActivity() {
//    val key = "b9652716e30540b4b0ace5aa673524df"
//    val endpoint = "https://grants.cognitiveservices.azure.com/"
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
//
////    val key = "b9652716e30540b4b0ace5aa673524df"
////    val endpoint = "https://grants.cognitiveservices.azure.com/"
//
//
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_form_analyzer)
//
//        for (int i = 0; i < recognizedForms.size(); i++) {
//            RecognizedForm form = recognizedForms.get(i);
//            formField.getValueData().getText(),
//            formField.getConfidence())
//
//        val form = File("local/file_path/filename.png")
//        val fileContent: ByteArray = Files.readAllBytes(form.toPath())
//        val inputStream: InputStream = ByteArrayInputStream(fileContent)
//
//        val recognizeContentPoller: SyncPoller<FormRecognizerOperationResult, kotlin.collections.List<FormPage>> =
//            formRecognizerClient.beginRecognizeContent(inputStream, form.length())
//
//        val contentPageResults: kotlin.collections.List<FormPage> =
//            recognizeContentPoller.getFinalResult()
//
//        for (i in contentPageResults.indices) {
//            val formPage: FormPage = contentPageResults[i]
//            System.out.printf("----Recognizing content info for page %d ----%n", i)
//            // Table information
//            System.out.printf(
//                "Has width: %f and height: %f, measured with unit: %s.%n", formPage.getWidth(),
//                formPage.getHeight(),
//                formPage.getUnit()
//            )
//            formPage.getTables().forEach { formTable ->
//                System.out.printf(
//                    "Table has %d rows and %d columns.%n", formTable.getRowCount(),
//                    formTable.getColumnCount()
//                )
//                formTable.getCells().forEach { formTableCell ->
//                    System.out.printf(
//                        "Cell has text %s.%n",
//                        formTableCell.getText()
//                    )
//                }
//            }
//            // Selection Mark
//            formPage.getSelectionMarks().forEach { selectionMark ->
//                System.out.printf(
//                    "Page: %s, Selection mark is %s within bounding box %s has a confidence score %.2f.%n",
//                    selectionMark.getPageNumber(),
//                    selectionMark.getState(),
//                    selectionMark.getBoundingBox().toString(),
//                    selectionMark.getConfidence()
//                )
//            }
//        }
//    }
//
//}