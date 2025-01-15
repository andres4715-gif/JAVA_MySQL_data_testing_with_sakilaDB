package org.example.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DatabaseHelper;

import java.util.List;

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


    @Test(priority = 1, description = "Validate actor_id values in film_actor table")
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

    @Test(priority = 2, description = "Validate that the film_actor")
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
}