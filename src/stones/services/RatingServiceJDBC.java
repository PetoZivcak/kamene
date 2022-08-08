package stones.services;

import stones.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RatingServiceJDBC implements RatingService {
    //        CREATE TABLE rating(
//            game VARCHAR(64) NOT NULL,
//    username VARCHAR(64) CONSTRAINT game  NOT NULL UNIQUE,
//    rating INT CHECK(rating BETWEEN 1 AND 5) NOT NULL ,
//    rated_ond DATE NOT NULL
//		 );
    public int checkValue;
    private static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "postgres";

    private static final String STATEMENT_ADD_RATING = "INSERT INTO rating VALUES (?, ?, ?, ?)";
    private static final String STATEMENT_UPDATE_RATING = "UPDATE rating SET  game=?,username=?,rating=?, rated_on=?  WHERE game=? AND username=?";
    private static final String STATEMENT_GET_RATING_CHECK = "SELECT count(game) FROM rating WHERE game=? and username=? ";
    private static final String STATEMENT_GET_AVG_RATING = "SELECT  round(avg(rating),0) FROM rating WHERE game=? ";
    private static final String STATEMENT_GET_RATING = "SELECT rating.rating FROM rating WHERE game=? and username=?";
    private static final String STATEMENT_RESET = "DELETE FROM rating";

    @Override
    public void setRating(Rating rating) {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statementAdd = connection.prepareStatement(STATEMENT_ADD_RATING);
                var statementCheck = connection.prepareStatement(STATEMENT_GET_RATING_CHECK);
                var statementUpdate = connection.prepareStatement(STATEMENT_UPDATE_RATING)

        ) {
            statementCheck.setString(1, rating.getGame());
            statementCheck.setString(2, rating.getUsername());


            try (var rs = statementCheck.executeQuery()) {

                int rsVal = 0;
                if (rs.next()) {
                    rsVal = rs.getInt(1);
                }
                //statementCheck.executeUpdate();
                if (rsVal > 0) {
                    statementUpdate.setString(1, rating.getGame());
                    statementUpdate.setString(2, rating.getUsername());
                    statementUpdate.setInt(3, rating.getRating());
                    statementUpdate.setTimestamp(4, new Timestamp(rating.getRated_on().getTime()));
                    statementUpdate.setString(5, rating.getGame());
                    statementUpdate.setString(6, rating.getUsername());
                    statementUpdate.executeUpdate();
                }
                if (rsVal == 0) {
                    statementAdd.setString(1, rating.getGame());
                    statementAdd.setString(2, rating.getUsername());
                    statementAdd.setInt(3, rating.getRating());
                    statementAdd.setTimestamp(4, new Timestamp(rating.getRated_on().getTime()));
                    statementAdd.executeUpdate();
                }
            } catch (SQLException e) {
                throw new GameStudioException(e);
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        int result = 0;
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(STATEMENT_GET_AVG_RATING);
        ) {
            statement.setString(1, game);
            try (var rs = statement.executeQuery();) {
                while (rs.next()) {
                    result = rs.getInt(1);
                }
                return result;
            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getRating(String game, String username) {
        int myRating = 0;
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_GET_RATING)
        ) {
            statement.setString(1, game);
            statement.setString(2, username);

            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    myRating = rs.getInt(1);
                    return myRating;
                }

            }

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return myRating;
    }

    @Override
    public void reset() {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.createStatement();

        ) {
            statement.executeUpdate(STATEMENT_RESET);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }

    }
}
