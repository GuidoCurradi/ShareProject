package it.betacom.richiamiProcedure;
import it.betacom.dbHandler.*;
import java.util.ArrayList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import java.sql.ResultSetMetaData;


public class DBFunction {
	String nazione = "Italia";
	static CallableStatement cstmt;
	static PreparedStatement stmt = null;
    static ResultSet rs = null;
	
	
	

    // Funzione per ottenere l'età di un autore dato il suo nome e cognome
    public static int getAge() {
        DBHandler db = DBHandler.getInstance();
        int eta = -1;

        try {
            stmt = db.getConnection().prepareStatement("SELECT * FROM autori_eta_temp");
            rs = stmt.executeQuery();

            if (rs.next()) {
                eta = rs.getInt("eta");
            }
            
           
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eta;
    }
    
    public static List<String> getNazione(String nazione) {
		List<String> result = new ArrayList<>();
		DBHandler db = DBHandler.getInstance();
		try {
			cstmt = db.getConnection().prepareCall("CALL get_age_autori_nazione(?)");
			cstmt.setString(1, nazione); 
            cstmt.execute();
            cstmt.close();
            
            getAge(); //Si setta rs con la Select *
            
            ResultSetMetaData metaData = rs.getMetaData();

            // Stampa dei nomi delle colonne
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.print(columnName + "\t");
            }
            System.out.println();
            
            // Stampa dei risultati
            String value;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    value = rs.getString(i);
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
            
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}