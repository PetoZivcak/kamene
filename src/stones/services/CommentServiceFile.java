package stones.services;
import stones.services.CommentService;



import stones.entity.Comment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class CommentServiceFile implements CommentService {
    private List<Comment> comments = new ArrayList<>();
    private static final String FILE = "comments.bin";

    @Override
    public void addComment(Comment comment) {
        comments = load();
        comments.add(comment);
        save(comments);
    }

    @Override
    public List<Comment> getComments(String game) {
        comments = load();
        return comments
                .stream().filter(c -> c.getGame().equals(game))
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        comments = new ArrayList<>();
        save(comments);
    }

    private List<Comment> load() {
        try (
                var is = new ObjectInputStream(new FileInputStream(FILE))
        ) {
            return (List<Comment>) is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new GameStudioException(e);
        }
    }

    private void save(List<Comment> commentsToSave) {
        try (
                var os = new ObjectOutputStream(new FileOutputStream(FILE))
        ) {
            os.writeObject(commentsToSave);
        } catch (IOException e) {
            throw new GameStudioException(e);
        }
    }
}
