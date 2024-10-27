package com.mycompany.database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database_connection {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/demoSchema";
    private static final String USER = "utsho";
    private static final String PASSWORD = "utsho@11";

    public static void main(String[] args) {
        // Uncomment the desired method to use
        fetchUserData();  // To fetch and display user data
        insertUserData(); // To insert new user data
        fetchUserData();  // To fetch and display user data again
    }

    // Function to fetch user data from the database
    public static void fetchUserData() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");

            // Create a Statement to execute SQL queries
            statement = connection.createStatement();

            // Define the SQL query
            String sql = "SELECT * FROM `users`"; // No need to specify database name in the query if already connected

            // Execute the query and retrieve the result
            resultSet = statement.executeQuery(sql);

            // Process the result set
            while (resultSet.next()) {
                // Assuming your table has columns: u_id (int) and name (String)
                int id = resultSet.getInt("u_id"); // Replace with your column name
                String name = resultSet.getString("name"); // Replace with your column name
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } finally {
            // Close the ResultSet, Statement, and Connection in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
    }

    // Function to insert user data into the database
    public static void insertUserData() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");

            // SQL query to insert data
            String sql = "INSERT INTO `users` (u_id, name) VALUES (?, ?)"; // Adjust columns as needed

            // Prepare the statement
            preparedStatement = connection.prepareStatement(sql);

            // Take user input
            System.out.print("Enter user ID: ");
            int id = scanner.nextInt(); // Read integer input for ID
            scanner.nextLine(); // Consume the leftover newline
            System.out.print("Enter user name: ");
            String name = scanner.nextLine(); // Read string input for name

            // Set the values for the prepared statement
            preparedStatement.setInt(1, id); // Set ID at parameter index 1
            preparedStatement.setString(2, name); // Set name at parameter index 2

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) successfully.");

        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } finally {
            // Close the PreparedStatement and Connection
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
            // Close the scanner
            scanner.close();
        }
    }
}
