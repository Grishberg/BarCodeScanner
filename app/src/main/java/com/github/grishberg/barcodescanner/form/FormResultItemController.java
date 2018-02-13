package com.github.grishberg.barcodescanner.form;

/**
 * Created by grishberg on 10.02.18.
 */

public interface FormResultItemController {
    void onStringItemChanged(int x, int y, String newValue);

    void onNumberItemChanged(int x, int y, int newValue);

    void onNumberItemChanged(int x, int y, float newValue);

    void onNumberItemInc(int x, int y);

    void onNumberItemDec(int x, int y);
}
