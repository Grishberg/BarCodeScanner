package com.github.grishberg.barcodescanner.form.recycler;

import com.github.grishberg.barcodescanner.form.FormResultItemController;
import com.github.grishberg.barcodescanner.sheets.SheetsService;

/**
 * Created by grishberg on 11.02.18.
 */

public class FormResultItemControllerImpl implements FormResultItemController {
    private final SheetsService service;

    public FormResultItemControllerImpl(SheetsService service) {
        this.service = service;
    }

    @Override
    public void onStringItemChanged(int x, int y, String newValue) {
        service.changeItemValue(x, y, newValue);
    }

    @Override
    public void onNumberItemChanged(int x, int y, int newValue) {

    }

    @Override
    public void onNumberItemChanged(int x, int y, float newValue) {

    }

    @Override
    public void onNumberItemInc(int x, int y) {

    }

    @Override
    public void onNumberItemDec(int x, int y) {

    }
}
