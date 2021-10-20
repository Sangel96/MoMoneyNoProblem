package com.example.momoneynoproblem.PDF;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Date;

public class PDFMaker extends AppCompatActivity {
    // variables for our buttons.
    // Button btnPDFMake;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;

    // creating a bitmap variable
    // for storing our images
    //Bitmap bmp, scaledbmp;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("report");
    Dataobj object = new Dataobj();

    Button saveNprintbtn,printButton;
    EditText editTextName, editQty;
    Spinner spinner;
    String[] itemList;
    double[] itemPrice;
    ArrayAdapter<String> adapter;

    long invoiceNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfmaker);
        callFindViewById();
        callOnClickListener();
        if (checkPermission() == false){
            requestPermission();
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invoiceNo = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void callOnClickListener() {
        saveNprintbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setInvoiceNo(invoiceNo + 1);
                object.setName(String.valueOf(editTextName.getText()));
                object.setDate(new Date().getTime());
                object.setFuelType(spinner.getSelectedItem().toString());
                object.setFuelQty(Double.valueOf(String.valueOf(editQty.getText())));
                object.setAmount(Double.valueOf(object.getFuelQty()*itemPrice[spinner.getSelectedItemPosition()]));

                //object is saved into the database
                myRef.child(String.valueOf(invoiceNo + 1)).setValue(object);

                printPDF();



            }
        });
    }

    private void printPDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint title = new Paint();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));

        canvas.drawText("Account Report", 20, 20, paint);
        paint.setTextSize(8.5f);
        canvas.drawText("By: Mo Money No Problem",20,40,paint);
        canvas.drawText("Beginning of Report",20,55,paint);

        title.setStyle(Paint.Style.STROKE);
        title.setPathEffect(new DashPathEffect(new float[]{5,5}, 0));
        title.setStrokeWidth(2);
        canvas.drawLine(20,65,230,65, title);

        String test = "Customer Name: " + editTextName.getText();
        canvas.drawText(test, 20,80, paint);
        canvas.drawLine(20,90,230,90, title);

        canvas.drawText("Purchase", 20,105, paint);

        canvas.drawText(spinner.getSelectedItem().toString(), 20,135,paint);
        canvas.drawText(editQty.toString(), 120,135, paint);

        double amount = itemPrice[spinner.getSelectedItemPosition()]*Double.parseDouble(editQty.getText().toString());
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.valueOf((amount)), 230,135, paint    );

        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawLine(20,210,230,210, title);

        paint.setTextSize(10.0f);
        canvas.drawText("total", 120, 225,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Date: " + new Date().getTime(), 20,260,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(8.5f);

        canvas.drawText(String.valueOf(invoiceNo+1), 20, 275,paint);
        canvas.drawText("Method: Tap", 20,290,paint);
        canvas.drawText("thanks", canvas.getWidth()/2, 320, paint);

        pdfDocument.finishPage(myPage);

        File file = new File(this.getExternalFilesDir("/"), "PDFMaker_PDF.pdf");

        pdfDocument.close();
    }

    private void callFindViewById() {
    saveNprintbtn = findViewById(R.id.btnSaveAndPrint);
    printButton = findViewById(R.id.btnPrint);
    editTextName = findViewById(R.id.editTextName);
    editQty = findViewById(R.id.editTextQty);
    spinner = findViewById(R.id.spinner);
    itemList = new String[]{"test"};
    itemPrice = new double[]{600};
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList  );
    spinner.setAdapter(adapter);
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

}


//        requestPermission();
//
//        // initializing our variables.
//        btnPDFMake = findViewById(R.id.PDFMake);
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cover_for_app);
//        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);
//
//        // below code is used for
//        // checking our permissions.
//        if (checkPermission()) {
//            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//        } else {
//            requestPermission();
//        }
//
//        btnPDFMake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // calling method to
//                // generate our PDF file.
//                generatePDF();
//                Toast.makeText(PDFMaker.this, "generated called", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void generatePDF() {
//        // creating an object variable
//        // for our PDF document.
//        PdfDocument pdfDocument = new PdfDocument();
//
//        // two variables for paint "paint" is used
//        // for drawing shapes and we will use "title"
//        // for adding text in our PDF file.
//        Paint paint = new Paint();
//        Paint title = new Paint();
//
//        // we are adding page info to our PDF file
//        // in which we will be passing our pageWidth,
//        // pageHeight and number of pages and after that
//        // we are calling it to create our PDF.
//        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
//
//        // below line is used for setting
//        // start page for our PDF file.
//        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
//
//        // creating a variable for canvas
//        // from our page of PDF.
//        Canvas canvas = myPage.getCanvas();
//
//        // below line is used to draw our image on our PDF file.
//        // the first parameter of our drawbitmap method is
//        // our bitmap
//        // second parameter is position from left
//        // third parameter is position from top and last
//        // one is our variable for paint.
//        canvas.drawBitmap(scaledbmp, 56, 40, paint);
//
//        // below line is used for adding typeface for
//        // our text which we will be adding in our PDF file.
//        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//
//        // below line is used for setting text size
//        // which we will be displaying in our PDF file.
//        title.setTextSize(15);
//
//        // below line is sued for setting color
//        // of our text inside our PDF file.
//        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
//
//        // below line is used to draw text in our PDF file.
//        // the first parameter is our text, second parameter
//        // is position from start, third parameter is position from top
//        // and then we are passing our variable of paint which is title.
//        canvas.drawText("A portal for IT professionals.", 209, 100, title);
//        canvas.drawText("Geeks for Geeks", 209, 80, title);
//
//        // similarly we are creating another text and in this
//        // we are aligning this text to center of our PDF file.
//        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
//        title.setTextSize(15);
//
//        // below line is used for setting
//        // our text to center of PDF.
//        title.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText("This is sample document which we have created.", 396, 560, title);
//
//        // after adding all attributes to our
//        // PDF file we will be finishing our page.
//        pdfDocument.finishPage(myPage);
//
//        // below line is used to set the name of
//        // our PDF file and its path.
//        File file = new File(Environment.getExternalStorageDirectory().getPath().toString() + "/testPDF.pdf");
//
//        try {
//            // after creating a file name we will
//            // write our PDF file to that location.
//            pdfDocument.writeTo(new FileOutputStream(file));
//
//            // below line is to print toast message
//            // on completion of PDF generation.
//            Toast.makeText(PDFMaker.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            // below line is used
//            // to handle error
//            e.printStackTrace();
//        }
//        // after storing our pdf to that
//        // location we are closing our PDF file.
//        pdfDocument.close();
//
//    }
//
//    private boolean checkPermission() {
//        // checking of permissions.
//        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
//        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission() {
//        // requesting permissions if not provided.
//        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0) {
//
//                // after requesting permissions we are showing
//                // users a toast message of permission granted.
//                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                if (writeStorage && readStorage) {
//                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//        }
//    }
//}