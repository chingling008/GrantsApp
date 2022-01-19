//package com.example.grantzapp.activity;
//
//
//import com.google.firebase.database.core.view.View;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.util.List;
//
//public class formAnalyzer {
//    String analyzeFilePath = "{file_source_url}";
//    String modelId = "{custom_trained_model_id}";
//    SyncPoller<View.OperationResult, IterableStream<RecognizedForm>> recognizeFormPoller =
//            formRecognizerClient.beginRecognizeCustomFormsFromUrl(analyzeFilePath, modelId);
//
//    IterableStream<RecognizedForm> recognizedForms = recognizeFormPoller.getFinalResult();
//
//recognizedForms.forEach(form -> {
//        System.out.println("----------- Recognized Form -----------");
//        System.out.printf("Form type: %s%n", form.getFormType());
//        form.getFields().forEach((label, formField) -> {
//            System.out.printf("Field %s has value %s with confidence score of %d.%n", label,
//                    formField.getFieldValue(),
//                    formField.getConfidence());
//        });
//        System.out.print("-----------------------------------");
//    });
//
//
//
