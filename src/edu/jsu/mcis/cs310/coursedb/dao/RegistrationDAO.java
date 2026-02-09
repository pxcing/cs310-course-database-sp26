package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        PreparedStatement ps = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                // SQL for inserting a new registration record
                String sql = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // If at least one row was affected, registration was successful
                int count = ps.executeUpdate();
                if (count > 0) {
                    result = true;
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        
        return result;
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        PreparedStatement ps = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                // SQL for dropping a single specific course
                String sql = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int count = ps.executeUpdate();
                if (count > 0) {
                    result = true;
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        
        return result;
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        PreparedStatement ps = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                // SQL for withdrawing from ALL courses for a specific term
                String sql = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                int count = ps.executeUpdate();
                if (count > 0) {
                    result = true;
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        
        return result;
    }

    public String list(int studentid, int termid) {
        
        String result = "[]"; // Default to empty JSON array
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                // SQL to list all registrations for a student, ordered by CRN
                String sql = "SELECT * FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                boolean hasresults = ps.execute();
                if (hasresults) {
                    rs = ps.getResultSet();
                    // Use the utility helper you wrote earlier to convert result set to JSON
                    result = DAOUtility.getResultSetAsJson(rs);
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        
        return result;
    }
}