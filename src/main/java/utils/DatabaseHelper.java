package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.DatabaseConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * Prints a list of maps as a formatted JSON string to the console.
     * Each map in the list represents a record or row of data, where keys are column names
     * and values are the corresponding data values (all as strings).
     *
     * @param dataBaseDataResponse A list of maps representing the data to be printed as JSON.
     *                             Each map should have String keys and String values.
     * @throws JsonProcessingException If there is an error during the JSON serialization process.
     *                                This can occur if the input data cannot be correctly converted to JSON.
     */
    public void printAsJson(List<Map<String, String>> dataBaseDataResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataBaseDataResponse);
        System.out.println(jsonOutput);
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
