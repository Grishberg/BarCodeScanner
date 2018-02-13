package com.github.grishberg.barcodescanner.common;

import android.support.annotation.Nullable;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by grishberg on 12.02.18.
 */

public class FileUtils {
    public static void safeClose(@Nullable InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // not handled
            }
        }
    }

    public static void safeClose(@Nullable XSSFWorkbook stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // not handled
            }
        }
    }
}
