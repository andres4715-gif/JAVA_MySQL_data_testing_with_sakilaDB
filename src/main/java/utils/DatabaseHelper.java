package utils;

import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    private Connection connection;

    // New connection with the Data Base
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUser(),
                    DatabaseConfig.getPassword()
            );
        }
    }

    // Close the Data Base connection
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Run a query and return the results as a list of strings
    public List<String> executeQuerylist(String query, String columnName) throws SQLException {
        List<String> results = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                results.add(resultSet.getString(columnName));
            }
        }
        return results;
    }

    public int executeQueryForSingleInt(String query, String columnName) throws SQLException {
        int result = 0; // Default value if no rows are returned
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) { // Assuming you expect one result
                result = resultSet.getInt(columnName);
            }
        }
        return result;
    }

    public List<Map<String, String>> executeQueryForMultipleColumns(String query, List<String> columnNames) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (String columnName : columnNames) {
                    row.put(columnName, resultSet.getString(columnName));
                }
                results.add(row);
            }
        }
        return results;
    }
}
