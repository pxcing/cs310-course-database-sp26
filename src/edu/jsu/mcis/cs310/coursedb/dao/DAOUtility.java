package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class DAOUtility {
    
    public static final int TERMID_SP26 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {

                // Get Metadata to find out how many columns exist and their names
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // Loop through every row in the result set
                while (rs.next()) {
                    
                    // Create a new JSON object for this row
                    JsonObject row = new JsonObject();
                    
                    // Loop through every column in the current row
                    for (int i = 1; i <= columnCount; i++) {
                        
                        // Get the column name (the key)
                        String columnName = rsmd.getColumnName(i);
                        
                        // Get the value (the data)
                        Object columnValue = rs.getObject(i);
                        
                        // Add the data to the row object. 
                        row.put(columnName, columnValue.toString());
                        
                    }
                    
                    records.add(row);
                }

            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // Serialize the array of objects into a single JSON string
        return Jsoner.serialize(records);
        
    }
    
}