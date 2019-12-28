package barlot.travelcollector.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import barlot.travelcollector.DatabaseHelper;
import barlot.travelcollector.R;
import barlot.travelcollector.Utils.PathUtil;
import barlot.travelcollector.models.TravelData;
import barlot.travelcollector.views.Home;
import barlot.travelcollector.views.ListViewer;

public class LoadData extends AppCompatActivity {

    private static final String TAG = "LoadData";

    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;
    Intent fileIntent;
    SimpleDateFormat dateFormmater = new SimpleDateFormat("dd-MM-yyyy");

    int count = 0;

    ArrayList<TravelData> travelDataList;

    DatabaseHelper myDb;

    public static int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        myDb = new DatabaseHelper(this);

        travelDataList = new ArrayList<>();

        //TODO: need to check the permissions - this function stopped my app
//        checkFilePermissions();


        fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        fileIntent.setType("*/*");
        String[] mimetypes = {"application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
        fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(fileIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {

                try {
                    String filePath = PathUtil.getPath(this, data.getData());
                    toastMessage("Load file from: "+filePath);
                    readExcelData(filePath);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * reads the excel file columns then rows. Stores data as ExcelUploadData object
     *
     * @return
     */
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");


        try {
            File file = new File(filePath);
            OPCPackage opcPackage = OPCPackage.open(file);
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);


            // assume the data is in first sheet always
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outer loop, loops through rows
            for (int r = 1; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();

                //inner loop, loops through exactly 11 columns
                for (int c = 0; c < 11; c++) {
                    String value = getCellAsString(row, c, formulaEvaluator);
                    String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                    Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                    //sb.append(value + ", ");
                    sb.append(value + "##");
                }
                // for parsing - TODO: consider to change it to less common char
                //sb.append(":");
                sb.append("::");
            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

            parseStringBuilder(sb);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage());
        } catch (InvalidFormatException e) {
            Log.e(TAG, "readExcelData: Error converting to excel. " + e.getMessage());
        }
    }


    /**
     * Returns the cell as a string from the excel file
     *
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        value = dateFormmater.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = "" + numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = "" + cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage());
        }
        return value;
    }

    /**
     * Method for parsing imported data and storing in ArrayList<TravelData>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder) {
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split("::");

        //Add to the ArrayList<TravelData> row by row
        for (int i = 0; i < rows.length; i++) {
            //Split the columns of the rows TODO: make sure there is not comma in the original sheet
            String[] columns = rows[i].split("##");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try {
                int albumId = (int) Double.parseDouble(columns[0]);
                Date date = dateFormmater.parse(columns[1]);
                String groupName = columns[2];
                String guideName = columns[3];
                String description = columns[4];
                double distanceInKm = Double.parseDouble(columns[5]);
                String tags = columns[6];
                String alternative = columns[7];
                String country = columns[8];
                String comments = columns[9];
                String link = columns[10];

                //add the the travelDataList ArrayList
                travelDataList.add(new TravelData(albumId, date, groupName, guideName, description,
                        distanceInKm, tags, alternative, country, comments, link));

            } catch (NumberFormatException e) {

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            } catch (ParseException e) {
                Log.e(TAG, "parseStringBuilder: ParseException: " + e.getMessage());
            }
        }

        // delete current records and add data imported from excel
        myDb.deleteAll();
        myDb.insertData(travelDataList);

        // open travel list viewer
        openListViewerActivity();
    }

    private void openListViewerActivity() {
        Intent intent = new Intent(LoadData.this, ListViewer.class);
        intent.putParcelableArrayListExtra("data", travelDataList);
        startActivity(intent);
        finish();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
