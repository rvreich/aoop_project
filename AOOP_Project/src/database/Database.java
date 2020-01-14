package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;	//used to avoid sql-injection attack, same implementation like PHP
	public static Database instance = null; //simpe Singleton implementation for security purpose
	
	private Database() {
		try {
			//sdfk -> simple dictionary for kids
			//take connection, set default timezone, if not set jbdc will return nasty error
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sdfk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			if(!con.isClosed()) {
				stmt = con.createStatement();
				System.out.println("Connected to MYSQL Server...");
			}
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
	}
	
	public static Database getInstance() {
		if(instance == null) {
			synchronized (Database.class) {
				if(instance == null) {
					instance = new Database();
				}
			}
		}
		return instance;
	}
	
	public void insertUserTable(String username, String password) {
		String sql = "INSERT INTO usertable (Username, Password) VALUES (?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getUserCredentials() {
		String sql = "SELECT * FROM usertable";
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getSingleUserCredential(String username, String password) {
		String sql = "SELECT * FROM usertable WHERE Username = '"+username+"' AND Password = '"+password+"'";
		try {
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void insertContentTable(String word, String definition) {
		String sql = "INSERT INTO contenttable (Word, Definition) VALUES (?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, word);
			ps.setString(2, definition);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getContents() {
		String sql = "SELECT * FROM contenttable";
		try {
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getSingleContent(String word) {
		String sql = "SELECT * FROM contenttable WHERE Word = '"+word+"'";
		try {
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}