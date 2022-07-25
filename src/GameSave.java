import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameSave implements Serializable {
    private Field field;
    private static final String STONES_FILE = System.getProperty("user.home") + System.getProperty("file.separator") + "minesweeper.settings";




}