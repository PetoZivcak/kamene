package stones.services;

import stones.entity.Comment;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    private static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "postgres";

    private static final String STATEMENT_ADD_COMMENT = "INSERT INTO comments VALUES (?, ?, ?, ?)";
    private static final String GET_COMMENTS = "SELECT game, username, comment, commented_on FROM comments WHERE game= ? ORDER BY commented_on DESC";
    private static final String STATEMENT_RESET = "DELETE FROM comments";


    @Override
    public void addComment(Comment comment) {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(STATEMENT_ADD_COMMENT)
        ) {
            if (comment.getComment().length() > 0) {
                statement.setString(1, comment.getGame());
                statement.setString(2, comment.getUsername());
                statement.setString(3, comment.getComment());
                statement.setTimestamp(4, new Timestamp(comment.getCommented_on().getTime()));
                statement.executeUpdate();
            } else {
                System.out.println("Nebol pridany ziadny comment");
            }

        } catch (Exception e) {
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(GET_COMMENTS)
        ) {
            statement.setString(1, game);
            try (
                    var rs = statement.executeQuery()
            ) {
                var comments = new ArrayList<Comment>();
                while (rs.next()) {
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                }
                return comments;
            }


        } catch (SQLException e) {
            throw new GameStudioException(e);
        }

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
