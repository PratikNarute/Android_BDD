package utility;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProperty {


    public static Properties properties;
    public static BufferedReader reader;
    static String propertyFilePath;


    public static String getPropertiesData(String key) throws IOException {
        String testData = null;
        propertyFilePath = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+ "Test_Data.properties";
        try{
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            properties.load(reader);
            reader.close();
            testData = properties.getProperty(key);
        }catch(NullPointerException e){
            System.out.println("Found NullPointerException: "+e.getMessage());
        }
        return testData;
    }

    public static String readCountryDataFromExcel(String country, String sRequiredData) throws Exception {
        Connection connection = null;
        Recordset recordset = null;
        String strQuery = null;

        try {
            Fillo fillo = new Fillo();
            String userDirectory = System.getProperty("user.dir");
            connection = fillo.getConnection(userDirectory + "/src/test/resources/ExcelSheet/WebAutomationData.xlsx");
            strQuery = "SELECT * FROM All_Countries WHERE COUNTRY='" + country + "'";// All_Countries : SheetName
            // Wrap recordset initialization in try-catch block
            try {
                recordset = connection.executeQuery(strQuery);
            }
            catch (Exception e){
                System.out.println("Found NullPointerException due to keys value in excel is not present in excel sheet"+e.getMessage());
            }

            while (recordset != null && recordset.next()) {
                return (recordset.getField(sRequiredData));
            }

        }
        finally {
            if(!(connection ==null)){
                connection.close();
            }
            if(!(recordset ==null)){
                recordset.close();
            }

        }
        return "";
    }

}
