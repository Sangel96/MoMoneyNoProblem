package com.example.momoneynoproblem.scanner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.momoneynoproblem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.Arrays;

public class scanner extends AppCompatActivity {
    private static final int SELECT_IMAGE_FROM_STORAGE =100;
    private TextView textView;
    private Button buttonTextBack;
    private SurfaceView surfaceView;

    private CameraSource cameraSource;
    private TextRecognizer textRecognizer;

    private TextToSpeech textToSpeech;
    private String stringResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        Button btnupload = (Button) findViewById(R.id.uploadfile);
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        // this code will open the file manager and import the pdf to be used to scan
        btnupload.setOnClickListener(v -> mGetContent.launch("image/*"));

    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            result -> {
                if(result != null){
                    textRecognizer(result);
                }
            });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraSource != null) {
            // text recognizier gets null reference exception,
            cameraSource.release();
        }
    }

    private void textRecognizer() {
        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setRequestedPreviewSize(1280, 1024)
                .build();

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(scanner.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(scanner.this, new String[]{CAMERA}, PackageManager.PERMISSION_GRANTED);
                        return;
                    }
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {

                SparseArray<TextBlock> sparseArray = detections.getDetectedItems();
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i<sparseArray.size(); ++i){
                    TextBlock textBlock = sparseArray.valueAt(i);
                    if (textBlock != null && textBlock.getValue() !=null){
                        stringBuilder.append(textBlock.getValue() + " ");
                    }
                }

                final String stringText = stringBuilder.toString();

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // PARSING RECEIPT TO AMOUNT AND DATE
                        ///GALLERY
                        stringResult = stringText;
                        //stringResult = stringResult.substring(stringResult.lastIndexOf("TOTAL"));

                        String stringDate = stringText;
                        //this code below saves a substring starting at total
                        stringResult = stringResult.substring(stringResult.toLowerCase().lastIndexOf("TOTAL".toLowerCase()) ); // Total: 80.00
                        Log.i("Scanner: ", stringResult);
                        //Filter by decimal numbers XX.XxXX.XXX
                        //\\d
                        String amount = stringResult.replaceAll("[^\\d.\\d]", "");
                        Log.i("Scanner: ", amount);
                        //Filter extraneous decimals
                        String[] words = amount.split("\\.");
                        Log.i("Scanner3", Arrays.toString(words));


                        if (words[1].length() > 2) {
                            words[1] = words[1].substring(0,2);
                        }
                        if (words[1].length() == 1) { //adds zero if only 1 digit
                            words[1] = words[1] + "0";
                        }

                        amount = words[0] + "." + words[1];
                        Log.i("Scanner4: ", "$" + amount);
                        // MM/DD/YY
                        //First stage filtering ~~~~~MM/DD/YY
                        String date = "";
                        if(stringDate.contains("/")) {
                            date = stringDate.replaceAll("[^\\d{2}/\\d{2}/\\d{2}]", "");
                        }
                        else if (stringDate.contains("-")){
                            date = stringDate.replaceAll("[^\\d{2}\\-\\d{2}\\-\\d{2}]", "");
                        }
                        //Second stage filtering
                        String[] dateSplit = date.split("\\/|\\-");
                        if (dateSplit[0].length() == 1) {
                            dateSplit[0] = "0" + dateSplit[0];
                        }
                        if (dateSplit[0].length() > 2) {dateSplit[0] = dateSplit[0].substring(dateSplit[0].length() - 2);}
                        if (dateSplit[2].length() > 2) {dateSplit[2] = dateSplit[2].substring(0, 2);}

                        date = dateSplit[0] + "-" + dateSplit[1] + "-" + dateSplit[2];
                        Log.i("Scanner 5", date );
                        // Log.i("Scanner Class", "Debugging Purposes: " + m1.group(1));
                        resultObtained("Total: $" + amount + "\n\nDate: " + date + "\n\nOriginal: " + stringText);

                        Intent intent = new Intent();
                        intent.putExtra("amount",amount);
                        intent.putExtra("date",date);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        });
    }

    private void textRecognizer(Uri uri) {
        String temp = null;
        com.google.mlkit.vision.text.TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

            Task<Text> task = textRecognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(@NonNull Text text) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String resultText = text.getText();
                    for (Text.TextBlock block : text.getTextBlocks()) {
                        String blockText = block.getText();
                        Point[] blockCornerPoints = block.getCornerPoints();
                        Rect blockFrame = block.getBoundingBox();
                        for (Text.Line line : block.getLines()) {
                            String lineText = line.getText();
                            Point[] lineCornerPoints = line.getCornerPoints();
                            Rect lineFrame = line.getBoundingBox();
                            for (Text.Element element : line.getElements()) {
                                String elementText = element.getText();
                                Point[] elementCornerPoints = element.getCornerPoints();
                                Rect elementFrame = element.getBoundingBox();
                                Log.i("Scanner: ", elementText);
                                stringBuilder.append(elementText + " ");
                            }

                        }

                    }
                    final String stringText = stringBuilder.toString();
                    Log.i("Scannnnnnner", stringText);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // PARSING RECEIPT TO AMOUNT AND DATE
                            ///GALLERY
                            stringResult = stringText;
                            //stringResult = stringResult.substring(stringResult.lastIndexOf("TOTAL"));

                            String stringDate = stringText;
                            //this code below saves a substring starting at total
                            stringResult = stringResult.substring(stringResult.toLowerCase().lastIndexOf("TOTAL".toLowerCase()) ); // Total: 80.00
                            Log.i("Scanner: ", stringResult);
                            //Filter by decimal numbers XX.XxXX.XXX
                            //\\d
                            String amount = stringResult.replaceAll("[^\\d.\\d]", "");
                            Log.i("Scanner: ", amount);
                            //Filter extraneous decimals
                            String[] words = amount.split("\\.");
                            Log.i("Scanner3", Arrays.toString(words));


                            if (words[1].length() > 2) {
                                    words[1] = words[1].substring(0,2);
                            }
                            if (words[1].length() == 1) { //adds zero if only 1 digit
                                words[1] = words[1] + "0";
                            }

                            amount = words[0] + "." + words[1];
                            Log.i("Scanner4: ", "$" + amount);
                            // MM/DD/YY
                            //First stage filtering ~~~~~MM/DD/YY
                            String date = "";
                            if(stringDate.contains("/")) {
                                date = stringDate.replaceAll("[^\\d{2}/\\d{2}/\\d{2}]", "");
                            }
                            else if (stringDate.contains("-")){
                                date = stringDate.replaceAll("[^\\d{2}\\-\\d{2}\\-\\d{2}]", "");
                            }
                            //Second stage filtering
                            String[] dateSplit = date.split("\\/|\\-");
                            if (dateSplit[0].length() == 1) {
                                dateSplit[0] = "0" + dateSplit[0];
                            }
                            if (dateSplit[0].length() > 2) {dateSplit[0] = dateSplit[0].substring(dateSplit[0].length() - 2);}
                            if (dateSplit[2].length() > 2) {dateSplit[2] = dateSplit[2].substring(0, 2);}

                            date = dateSplit[0] + "-" + dateSplit[1] + "-" + dateSplit[2];
                            Log.i("Scanner 5", date );
                           // Log.i("Scanner Class", "Debugging Purposes: " + m1.group(1));
                            resultObtained("Total: $" + amount + "\n\nDate: " + date + "\n\nOriginal: " + stringText);

                            Intent intent = new Intent();
                            intent.putExtra("amount",amount);
                            intent.putExtra("date",date);
                            setResult(RESULT_OK,intent);
                            finish();

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resultObtained(String s){
        setContentView(R.layout.activity_scanner);
        //the code below displays the full converted text
        textView = findViewById(R.id.textView);
        textView.setText(s);
        // the code below is for return result
        //buttonTextBack(textView);
        //textToSpeech.speak(stringResult, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void buttonStart(View view){
        setContentView(R.layout.surfaceview);
        textRecognizer();
    }

// My code
    //send text back button click
    public void buttonTextBack(View view) {
        // get the text from the EditText
        setContentView(R.layout.activity_scanner);
        textView = findViewById(R.id.textView);
        String amount = textView.getText().toString();
        String date = textView.getText().toString();

        //put the String to pass back into an Intent and close this activity
        Intent intent = new Intent();
        intent.putExtra("amount",amount);
        intent.putExtra("date",date);
        setResult(RESULT_OK,intent);
        finish();
    }

}