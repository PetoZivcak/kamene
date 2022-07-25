import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {
    private Field field;


    public void start(Field field) {
        this.field = field;
        makeChaos(this.field);
        Main.getInstance().setStartMillis(System.currentTimeMillis());
        do {
            System.out.println("Hrajes " + Main.getInstance().getPlayingSeconds() + " sekund");
            update();
            try {
                input();
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
            if (field.solvedGame() == true) {
                update();
                System.out.println(" ");
                System.out.printf("Vyhral si v case %s  sekund", String.valueOf(Main.getInstance().getPlayingSeconds()));
                System.exit(0);
            }

        } while (field.solvedGame() == false);
    }

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String read() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void update() {

        System.out.println();
        //Tlač hodnôt poľa
        for (int i = 0; i < field.getNrOfRow(); i++) {
            for (int j = 0; j < field.getNrOfCol(); j++) {
                if (field.getSquare(i, j).getValue() == 0) {
                    System.out.printf("%4s", " ");
                } else {
                    System.out.printf("%4s", field.getSquare(i, j).getValue());
                }
                if (j == field.getNrOfCol() - 1) {
                    System.out.println();
                }

            }

        }


    }

    public void makeChaos(Field field) {
        String myCommand;
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int myRandom = random.nextInt(4);
            switch (myRandom) {
                case 0:
                    myCommand = "U";
                    this.field.moveSquare(myCommand);
                    break;
                case 1:
                    myCommand = "D";
                    this.field.moveSquare(myCommand);
                    break;
                case 2:
                    myCommand = "L";
                    this.field.moveSquare(myCommand);
                    break;
                case 3:
                    myCommand = "R";
                    this.field.moveSquare(myCommand);
                    break;
            }
        }

    }

    public void input() throws MyException {
        final Pattern PATTERNINPUT = Pattern.compile("[UDLR]{1}", Pattern.CASE_INSENSITIVE);

        String input = read().toUpperCase();
        Matcher matcherInput = PATTERNINPUT.matcher(input);
        if (input.contains("X")) {
            System.out.println("Hra bola ukoncena");
            System.exit(0);
        }
        if (matcherInput.matches()) {

            this.field.moveSquare(input);
        } else {
            throw new MyException("Nespravny vstup na pohyb pouzite tlacitka u d l r");
        }
    }

}

