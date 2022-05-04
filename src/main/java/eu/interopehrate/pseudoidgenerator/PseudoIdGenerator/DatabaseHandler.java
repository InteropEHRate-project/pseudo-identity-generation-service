package eu.interopehrate.pseudoidgenerator.PseudoIdGenerator;

import java.sql.*;

public class DatabaseHandler {

    private static Connection conn = null;
    private static final String URL = "jdbc:mysql://database-mysql:3306/";
    private static final String DATABASE_NAME = "pseudo_identities";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "interopehrate";

    private static final String TABLE_NAME = "pseudo_identity";
    private static final String COLUMN_PREFIX = "prefix";
    private static final String COLUMN_INCR_NUMBER = "incremental_number";
    private static final int MINIMUM_VALUE = 1;

    public static int getIncrementalNumber(String prefix) throws SQLException {
        connectToDatabase();
        int incremental_number = selectTuple(prefix);
        disconnect();
        return incremental_number;
    }

    public static void connectToMySQL() {
        //System.out.println("Connected to MySQL database.");

        try{
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"+DATABASE_NAME+"'";
            //System.out.println("Query: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            boolean flag = rs.next();
            if(!flag){
                createDatabase();
            }

        }catch(Exception e) {
            e.printStackTrace();

        }
    }

    private static void createDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "CREATE DATABASE "+DATABASE_NAME;
        //System.out.println("Query: " + sql);
        stmt.executeUpdate(sql);
        //System.out.println("Database created successfully.");
    }

    private static void connectToDatabase() {
        //System.out.println("Connected to database.");

        try{
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(URL+DATABASE_NAME,USERNAME, PASSWORD);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static int selectTuple(String prefix) throws SQLException {
        int incremental_number;
        Statement stmt = conn.createStatement();
        String sql = "SELECT "+COLUMN_PREFIX+", "+COLUMN_INCR_NUMBER+" FROM "+TABLE_NAME+" WHERE "+COLUMN_PREFIX+" = '"+prefix+"'";
        //System.out.println("Query: " + sql);
        ResultSet rs = stmt.executeQuery(sql);
        boolean flag = rs.next();
        if(!flag){
            //System.out.println("Prefix not found.");
            incremental_number = MINIMUM_VALUE;
            insertTuple(prefix, incremental_number);

        } else {
            //System.out.println("Prefix already exists.");
            incremental_number = rs.getInt(COLUMN_INCR_NUMBER) + 1;
            updateTuple(prefix, incremental_number);

        }
        rs.close();
        stmt.close();
        return incremental_number;
    }

    private static void insertTuple(String prefix, int incremental_number) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO "+TABLE_NAME+" ("+COLUMN_PREFIX+", "+COLUMN_INCR_NUMBER+") VALUES ('"+prefix+"', "+incremental_number+")";
        //System.out.println("Query: " + sql);
        stmt.executeUpdate(sql);
        //System.out.println("1 row affected");
        stmt.close();
    }

    private static void updateTuple(String prefix, int incremental_number) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "UPDATE "+TABLE_NAME+" SET "+COLUMN_INCR_NUMBER+" = "+incremental_number+" WHERE "+COLUMN_PREFIX+" = '"+prefix+"'";
        //System.out.println("Query: " + sql);
        stmt.executeUpdate(sql);
        //System.out.println("1 row updated");
        stmt.close();
    }

    private static void disconnect(){
        try {
            conn.close();
            //System.out.println("Disconnected from database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
