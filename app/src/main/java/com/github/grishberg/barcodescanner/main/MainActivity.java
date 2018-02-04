package com.github.grishberg.barcodescanner.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.barcode.BarCodeScanActivity;
import com.github.grishberg.barcodescanner.common.ValueObserver;
import com.github.grishberg.barcodescanner.di.DiManager;

public class MainActivity extends AppCompatActivity implements MainServiceStateChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int FILE_CHOOSER_REQUEST_CODE = 123;
    private MainController mainController;
    private TextView barCodeText;
    private TextView foundCellAddressText;

    private final ValueObserver<String> barCodeObserver = new ValueObserver<String>() {
        @Override
        public void onValueChanged(String value) {
            Log.d(TAG, "onChangedBarCode: " + value);
            barCodeText.setText(value);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainController = DiManager.getAppComponent().provideMainController();

        Button openCameraButton = findViewById(R.id.open_camera_button);

        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.onScannerButtonClicked();
            }
        });

        Button openXlsFileButton = findViewById(R.id.open_xls_file);
        openXlsFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.onOpenFileButtonClicked();
            }
        });
        barCodeText = findViewById(R.id.barcode_text);
        foundCellAddressText = findViewById(R.id.found_cell_address_text);

        mainController.registerListener(this);
        mainController.registerBarcodeObserver(barCodeObserver);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        mainController.unregisterListener(this);
        mainController.unregisterBarcodeObserver(barCodeObserver);
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    public void onShowBarCodeScanner() {
        Log.d(TAG, "onShowBarCodeScanner: ");
        BarCodeScanActivity.start(this);
        finish();
    }

    @Override
    public void onOpenFileChooser() {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, getString(R.string.file_chooser_title)),
                FILE_CHOOSER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedFile = data.getData(); //The uri with the location of the file
            Log.d(TAG, "onActivityResult: " + selectedFile.getPath());
        }
    }

    @Override
    public void onFoundDocumentCellByAddress(String cellAddress) {
        foundCellAddressText.setText(cellAddress);
    }
}
