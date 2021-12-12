package com.example.momoneynoproblem.PDF;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        PDFView pdfView = findViewById(R.id.pdfView);
        //opens the pdf located in assets folder only
        String reportName = getIntent().getStringExtra("fileName");
        Log.i("PDFVIEWER", reportName);

        //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + reportName;
        //Log.i("PDFViewer", reportName);
        pdfView.fromFile(new File(reportName))
                .load();

        //pdfView.fromAsset(reportName).load();


//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//            System.out.println("hello");
//            //pdfView.fromAsset("samplePDF").load();
//        }

    }
}