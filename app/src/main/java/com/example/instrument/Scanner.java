package com.example.instrument;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

public class Scanner extends AppCompatActivity {
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private TextView barcodeText;
    private String barcodeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        surfaceView = findViewById(R.id.surface_view);
        init();
    }

    private void init() {

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.CODE_128)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Scanner.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(Scanner.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

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


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            // we have a value...
                            barcodeData = barcodes.valueAt(0).displayValue;

                            // get the value back to mainactivity
                            try
                            {
                                MainActivity.activeID = Integer.parseInt(barcodeData);
                                ArrayList<Instrument> instruments = ListContainer.getInstruments();
                                for(int i=0; i<instruments.size();i++){
                                    if(instruments.get(i).getId() == MainActivity.activeID){
                                        Intent singleItem = new Intent(getApplicationContext(), InstrumentInfo.class);
                                        singleItem.putExtra("itemIndex", i);
                                        startActivity(singleItem);
                                        finish();
                                    }
                                }
                                // if i get here, the id is real number but not in my list...
                                Intent singleItem = new Intent(getApplicationContext(), InstrumentInfo.class);
                                singleItem.putExtra("itemIndex", -99);
                                startActivity(singleItem);
                                finish();
                            }
                            catch(NumberFormatException e)
                            {
                                Toast.makeText(getApplicationContext(), "Barcode Format Incorrect", Toast.LENGTH_SHORT).show();
                                return;
                            }


                        }
                    });

                }
            }
        });
    }


}