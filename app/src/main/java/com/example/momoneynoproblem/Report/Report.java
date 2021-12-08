package com.example.momoneynoproblem.Report;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.momoneynoproblem.PDF.PDFViewer;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
import com.example.momoneynoproblem.Transaction.Transaction1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        if (checkPermission() == false){
            requestPermission();
        }
        ListView listView;
        listView = (ListView) findViewById(R.id.listView);
        final int[] Selection = new int[1];
        final String[] months = {"JANUARY", "FEBRUARY" , "MARCH", "APRIL", "MAY", "JUNE" ,"JULY" ,"AUGUST" ,"SEPTEMBER" ,"OCTOBER" ,"NOVEMBER" , "DECEMBER"};
        Button btnLoadReport = (Button) findViewById(R.id.PDFMaker_Button);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Transactions"); //create a reference to generated reports on firebase

        ArrayList<String> Report = new ArrayList<>();
        ArrayList<Transaction1> userData = new ArrayList<>();
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(Report.this, android.R.layout.simple_list_item_1, Report);
        listView.setAdapter(ArrayAdapter);
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Report.clear();
                for (DataSnapshot temp : snapshot.getChildren()) {
                    if (temp.child("accountId").getValue(String.class).compareTo(Singleton.getInstance().getUserID()) == 0  ) {
                        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd-yy");
                        LocalDate month = LocalDate.parse(temp.child("date").getValue(String.class), f);

                        Transaction1 tempData = new Transaction1(temp.child("amount").getValue(String.class),temp.child("transaction_type").getValue(String.class) , temp.child("transaction_source_type").getValue(String.class) ,
                        temp.child("transID").getValue(String.class), temp.child("date").getValue(String.class), temp.child("storeName").getValue(String.class) , temp.child("accountId").getValue(String.class));
                        userData.add(tempData);

                    }
                }
                Report.add("Report for: " + "November");
                ArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //onclick item listener to select which report to open in PDFViewer
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Selection[0] = position;
                String text = "position: " + Selection[0];
                Toast.makeText(Report.this, text, Toast.LENGTH_LONG).show();
            }
        });

        btnLoadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Report.this, PDFViewer.class);
//                intent.putExtra("PDFtoOpen",listView.getItemIdAtPosition(Selection[0]));
//                startActivity(intent);
                createPdf(userData, months[10], "Sam Mata", "angelmata31@gmail.com");
                //Log.i("Test", String.valueOf(Selection[0]) );

            }
        });


//        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Transactions");
//        ArrayList<Transaction1> userData = new ArrayList<>();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userData.clear();
//                /**
//                 * This portion of the code stores the users Transcations that belongs ONLY to the current user
//                 * Need to implement: additional constraint in the if statement that only stores user's transcations
//                 * between dateX1  and dateX2
//                 * Example: PDF is only for dates between 12/01/21 and 12/31/21
//                 *  then if statement has (psuedo code): "&& dateX1 <= (function to get date from firebase) <= dateX2
//                 */
//                for (DataSnapshot temp : snapshot.getChildren()){
//                    if (temp.child("accountId").getValue(String.class).compareTo(Singleton.getInstance().getUserID()) == 0) {
//                        Transaction1 tempData = new Transaction1(temp.child("amount").getValue(String.class),temp.child("transaction_type").getValue(String.class) , temp.child("transaction_source_type").getValue(String.class) ,
//                                temp.child("transID").getValue(String.class), temp.child("date").getValue(String.class), temp.child("storeName").getValue(String.class) , temp.child("accountId").getValue(String.class));
//                        userData.add(tempData);
//                        //  Log.i("PDFMaker",temp.getValue().toString());
//                    }
//                }
//                Log.i("Test", String.valueOf(Selection[0]) );
//                createPdf(userData);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
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

    private void createPdf(ArrayList<Transaction1> data, String month, String name, String email) {
        String mFileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(System.currentTimeMillis());
        // alternative "yyyy_MM_dd_HH_mm_ss
        String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfpath, mFileName + ".pdf");
        OutputStream outputStream = null;
        Log.i("PDFMaker", String.valueOf(data.size()));
//        String name = Singleton.getInstance().getName();
//        String email = Singleton.getInstance().getEmail();
        Log.i("PDFMAker", "name: " +  name);
        //Log.i("PDFMAker, email: ", email    );
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            PdfWriter writer = new PdfWriter(file);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDocument);

            //Top of Page
            //Add LOGO image
            //File f = FileUtils`.fileFromAsset(this.getAssets() , "mologo.png");
//            String imgSrc = Context.getAssets().open("mologo.png");
//            ImageData imageData = ImageDataFactory.create(imgSrc);
//            Image image = new Image(imageData);
//            document.add(image);
            //image declaration:
            Drawable d1 = getDrawable(R.drawable.mologo);
            Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            byte[] bitmapData1 = stream1.toByteArray();

            ImageData imageData1 = ImageDataFactory.create(bitmapData1);
            Image image1 = new Image(imageData1);
            image1.setHeight(250f);
            image1.setWidth(250f);
            //end image declaration
            float columnWidth[] = {140,140,140,140};
            Table table1 = new Table(columnWidth);

            //table 1 ---- 01
            table1.addCell(new Cell(5,2).add(image1).setBorder(Border.NO_BORDER));
            //table1.addCell(new Cell().add(new Paragraph("")));
            table1.addCell(new Cell(1,2).add(new Paragraph("Account Report")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            //table 1 ---- 02
            //table1.addCell(new Cell().add(new Paragraph("")));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            //table 1 ---- 03
            //table1.addCell(new Cell().add(new Paragraph("")));
            table1.addCell(new Cell().add(new Paragraph("Report Month:")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell(1,2).add(new Paragraph(month)).setBorder(Border.NO_BORDER));
            //table 1 ---- 04
            //table1.addCell(new Cell().add(new Paragraph("")));
            //table1.addCell(new Cell().add(new Paragraph("")));
            table1.addCell(new Cell().add(new Paragraph("Account Holder: ")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(name).setBorder(Border.NO_BORDER)));
            //table 1 ---- 05
            //table1.addCell(new Cell().add(new Paragraph("")));
            //table1.addCell(new Cell().add(new Paragraph("")));
            table1.addCell(new Cell().add(new Paragraph("Email:")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(email).setBorder(Border.NO_BORDER)));

            float columnWidth2[] = {112,112,112,112,112};
            Table table2 = new Table(columnWidth2);
            //color for background
            DeviceRgb greenish = new DeviceRgb(71,190,160);
            DeviceRgb gold = new DeviceRgb(255,221,26);
            //table 2 ---- 01
            table2.addCell(new Cell().add(new Paragraph("Date: ")).setBackgroundColor(gold));
            table2.addCell(new Cell().add(new Paragraph("Vendor: ")).setBackgroundColor(gold));
            table2.addCell(new Cell().add(new Paragraph("Category: ")).setBackgroundColor(gold));
            table2.addCell(new Cell().add(new Paragraph("Type: ")).setBackgroundColor(gold));
            table2.addCell(new Cell().add(new Paragraph("Amount: ")).setBackgroundColor(gold));

            for (int i = 0; i< data.size(); i++){
                //document.add(new Paragraph(data.get(i).toString()));
                if (i%2 == 0){
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).date)).setBackgroundColor(greenish));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).storeName)).setBackgroundColor(greenish));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).transaction_source_type)).setBackgroundColor(greenish));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).transaction_type)).setBackgroundColor(greenish));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).amount)).setBackgroundColor(greenish));
                }
                else{
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).date)));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).storeName)));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).transaction_source_type)));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).transaction_type)));
                    table2.addCell(new Cell().add(new Paragraph(data.get(i).amount)));
                }
            }
            //table1.setBorder(Border.NO_BORDER);
            //table2.setBorder(Border.NO_BORDER);
            document.add(table1);
            document.add(table2);
            document.add(new Paragraph("End of File").setTextAlignment(TextAlignment.CENTER));
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
                Intent i = new Intent(Report.this, PDFViewer.class);
                i.putExtra("fileName", pdfpath+ "/" + mFileName + ".pdf");
                Log.i("Report", pdfpath+ "/" +mFileName + ".pdf");
                startActivity(i);
    }
}

