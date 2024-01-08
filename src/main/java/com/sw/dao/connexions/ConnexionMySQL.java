package com.sw.dao.connexions;

import com.sw.exceptions.ExceptionDB;
import java.sql.*;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;
public class ConnexionMySQL extends ConnexionDB {

    private Connection connection; // Variable de classe pour la connexion
    private static final Dotenv dotenv = Dotenv.load();
    @Override
    public Connection connection() throws ExceptionDB {
        // Load environment variables
        String dbHost = dotenv.get("DB_HOST");
        String dbUsername = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dbName = dotenv.get("DB_NAME");


    //private String motDePasse = "Mateo3945";

        // Vérifiez si l'une d'elles est nulle
        if (dbHost == null || dbUsername == null || dbPassword == null || dbName == null) {
            throw new ExceptionDB("One or more environment variables required for the database connection are null.");
        }


        // Check if any of the environment variables are null
        if (dbHost == null || dbUsername == null || dbPassword == null || dbName == null) {
            throw new ExceptionDB("One or more environment variables required for the database connection are null.");
        }

        // JDBC connection properties
        Properties props = new Properties();
        props.setProperty("user", dbUsername);
        props.setProperty("password", dbPassword);
        props.setProperty("useSSL", "true"); // Enable SSL

        try {
            // Connect to the database
            String url = "jdbc:mysql://" + dbHost + "/" + dbName + "?useSSL=false"; // Update this line if SSL is not used
            this.connection = DriverManager.getConnection(url, props); // Use the class variable

            // Return the connection object
            return this.connection;
        } catch (SQLException e) {
            System.out.println("Failure in database connection: SQL Error.");
            throw new ExceptionDB("Error connecting to the MySQL database: SQL Error", e);
        }
    }

    @Override
    public Connection getConnection() throws ExceptionDB, SQLException {
        // Check if the connection is already established and not closed
        if (this.connection == null || this.connection.isClosed()) {
            connection(); // Establish a new connection if necessary
        }
        return this.connection;
    }

    @Override
    public void closeConnection() throws ExceptionDB {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new ExceptionDB("Error closing the database connection", e);
            }
        }
    }
}
