package utils.queries.table;

public class actor {

    public static final String COUNT_ACTORS =
            "SELECT COUNT(*) AS total FROM actor";

    public static final String GET_ACTORID_FISTNAME_LASTNAME_LIMIT_10 =
            "SELECT " +
                    "actor_id, first_name, last_name " +
                    "FROM actor " +
                    "Limit 10";

}