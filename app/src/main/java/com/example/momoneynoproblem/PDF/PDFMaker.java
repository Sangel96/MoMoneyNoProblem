package com.example.momoneynoproblem.PDF;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("report");


    ActivityResultLauncher<String> mgetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfmaker);
        if (checkPermission() == false){
            requestPermission();
        }
        createPdf();

//
//
//        callFindViewById();
//        callOnClickListener();
//        if (checkPermission() == false){
//            requestPermission();
//        }
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                invoiceNo = snapshot.getChildrenCount();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

//    private void callOnClickListener() {
//        saveNprintbtn.setOnClickListener(v -> {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//               mgetContent.launch("download/*");
//
//            }
//            object.setInvoiceNo(invoiceNo + 1);
//            object.setName(String.valueOf(editTextName.getText()));
//            object.setDate(new Date().getTime());
//            object.setFuelType(spinner.getSelectedItem().toString());
//            object.setFuelQty(Double.valueOf(String.valueOf(editQty.getText())));
//            object.setAmount(Double.valueOf(object.getFuelQty()*itemPrice[spinner.getSelectedItemPosition()]));
//
//            //object is saved into the database
//            myRef.child(String.valueOf(invoiceNo + 1)).setValue(object);
//
//            //printPDF();
//            Toast.makeText(this, "Starting PDF Creator", Toast.LENGTH_SHORT).show();
//            createPdf();
//            //
//
//        });
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

    private void createPdf() {
        String mFileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(System.currentTimeMillis());
        // alternative "yyyy_MM_dd_HH_mm_ss
        String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfpath, mFileName + ".pdf");
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PdfWriter writer = new PdfWriter(file);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDocument);

            Paragraph paragraph = new Paragraph("Hello f");

            document.add(paragraph);
            document.close();
            Toast.makeText(this, "created", Toast.LENGTH_SHORT).show();


            //itext 7 start
            //paragraph
            Paragraph paragraph1 = new Paragraph("Hello f");
            document.add(paragraph1);
            //adding image
            String imgSrc = "filelocation";
            ImageData data = ImageDataFactory.create(imgSrc);
            Image image = new Image(data);
            document.add(image);
            //list
            List list1 = new List();
            list1.add("Java");
            //itext7 end




        } catch (FileNotFoundException | MalformedURLException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
     }
}

//
//    private boolean checkPermission() {
//        //ContextCompat.checkSelfPermission(WRITE_EXTERNAL_STORAGE);
//        // checking of permissions.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            return Environment.isExternalStorageManager();
//        }
//        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
//        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission() {
//        // requesting permissions if not provided.
//        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        //ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
//                startActivityForResult(intent, 2296);
//            } catch (Exception e) {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent, 2296);
//            }
//        } else {
//            //below android 11
//            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
//    }
//
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
//                    Toast.makeText(this, "Permission Denined :(", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//        }
//    }
//
//}


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