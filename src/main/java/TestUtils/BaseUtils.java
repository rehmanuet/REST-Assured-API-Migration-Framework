package TestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class BaseUtils {
    private static Properties prop = new Properties();
    private static Workbook book;
    public static Object[][] getMyAccTestData(String sheetName) {
        FileInputStream file = null;
        try {
            String TESTDATA_SHEET_PATH = "C:\\restassured_framework\\src\\test\\resources\\APIData.xlsx";
            file = new FileInputStream(TESTDATA_SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert file != null;
            book = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = book.getSheet(sheetName);
        Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                data[i][k] = sheet.getRow(i + 1).getCell(k).toString();

            }
        }
        return data;
    }
    public static Map<String, String> Header() {
        Map<String, String> Headers = new HashMap<>();
        Headers.put("Authorization", "Bearer d453c0d4-7011-48c4-9784-ad6b28d8c60e");
        Headers.put("Content-Type", "application/json;charset=UTF-8");
        Headers.put("x-enterprise-id", "E000109");
        Headers.put("x-store-id", "S000000109");
        Headers.put("REMOTE_USER", "sys-user-driveflex-admin");

        return Headers;
    }
    public static String getProp(String varName) throws IOException {
        InputStream input;

        input = ReadPropertyFile.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(input);
        return prop.getProperty(varName);

    }

}
