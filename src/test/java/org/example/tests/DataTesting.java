package org.example.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DataTesting {
    private DatabaseHelper dbHelper;

    @BeforeClass
    public void setUp() {
        dbHelper = new DatabaseHelper();
        try {
            dbHelper.connect();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to establish database connection");
        }
    }

    @AfterClass
    public void tearDown() {
        dbHelper.disconnect();
    }


    @Test(priority = 1, description = "🧤 BACK BOX TEST CASE 🧤 Validate actor_id values in film_actor table")
    public void validateFilmActorTable_actor_id() {
        String query = "SELECT * FROM film_actor";
        List<String> actorIds = null;

        try {
            actorIds = dbHelper.executeQuerylist(query, "actor_id");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error while executing the query");
        }

        System.out.println("Actor IDs retrieved: " + actorIds);
        Assert.assertNotNull(actorIds, "Actor IDs list is null");
        Assert.assertFalse(actorIds.isEmpty(), "Actor IDs list is empty");
    }

    @Test(priority = 2, description = "🧤 BACK BOX TEST CASE 🧤 Validate count for film_actors")
    public void validateActorCount() {
        String query = "SELECT COUNT(*) AS total FROM actor";
        int actorCount = 0;

        try {
            actorCount = dbHelper.executeQueryForSingleInt(query, "total");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error while executing the query");
        }

        System.out.println("Total number of actors: " + actorCount);
        Assert.assertTrue(actorCount > 0, "Actor count should be greater than zero");
    }

    @Test(priority = 3, description = "🧤 BACK BOX TEST CASE 🧤 Validate Film Actor Table_actor Count")
    public void validateFilmActorTable_actorCount() {
        int actorFinalCount = 0;
        String query = "SELECT COUNT(*) AS total FROM film_actor";
        try {
            actorFinalCount = dbHelper.executeQueryForSingleInt(query, "total");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error while executing the query");
        }

        System.out.println("Total register actors in film_actor: " + actorFinalCount);
        Assert.assertTrue(actorFinalCount > 0, "No records in table film_actor");
    }

    @Test(priority = 4, description = "🧤 BACK BOX TEST CASE 🧤 validate Film Descriptions")
    public void validateFilmDescriptions() throws SQLException {
        String query = "SELECT COUNT(*) AS invalid_count FROM film WHERE description IS NULL OR description = ''";
        int invalidCount = dbHelper.executeQueryForSingleInt(query, "invalid_count");
        System.out.println("🚀 Film where Description is null or empty: " + invalidCount);
        Assert.assertEquals(invalidCount, 0, "Check, some films has an empty description");
    }

    @Test(priority = 5, description = "🧤 BACK BOX TEST CASE 🧤 Validate actor_id and film_id in film_actor table")
    public void validateFilmActorTable_MultipleColumns() throws JsonProcessingException {
        String query = "SELECT actor_id, film_id FROM film_actor";
        List<Map<String, String>> records = null;

        try {
            records = dbHelper.executeQueryForMultipleColumns(query, List.of("actor_id", "film_id"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error while executing the query");
        }
        // Printing result as a Json
        dbHelper.printAsJson(records);

        Assert.assertNotNull(records, "Result set is null");
        Assert.assertFalse(records.isEmpty(), "No records found in film_actor table");

        for (Map<String, String> record : records) {
            String actorId = record.get("actor_id");
            String filmId = record.get("film_id");

            Assert.assertNotNull(actorId, "actor_id is null");
            Assert.assertNotNull(filmId, "film_id is null");

            // EXAMPLE: Additional information.
            Assert.assertTrue(Integer.parseInt(actorId) > 0, "Invalid actor_id: " + actorId);
            Assert.assertTrue(Integer.parseInt(filmId) > 0, "Invalid film_id: " + filmId);
        }
    }

    @Test(priority = 6, description = "🧤 BACK BOX TEST CASE 🧤 Validate actor names in actor table")
    public void verifyActorNamesInActorTable_multipleColumns() throws JsonProcessingException {
        String query = "SELECT actor_id, first_name, last_name FROM actor limit 10";
        List<Map<String, String>> records = null;

        try {
            records = dbHelper.executeQueryForMultipleColumns(query, List.of("actor_id", "first_name", "last_name"));
            dbHelper.printAsJson(records);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error while executing the query");
        }

        Assert.assertNotNull(records, "Result set is null");
        Assert.assertFalse(records.isEmpty(), "No records found in the actor table");

        // Verify results(Data base Response)
        for (Map<String, String> key : records) {
            String actorId = key.get("actor_id");
            String firstName = key.get("first_name");
            String lastName = key.get("last_name");

            Assert.assertNotNull(firstName, "First name is null for actor_id: " + actorId);
            Assert.assertFalse(firstName.isEmpty(), "First name is empty for actor_id: " + actorId);

            Assert.assertNotNull(lastName, "Last name is null for actor_id: " + actorId);
            Assert.assertFalse(lastName.isEmpty(), "Last name is empty for actor_id: " + actorId);

            Assert.assertNotNull(actorId, "actorId is null: ");
        }
    }
}
