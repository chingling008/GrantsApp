//package com.example.grantzapp.activity;
//
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.util.List;
//
//public class formAnalyzer {
//
//        String formUrl = "{form_url}";
//        String modelId = "{custom_trained_model_id}";
//        SyncPoller<FormRecognizerOperationResult, List<RecognizedForm>> recognizeFormPoller =
//                formRecognizerClient.beginRecognizeCustomFormsFromUrl(modelId, formUrl);
//
//        List<RecognizedForm> recognizedForms = recognizeFormPoller.getFinalResult();
//
//    for (int i = 0; i < recognizedForms.size(); i++) {
//            RecognizedForm form = recognizedForms.get(i);
//            System.out.printf("----------- Recognized custom form info for page %d -----------%n", i);
//            System.out.printf("Form type: %s%n", form.getFormType());
//            System.out.printf("Form type confidence: %.2f%n", form.getFormTypeConfidence());
//            form.getFields().forEach((label, formField) ->
//                    System.out.printf("Field %s has value %s with confidence score of %f.%n", label,
//                            formField.getValueData().getText(),
//                            formField.getConfidence())
//            );
//        }
//        File form = new File("local/file_path/filename.png");
//        byte[] fileContent;
//
//        {
//            try {
//                fileContent = Files.readAllBytes(form.toPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        InputStream inputStream = new ByteArrayInputStream(fileContent);
//
//        SyncPoller<FormRecognizerOperationResult, List<FormPage>> recognizeContentPoller =
//                formRecognizerClient.beginRecognizeContent(inputStream, form.length());
//
//        List<FormPage> contentPageResults = recognizeContentPoller.getFinalResult();
//
//    for (int i = 0; i < contentPageResults.size(); i++) {
//            FormPage formPage = contentPageResults.get(i);
//
//
//            });
//            // Selection Mark
//            formPage.getSelectionMarks().forEach(selectionMark -> System.out.printf(
//                    "Page: %s, Selection mark is %s within bounding box %s has a confidence score %.2f.%n",
//                    selectionMark.getPageNumber(), selectionMark.getState(), selectionMark.getBoundingBox().toString(),
//                    selectionMark.getConfidence()));
//        }
//
//}
//
//
