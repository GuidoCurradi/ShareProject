package it.betacom.dbHandler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.print.DocFlavor.INPUT_STREAM;

public class DBHandler {
	
	private String ConnessioneDB = null;
	private static DBHandler instance = null;
	private final Properties connectionProperties;
	private String connessione = null;
	private String utente = null;
	private String psw = null;
	private Connection con = null;
	private InputStream in = DBHandler.class.getClassLoader().getResourceAsStream("config.properties");
	
	public static synchronized DBHandler getInstance() {
		if (instance == null) {
			instance = new DBHandler();
		}
		
		return instance;
	}
	
	DBHandler() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connectionProperties = new Properties();
		
	    try {
			connectionProperties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    ConnessioneDB = connectionProperties.getProperty("db.url")+connectionProperties.getProperty("db.name");
		utente = connectionProperties.getProperty("db.user");
		psw = connectionProperties.getProperty("db.password");
	}
	
	public Connection getConnection() {
		try {
//			con = DriverManager.getConnection(connessione, info)
			con = DriverManager.getConnection(ConnessioneDB, utente, psw);
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		
		return con;
	}
	
	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Connessione Chiusa\n");
	}
	
	public void queryDiProva() throws SQLException {
		Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select first_name, country from customers");
        while(rs.next()) {
            System.out.println(rs.getString(1) + "||" + rs.getString("country"));
        }
	}
	
}
