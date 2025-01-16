package utils.queries.table;

public class film_actor {

    public static final String GET_ALL_FILM_ACTORS =
            "SELECT * FROM film_actor";

    public static final String COUNT_FILM_ACTOR =
            "SELECT COUNT(*) AS total FROM film_actor";

    public static final String GET_ALL_ACTORID_FILMID =
            "SELECT actor_id, film_id " +
                    "FROM film_actor";
}


