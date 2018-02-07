package com.github.grishberg.barcodescanner.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.common.ValueObserver;
import com.github.grishberg.barcodescanner.di.DiManager;
import com.github.grishberg.barcodescanner.main.MainController;

import java.util.List;

/**
 * Created by grishberg on 06.02.18.
 */

public class FoundResultFragment extends Fragment {
    private static final String TAG = FoundResultFragment.class.getSimpleName();
    private MainController mainController;
    private TextView barCodeText;
    private TextView documentResult;

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
            StringBuilder sb = new StringBuilder();
            for (CellRelation res : result) {
                sb.append(res.toString());
                sb.append("; ");
            }
            documentResult.setText(sb.toString());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form_result, container, false);
        barCodeText = root.findViewById(R.id.form_result_barcode);
        documentResult = root.findViewById(R.id.form_result_found_value);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainController = DiManager.getAppComponent().provideMainController();
        mainController.registerBarcodeObserver(barCodeObserver);
        mainController.registerResultCellsObserver(resultObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainController.unregisterBarcodeObserver(barCodeObserver);
        mainController.unregisterResultCellsObserver(resultObserver);
    }
}
