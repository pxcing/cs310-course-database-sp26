package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]"; // Default return is an empty JSON array
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Prepare the statement using the constant QUERY_FIND
                ps = conn.prepareStatement(QUERY_FIND);
                
                // Bind the variables to the ? placeholders in the query
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);

                boolean hasresults = ps.execute();
                
                if (hasresults) {
                    // Get the results and convert them to JSON using our utility
                    rs = ps.getResultSet();
                    result = DAOUtility.getResultSetAsJson(rs);
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            // Clean up resources to prevent database connection leaks
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}