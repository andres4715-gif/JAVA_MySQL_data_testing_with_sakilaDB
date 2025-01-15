package org.example;

import org.example.config.DatabaseConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataTesting {

    @Test
    public void validateFilmActorTable_actor_id() {
        // List to store results
        List<String> actorIds = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.getUrl(),
                DatabaseConfig.getUser(),
                DatabaseConfig.getPassword())) {

            System.out.println("✅✅✅ Good Connection ✅✅✅");

            // DB QUERY
            String query = "SELECT * FROM film_actor";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                // Processing results
                while (resultSet.next()) {
                    actorIds.add(resultSet.getString("actor_id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error during database connection or query execution");
        }

        // Validation
        System.out.println("Actor IDs retrieved: " + actorIds);
        Assert.assertFalse(actorIds.isEmpty(), "The actor_ids list is empty. Query might have failed!");
    }
}
