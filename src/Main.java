public class Main {

    private long startMillis;
    private static Main instance;

    public static Main getInstance() {
        if (instance == null) {
            new Main();
        }
        return instance;
    }

    private Main() {
        instance = this;
        Field field = new Field(3, 3);
        Console console = new Console();
        console.start(field);

    }

    public static void main(String[] args) {
        Main.getInstance();
    }


    public int getPlayingSeconds() {
        return (int) ((System.currentTimeMillis() - startMillis) / 1000);
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }
}
