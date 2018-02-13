package com.github.grishberg.barcodescanner.sheets;

import android.content.Context;
import android.os.AsyncTask;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.common.AbsServiceObservable;
import com.github.grishberg.barcodescanner.common.FileUtils;
import com.github.grishberg.barcodescanner.common.Logger;
import com.github.grishberg.barcodescanner.form.CellRelation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.inject.Inject;

/**
 * Operations with sheets.
 */
public class SheetsService extends AbsServiceObservable<SheetsServiceListener> {
    private static final String TAG = SheetsService.class.getSimpleName();
    private final Logger logger;
    private final Context appContext;
    private File docFile;

    @Inject
    public SheetsService(Logger logger, Context appContext) {
        this.logger = logger;
        this.appContext = appContext;
    }

    public void setDocFileName(String fileName) {
        docFile = new File(fileName);
    }

    public void findSheetsCell(final SheetsColumnRequest request) {
        new AsyncTask<Void, Void, ArrayList<CellRelation>>() {
            @Override
            protected ArrayList<CellRelation> doInBackground(Void... voids) {
                return findResults(request);
            }

            @Override
            protected void onPostExecute(ArrayList<CellRelation> result) {
                notifyResultFound(result);
            }
        }.execute();
    }

    private ArrayList<CellRelation> findResults(SheetsColumnRequest request) {
        ArrayList<CellRelation> results = new ArrayList<>();

        WorkbookHolder workbookHolder = new WorkbookHolder();
        try {
            workbookHolder.stream = new FileInputStream(docFile);
            workbookHolder.workbook = new XSSFWorkbook(workbookHolder.stream);
            XSSFSheet sheet = workbookHolder.workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbookHolder.workbook.getCreationHelper()
                    .createFormulaEvaluator();
            logger.d(TAG, "findResults: rowsCount = " + rowsCount);

            int[] searchRows = new int[request.getCellRepresentations().size()];
            for (int i = 0; i < request.getCellRepresentations().size(); i++) {
                CellRelation relation = request.getCellRepresentations().get(i);
                searchRows[i] = relation.getX();
            }
            Row firstRow = sheet.getRow(searchRows[0]);
            int cellsCount = firstRow.getPhysicalNumberOfCells();
            for (int y = 0; y < cellsCount; y++) {
                boolean found = false;
                Row row = sheet.getRow(y);
                for (int i = 0; i < searchRows.length; i++) {
                    int x = searchRows[i];
                    String value = getCellAsString(row, x, formulaEvaluator);
                    String cellInfo = "x:" + x + "; y:" + y + "; v:" + value;

                    logger.d(TAG, cellInfo);
                    if (request.getSearchString().equals(value)) {
                        found = true;
                        logger.d(TAG, "------- found cell in : " + cellInfo);
                        break;
                    }
                }
                if (found) {
                    for (CellRelation representation : request.getCellRepresentations()) {
                        int rowIndexFromRelation = representation.getX();

                        CellRelation resultRelation = new CellRelation(representation);
                        resultRelation.setValue(getCellAsString(row, rowIndexFromRelation, formulaEvaluator));
                        results.add(resultRelation);
                    }
                }
            }
        } catch (Exception e) {
            /* proper exception handling to be here */
            logger.e(TAG, e.toString());
        } finally {
            workbookHolder.close();
        }
        return results;
    }

    private void notifyResultFound(ArrayList<CellRelation> result) {
        for (SheetsServiceListener listener : listeners) {
            listener.onFoundSheetsCell(result);
        }
    }

    public void changeItemValue(int x, int y, String newValue) {
        logger.d(TAG, "changeItemValue() called with: x = [" + x + "], y = [" + y +
                "], newValue = [" + newValue + "]");
        writeXls(x, y, newValue);
    }

    private void writeXls(int x, int y, String value) {
        // read existing book.
        WorkbookHolder workbookHolder = new WorkbookHolder();
        try {
            workbookHolder.stream = new FileInputStream(docFile);
            workbookHolder.workbook = new XSSFWorkbook(workbookHolder.stream);

            XSSFSheet sheet = workbookHolder.workbook.getSheetAt(0);
            Row row = sheet.getRow(y);
            Cell cell = row.createCell(x);
            cell.setCellValue(value);

            // write to sheet
            OutputStream outputStream = new FileOutputStream(docFile);
            workbookHolder.workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            /* proper exception handling to be here */
            logger.e(TAG, e.toString());
        } finally {
            workbookHolder.close();
        }

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

    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
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

    private static class WorkbookHolder {
        InputStream stream = null;
        XSSFWorkbook workbook = null;

        void close() {
            FileUtils.safeClose(workbook);
            FileUtils.safeClose(stream);
        }
    }
}
