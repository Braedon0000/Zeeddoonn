package za.ac.cput.marathoneventregistration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import za.ac.cput.marathoneventregistration.Connection.DBConnection;
import za.ac.cput.marathoneventregistration.domain.Race;

/**
 *
 * @author Leonard
 */
public class RaceDao {

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public RaceDao() {
        try {
            this.con = DBConnection.derbyConnection();
        } catch (Exception exe) {
            JOptionPane.showMessageDialog(null, exe);
        }
    }
//    public ArrayList<Race> getAll(Race race) {
//
//        ArrayList<Race> krace = new ArrayList<>();
//        String query = "SELECT racecode FROM racetable";
//
//        try {
//
//            pstmt = this.con.prepareStatement(query);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs != null) {
//                while (rs.next()) {
//                    // String value = rs.getString("")
//                    krace.add(new Race(rs.getString("racecode")));
//                }
//                rs.close();
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.toString());
//        } finally {
//            try {
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, ex.toString());
//            }
//        }
//        return krace;
//    }
    public void save(Race race)
    {   
        int count;
        String query = "SELECT COUNT(*) FROM racetable WHERE racecode = ?";
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setString(1,race.getRaceCode());
            rs = pstmt.executeQuery();
            
            rs.next();
            
            count = rs.getInt(1);
  
            
            if(count > 0)
            {
                JOptionPane.showMessageDialog(null,"Duplicate race code, already exists in the table!" );
                
            }
            else
            {
                String insertRecordSql = "INSERT INTO racetable  VALUES (?,?,?,?,?)";
                pstmt = this.con.prepareStatement(insertRecordSql);
                pstmt.setString(1, race.getRaceCode());
                pstmt.setString(2, race.getFirstName());
                pstmt.setString(3, race.getLastName());
                pstmt.setString(4, race.getRaceType());
                pstmt.setBoolean(5, race.isClub());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record saved successfully!");
                
                
               
 
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
//                closeCloseconnection();
            }
             catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e);
            } 
        }
        
    }
    
    public void closePreparedStatement() throws SQLException
    {
        if (pstmt != null) {
            pstmt.close();
        }

    }
    
    public void closeResultSet() throws SQLException
    {
        rs.close();

    }
    
    public void closeCloseconnection() throws SQLException
    {
        if (con != null) {
            con.close();
        }
    }
    
    
    
    

}
