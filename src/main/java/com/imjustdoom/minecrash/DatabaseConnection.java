package com.imjustdoom.minecrash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public Statement stmt;

    public DatabaseConnection() {
        String user = Main.config.user;
        String pass = Main.config.password;
        String server = Main.config.host;
        String port = Main.config.port;
        String database = Main.config.database;

        //Connect and setup database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + server + ":" + port + "/" + database, user, pass);
            stmt = con.createStatement();

            createSQLTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSQLTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS review (" +
                "`uuid` VARCHAR(36) NOT NULL, " +
                "`username` VARCHAR(16) NOT NULL, " +
                "`primary_group` VARCHAR(36) NOT NULL, " +
                "KEY `players_username` (`uuid`) USING BTREE, " +
                "PRIMARY KEY (`uuid`) " +
                ") ENGINE=InnoDB;";

        stmt.executeUpdate(sql);
    }
}
