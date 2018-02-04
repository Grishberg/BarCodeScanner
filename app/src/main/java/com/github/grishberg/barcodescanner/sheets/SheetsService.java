package com.github.grishberg.barcodescanner.sheets;

import android.content.Context;
import android.os.AsyncTask;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.common.AbsServiceObservable;
import com.github.grishberg.barcodescanner.common.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.inject.Inject;

/**
 * Created by grishberg on 04.02.18.
 */

public class SheetsService extends AbsServiceObservable<SheetsServiceListener> {
    private static final String TAG = SheetsService.class.getSimpleName();
    private final Logger logger;
    private final Context appContext;

    @Inject
    public SheetsService(Logger logger, Context appContext) {
        this.logger = logger;
        this.appContext = appContext;
    }

    public void findSheetsCell(final String key) {
        new AsyncTask<Void, Void, ArrayList<FoundCellResult>>() {
            @Override
            protected ArrayList<FoundCellResult> doInBackground(Void... voids) {
                return findResults(key);
            }

            @Override
            protected void onPostExecute(ArrayList<FoundCellResult> result) {
                notifyResultFound(result);
            }
        }.execute();
    }

    private ArrayList<FoundCellResult> findResults(String key) {
        ArrayList<FoundCellResult> results = new ArrayList<>();

        InputStream stream = appContext.getResources().openRawResource(R.raw.ean13_sample);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper()
                    .createFormulaEvaluator();
            logger.d(TAG, "findResults: rowsCount = " + rowsCount);
            for (int r = 0; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                for (int c = 0; c < cellsCount; c++) {
                    String value = getCellAsString(row, c, formulaEvaluator);
                    String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                    logger.d(TAG, cellInfo);
                    if (key.equals(value)) {
                        logger.d(TAG, "------- found cell in : " + cellInfo);
                        results.add(new FoundCellResult(r, c));
                    }
                }
            }
        } catch (Exception e) {
            /* proper exception handling to be here */
            logger.e(TAG, e.toString());
        }

        return results;
    }

    private void notifyResultFound(ArrayList<FoundCellResult> result) {
        for (SheetsServiceListener listener : listeners) {
            listener.onFoundSheetsCell(result);
        }
    }

    private void writeXls() {
        //XXX: Using blank template file as a workaround to make it work
        //Original library contained something like 80K methods and I chopped it to 60k methods
        //so, some classes are missing, and some things not working properly
        InputStream stream = appContext.getResources().openRawResource(R.raw.template);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            //XSSFWorkbook workbook = new XSSFWorkbook();
            //XSSFSheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("mysheet"));
            for (int i = 0; i < 10; i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(i);
            }
            String outFileName = "filetoshare.xlsx";
            logger.d(TAG, "writing file " + outFileName);
            File cacheDir = appContext.getCacheDir();
            File outFile = new File(cacheDir, outFileName);
            OutputStream outputStream = new FileOutputStream(outFile.getAbsolutePath());
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            logger.d(TAG, "sharing file...");
        } catch (Exception e) {
            /* proper exception handling to be here */
            logger.e(TAG, e.toString());
        }
    }

    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellTypeEnum()) {
                case BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;
                case NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("dd/MM/yy", Locale.US);
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = "" + numericValue;
                    }
                    break;
                case STRING:
                    value = "" + cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
            logger.e(TAG, e.toString());
        }
        return value;
    }
}
