package com_bptn.weather;

import java.sql.Connection;
public class Main {
	public static void main(String[] args) {
		WeatherAppUserInsertion jdbc = new WeatherAppUserInsertion();
		Connection conn = jdbc.createConnection();
		jdbc.addUser(conn, "Ray", "Mist", "raymist", "18292831", "ray@mist.com", "Ray@22", true);
		
		jdbc.getUsers(conn);
		
		jdbc.closeConnection(conn);
	}
}