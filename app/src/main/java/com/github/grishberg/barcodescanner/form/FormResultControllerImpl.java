package com.github.grishberg.barcodescanner.form;

import com.github.grishberg.barcodescanner.main.MainScreenService;

/**
 * Created by grishberg on 13.02.18.
 */

public class FormResultControllerImpl implements FormResultController {
    private final MainScreenService service;

    public FormResultControllerImpl(MainScreenService service) {
        this.service = service;
    }

    @Override
    public void onOpenButtonClicked() {
        service.showOpenFileDialog();
    }
}
