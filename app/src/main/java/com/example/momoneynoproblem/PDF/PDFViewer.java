package com.example.momoneynoproblem.PDF;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PDFViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        PDFView pdfView = findViewById(R.id.pdfView);
        //opens the pdf located in assets folder only
        pdfView.fromAsset("Generated Financial Report.pdf").load();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            System.out.println("hello");
            //pdfView.fromAsset("samplePDF").load();
        }

    }
}