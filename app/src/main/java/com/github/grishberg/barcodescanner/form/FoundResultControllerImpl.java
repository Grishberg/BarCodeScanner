package com.github.grishberg.barcodescanner.form;

import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.main.MainScreenService;

/**
 * Created by grishberg on 08.02.18.
 */

public class FoundResultControllerImpl implements FoundResultController {
    private FoundResultFragment view;
    private MainScreenService service;
    private Logger logger;

    public FoundResultControllerImpl(FoundResultFragment view,
                                     MainScreenService service,
                                     Logger logger) {

        this.view = view;
        this.service = service;
        this.logger = logger;
    }
}
