package com_bptn.weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;
public class WeatherAppUserInsertion {
	static Dotenv dotenv = Dotenv.load();
	private static final String userName = dotenv.get("db_userName");
	private static final String password = dotenv.get("db_password");
	private static final String dbName = dotenv.get("db_name");
	private static final String port = dotenv.get("db_port");
	private static final String dbUrl = dotenv.get("db_Url");
	public Connection createConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://" + dbUrl + ":" + port + "/" + dbName, userName,
					password);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return conn;
	}
	public void addUser(Connection conn, String firstName, String lastName, String username, String phone,
			String emailId, String password, boolean emailVerified) {
		try {
			PreparedStatement stmt = null;
			String sql = "INSERT INTO \"User\" (\"firstName\", \"lastName\", \"username\", \"phone\", \"emailId\", \"password\", \"emailVerified\", \"createdOn\") VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, username);
			stmt.setString(4, phone);
			stmt.setString(5, emailId);
			stmt.setString(6, password);
			stmt.setBoolean(7, emailVerified);
			int resultCount = stmt.executeUpdate();
			System.out.println(resultCount + " record(s) inserted");
			stmt.close();
		} catch (SQLException ex) {
			// handle exception
			System.out.println("Exception:" + ex.getMessage());
		}
	}
	
	public void getUsers(Connection conn) {
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String sql = "SELECT * FROM \"User\"";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(rs.getString("firstName"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean closeConnection(Connection conn) {
		boolean flag = true;
		
		try {
			conn.close();
		} catch (SQLException ex) {
			flag = false;
			// Handle exception, in case of any errors:
			System.out.println("Exception:" + ex.getMessage());
		}
		
		return flag;
	}
}
