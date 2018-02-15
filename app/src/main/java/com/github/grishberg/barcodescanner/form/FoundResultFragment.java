package com.github.grishberg.barcodescanner.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.common.ValueObserver;
import com.github.grishberg.barcodescanner.di.DiManager;
import com.github.grishberg.barcodescanner.form.recycler.FormResultAdapter;
import com.github.grishberg.barcodescanner.form.recycler.FormResultItemControllerImpl;
import com.github.grishberg.barcodescanner.form.recycler.ViewHolderFactory;
import com.github.grishberg.barcodescanner.form.recycler.ViewHolderFactoryImpl;
import com.github.grishberg.barcodescanner.main.MainScreenService;
import com.github.grishberg.barcodescanner.sheets.SheetsService;

import java.util.List;

/**
 * Created by grishberg on 06.02.18.
 */

public class FoundResultFragment extends Fragment {
    private static final String TAG = FoundResultFragment.class.getSimpleName();
    private MainScreenService service;
    private TextView barCodeText;
    private FormResultAdapter formResultAdapter;
    private FormResultController formResultController;

    private Logger logger;

    private final ValueObserver<String> barCodeObserver = new ValueObserver<String>() {
        @Override
        public void onValueChanged(String value) {
            Log.d(TAG, "onChangedBarCode: " + value);
            barCodeText.setText(value);
        }
    };

    private final ValueObserver<List<CellRelation>> resultObserver = new ValueObserver<List<CellRelation>>() {
        @Override
        public void onValueChanged(List<CellRelation> result) {
            Log.d(TAG, "onValueChanged: ");
            formResultAdapter.setRelationsWithResult(result);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form_result, container, false);
        barCodeText = root.findViewById(R.id.form_result_barcode);
        RecyclerView resultRecyclerView = root.findViewById(R.id.form_result_recycler_view);
        resultRecyclerView.setAdapter(formResultAdapter);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Button openDocButton = root.findViewById(R.id.form_result_open_document_btn);
        openDocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formResultController.onOpenButtonClicked();
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = DiManager.getAppComponent().provideLogger();
        SheetsService sheetsService = DiManager.getAppComponent().provideSheetsService();
        this.service = DiManager.getAppComponent().provideMainScreenService();
        this.service.registerBarcodeObserver(barCodeObserver);
        this.service.registerResultCellsObserver(resultObserver);
        FormResultItemController itemController = new FormResultItemControllerImpl(sheetsService);
        ViewHolderFactory viewHolderFactory = new ViewHolderFactoryImpl(itemController);
        formResultAdapter = new FormResultAdapter(viewHolderFactory);
        formResultController = new FormResultControllerImpl(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        service.unregisterBarcodeObserver(barCodeObserver);
        service.unregisterResultCellsObserver(resultObserver);
    }
}
