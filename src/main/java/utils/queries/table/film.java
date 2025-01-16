package utils.queries.table;

public class film {

    public static final String COUNT_FILM =
            "SELECT COUNT(*) AS invalid_count " +
                    "FROM film " +
                    "WHERE description IS NULL OR description = ''";

    public static final String FILM_JOIN_FILM_CATEGORY =
            "SELECT COUNT(*) categoryID\n" +
                    "FROM film f\n" +
                    "JOIN film_category fc\n" +
                    "ON f.film_id = fc.film_id\n" +
                    "WHERE category_id is NULL";
}
