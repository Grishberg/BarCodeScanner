package com.github.grishberg.barcodescanner.form.builder;

/**
 * Created by grishberg on 07.02.18.
 */

public interface FormBuilderController {
    void onFormCreated();

    void onAddRelationButtonClicked();

    void registerFormBuilderListener(FormBuilderModelListener listener);

    void unregisterFormBuilderListener(FormBuilderModelListener listener);
}
