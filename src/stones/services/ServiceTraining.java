package stones.services;

import stones.entity.Score;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.List;

public class ServiceTraining implements ScoreService {

    public static final String JDBC_URL = "jdbc:postgresql://localhost/trainingdatabase";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "postgres";
    public static final String RESET_STATEMENT="DELETE FROM score";
public static final String STATEMENT_ADD_SCORE="INSERT INTO score VALUES (?, ?, ?, ?)";

    @Override
    public void addScore(Score score) {
        try (
                var connection=DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
                var statement=connection.prepareStatement(STATEMENT_ADD_SCORE);
                ){
            statement.setString(1,score.getGame());
            statement.setString(2, score.getUsername());
            statement.setInt(3,score.getPoints());
            statement.setTimestamp(4,new Timestamp(score.getPlayedOn().getTime()));


        }catch (Exception e){}

    }

    @Override
    public List<Score> getBestScores(String game) {
        return null;
    }

    @Override
    public void reset() {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.createStatement();
        ) {
            statement.executeUpdate(RESET_STATEMENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
