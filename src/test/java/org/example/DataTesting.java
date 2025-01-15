package org.example;

import org.example.config.DatabaseConfig;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataTesting {

    private Connection connection;

    @BeforeClass
    public void setup() {
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUser(),
                    DatabaseConfig.getPassword()
            );
            System.out.println("✅✅✅ Database connection established successfully ✅✅✅");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to establish database connection");
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅✅✅ Database connection closed successfully ✅✅✅");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 1, description = "Validate that the film_actor table has non-empty actor_id entries")
    public void validateFilmActorTable_actor_id() {
        // List to store results
        List<String> actorIds = new ArrayList<>();

        try {
            // Query execution
            String query = "SELECT actor_id FROM film_actor";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                // Processing results
                while (resultSet.next()) {
                    actorIds.add(resultSet.getString("actor_id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error during query execution or result processing");
        }

        // Validation
        System.out.println("Actor IDs retrieved: " + actorIds);
        Assert.assertFalse(actorIds.isEmpty(), "The actor_ids list is empty. Query might have failed!");
    }

    @Test(priority = 2, description = "Validate that the film_actor table has at least 100 entries")
    public void validateFilmActorTable_rowCount() {
        int rowCount = 0;

        try {
            // Query execution
            String query = "SELECT COUNT(*) AS total FROM film_actor";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    rowCount = resultSet.getInt("total");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error during query execution or result processing");
        }

        // Validation
        System.out.println("Total rows in film_actor table: " + rowCount);
        Assert.assertTrue(rowCount >= 100, "The table has less than 100 rows. Validation failed!");
    }
}
