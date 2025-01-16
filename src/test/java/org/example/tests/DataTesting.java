package org.example.tests;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DatabaseHelper;

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

    @Test(priority = 4, description = "🧤 BACK BOX TEST CASE 🧤 Validate actor_id and film_id in film_actor table")
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
}
