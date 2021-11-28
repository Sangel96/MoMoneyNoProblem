package com.example.momoneynoproblem.PDF;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PDFMaker extends AppCompatActivity {
    // variables for our buttons.
    // Button btnPDFMake;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;
    ArrayList<String> userData = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Transcation");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfmaker);
        if (checkPermission() == false){
            requestPermission();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Transactions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userData.clear();
                for (DataSnapshot temp : snapshot.getChildren()){
                    String tempData = new String(temp.getValue().toString());
                    userData.add( tempData );
                    //  Log.i("PDFMaker",temp.getValue().toString());
                }
                createPdf(userData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }

    private void createPdf(ArrayList<String> data) {
        String mFileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(System.currentTimeMillis());
        // alternative "yyyy_MM_dd_HH_mm_ss
        String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfpath, mFileName + ".pdf");
        OutputStream outputStream = null;
        Log.i("PDFMaker", String.valueOf(data.size()));

        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PdfWriter writer = new PdfWriter(file);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDocument);
            Singleton user = Singleton.getInstance();
            String temp = new String(user.getUserID());
            document.add(new Paragraph("USER :" + temp));
            Paragraph paragraph = new Paragraph("TEST");

            for (int i = 0; i< data.size(); i++){
                document.add(new Paragraph(data.get(i).toString()));
            }

            document.add(paragraph);


            document.close();
            Toast.makeText(this, "created", Toast.LENGTH_SHORT).show();

//            //itext 7 start
//            //paragraph
//            Paragraph paragraph1 = new Paragraph("Hello f");
//            document.add(paragraph1);
//            //adding image
//            String imgSrc = "filelocation";
//            ImageData data = ImageDataFactory.create(imgSrc);
//            Image image = new Image(data);
//            document.add(image);
//            //list
//            List list1 = new List();
//            list1.add("Java");
//            //itext7 end

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
     }
}
