package seedu.duke;


import seedu.calorietracker.CalorieTracker;
import seedu.commands.Command;
import seedu.commands.ExitCommand;
import seedu.commands.IncorrectSyntaxCommand;
import seedu.exceptions.InvalidSyntaxException;
import seedu.parser.Parser;
import seedu.storage.ReadFile;
import seedu.ui.Ui;
import seedu.workout.WorkoutList;

public class Duke {
    private WorkoutList workoutList;
    private CalorieTracker calorieTracker;

    private static final String FILE_PATH = "data/workoutRecording.txt";

    public Duke(String filePath) {
        ReadFile.readFile(filePath);
    }
    public static void main(String[] args) {
        new Duke(FILE_PATH).run();
    }

    private void run() {
        workoutList = new WorkoutList();
        calorieTracker = new CalorieTracker();
        Ui.showWelcomeMessage();
        executeCommandUntilExit();
    }

    private void executeCommandUntilExit() {
        Command command;
        do {
            String userInput = Ui.getUserInput();
            try {
                command = new Parser().processCommand(userInput);
            } catch (InvalidSyntaxException ise) {
                command = new IncorrectSyntaxCommand(ise.toString());
            }
            command.setData(workoutList, calorieTracker);
            System.out.println(command.execute());
        } while (!ExitCommand.isExit(command));
    }
}

