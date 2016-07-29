package com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {

private static final Map<String, String> config = new HashMap<String, String>();
	
	private static Map<String, Connection> connPool = new HashMap<String, Connection>();
	
	static {
		loadConfig("config");
	}
	
	private static void loadConfig(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				String[] pair = line.split("=");
				if (pair.length < 2) {
					config.put(pair[0], "");
				} else {
					
					config.put(pair[0], pair[1]);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConn(String database) {
		try {
			Connection conn = connPool.get(database);
			if (conn != null && conn.isValid(0)) {
				return conn;
			}
			String user = config.get("user");
			String passwd = config.get("passwd");
			String ip = config.get("ip");
			String port = config.get("port");
			String url = "jdbc:postgresql://" + ip + ":" + port + "/" + database;
			conn = DriverManager.getConnection(url, user, passwd);
			
			connPool.put(database, conn);
			return conn;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static Statement getStatement(String database) {
		try {
			Connection connection = getConn(database);
			if (connection == null) {
				return null;
			}
			Statement statement = connection.createStatement();
			return statement;
		} catch (Exception e) {
			return null;
		}

	}
	
	public static void main(String[] args) throws SQLException {
		Statement statement = getStatement("growing");
		if (statement != null) {
			String sql = "select count(*) from top3pv";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		}
	}
}
