package com.github.grishberg.barcodescanner.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.barcode.BarCodeScannerView;
import com.github.grishberg.barcodescanner.di.DiManager;
import com.github.grishberg.barcodescanner.form.builder.FormBuilderFragmentView;
import com.github.grishberg.barcodescanner.form.FoundResultFragment;

public class MainScreenView extends AppCompatActivity implements MainServiceStateChangeListener {
    private static final String TAG = MainScreenView.class.getSimpleName();
    private static final int FILE_CHOOSER_REQUEST_CODE = 123;
    private static final int NUM_PAGES = 3;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int RESULT_SCREEN = 1;
    public static final int BARCODE_SCREEN = 0;
    public static final int ADD_RELATIONS_SCREEN = 2;

    private MainController mainController;
    private MainScreenService service;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = DiManager.getAppComponent().provideMainScreenService();
        mainController = new MainControllerImpl(this, service, DiManager.getAppComponent()
                .provideLogger());

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        service.registerListener(this);

        checkPermission();
        mainController.onCreated();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        service.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainScreenView.class));
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
        if (requestCode == FILE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK
                && data.getData() != null) {
            mainController.openDocument(data.getData().getPath());
        }
    }

    @Override
    public void onShowBarCodeScanner() {
        Log.d(TAG, "onShowBarCodeScanner: ");
        viewPager.setCurrentItem(BARCODE_SCREEN);
    }

    @Override
    public void onShowMainScreen() {
        Log.d(TAG, "onShowMainScreen: ");
        viewPager.setCurrentItem(RESULT_SCREEN);
    }

    @Override
    public void onShowAddRelationsScreen() {
        Log.d(TAG, "onShowAddRelationsScreen: ");
        viewPager.setCurrentItem(ADD_RELATIONS_SCREEN);
    }

    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        CAMERA_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case BARCODE_SCREEN:
                    return new BarCodeScannerView();
                case RESULT_SCREEN:
                    return new FoundResultFragment();
            }
            return new FormBuilderFragmentView();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

