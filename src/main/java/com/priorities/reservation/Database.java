package com.priorities.reservation;

import java.sql.*;
import java.util.*;

public final class Database {
    private static final String URL = "jdbc:sqlite:reservation.db";
    public static final Map<String, String> TRAINS = new LinkedHashMap<String, String>();
    static { TRAINS.put("12001", "Shatabdi Express"); TRAINS.put("12309", "Rajdhani Express"); TRAINS.put("12627", "Karnataka Express"); TRAINS.put("12951", "Mumbai Rajdhani"); }
    private Database() { }

    public static void initialise() throws SQLException {
        try (Connection c = connection(); Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT NOT NULL)");
            s.execute("CREATE TABLE IF NOT EXISTS reservations (pnr TEXT PRIMARY KEY, passenger_name TEXT NOT NULL, train_no TEXT NOT NULL, train_name TEXT NOT NULL, class_type TEXT NOT NULL, journey_date TEXT NOT NULL, source TEXT NOT NULL, destination TEXT NOT NULL)");
            try (PreparedStatement p = c.prepareStatement("INSERT OR IGNORE INTO users(username,password) VALUES(?,?)")) { p.setString(1, "admin"); p.setString(2, "admin123"); p.executeUpdate(); }
        }
    }
    private static Connection connection() throws SQLException { return DriverManager.getConnection(URL); }
    public static boolean authenticate(String user, String pass) throws SQLException {
        try (Connection c = connection(); PreparedStatement p = c.prepareStatement("SELECT 1 FROM users WHERE username=? AND password=?")) { p.setString(1,user); p.setString(2,pass); try (ResultSet r=p.executeQuery()) { return r.next(); } }
    }
    public static void save(Reservation r) throws SQLException {
        String q="INSERT INTO reservations VALUES(?,?,?,?,?,?,?,?)";
        try (Connection c=connection(); PreparedStatement p=c.prepareStatement(q)) { p.setString(1,r.pnr);p.setString(2,r.passengerName);p.setString(3,r.trainNumber);p.setString(4,r.trainName);p.setString(5,r.travelClass);p.setString(6,r.journeyDate);p.setString(7,r.source);p.setString(8,r.destination);p.executeUpdate(); }
    }
    public static Reservation find(String pnr) throws SQLException {
        try (Connection c=connection(); PreparedStatement p=c.prepareStatement("SELECT * FROM reservations WHERE pnr=?")) { p.setString(1,pnr); try(ResultSet rs=p.executeQuery()) { if(!rs.next()) return null; Reservation r=new Reservation(); r.pnr=rs.getString("pnr");r.passengerName=rs.getString("passenger_name");r.trainNumber=rs.getString("train_no");r.trainName=rs.getString("train_name");r.travelClass=rs.getString("class_type");r.journeyDate=rs.getString("journey_date");r.source=rs.getString("source");r.destination=rs.getString("destination");return r; } }
    }
    public static boolean cancel(String pnr) throws SQLException { try(Connection c=connection();PreparedStatement p=c.prepareStatement("DELETE FROM reservations WHERE pnr=?")){p.setString(1,pnr);return p.executeUpdate()==1;} }
}
