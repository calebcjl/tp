package seedu.workout;


import seedu.parser.DateFormatter;
import seedu.ui.Ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WorkoutList {
    public static final int NO_CURRENT_WORKOUT = -1;
    public static final int EMPTY = 0;
    private static final String EMPTY_WORKOUT_LIST_MESSAGE = "No workout recorded.";
    private static final String WORKOUT_LIST_HEADER =
            "Here are the list of dates of your workouts:" + System.lineSeparator();

    private ArrayList<Workout> workoutArrayList;
    private int currentWorkoutIndex;

    public WorkoutList() {
        workoutArrayList = new ArrayList<>();
        currentWorkoutIndex = NO_CURRENT_WORKOUT;
    }

    public void addWorkout(Workout workout) {
        workoutArrayList.add(workout);
        currentWorkoutIndex = getLastIndex();
    }

    public void setCurrentWorkoutIndex(int currentWorkoutIndex) {
        this.currentWorkoutIndex = currentWorkoutIndex;
    }

    public int getCurrentWorkoutIndex() {
        return currentWorkoutIndex;
    }

    public int getLastIndex() {
        return workoutArrayList.size() - 1;
    }

    public Workout getCurrentWorkout() {
        return workoutArrayList.get(currentWorkoutIndex);
    }

    public ArrayList<Workout> getWorkoutArrayList() {
        return workoutArrayList;
    }

    public String deleteWorkout(Date date) {
        for (Workout workout : workoutArrayList) {
            if (workout.getDate() == date) {
                workoutArrayList.remove(workout);
                return "Workout deleted";
            }
        }

        return "Workout not in list";
    }

    @Override
    public String toString() {
        if (workoutArrayList.size() == EMPTY) {
            return EMPTY_WORKOUT_LIST_MESSAGE;
        }

        StringBuilder workoutListString = new StringBuilder();
        workoutListString.append(WORKOUT_LIST_HEADER);
        for (int i = 0; i < workoutArrayList.size(); i += 1) {
            workoutListString.append(i + 1).append(". "
                    + DateFormatter.dateToString(workoutArrayList.get(i).getDate()) + System.lineSeparator());
        }

        return workoutListString.toString();
    }

    //@@ author guillaume-grn
    public ArrayList<Exercise> countSetsRepsPreparation(Date dayInSpecificWeekDate) {
        WorkoutList workoutsInSpecificWeek = getWorkoutsInSpecificWeek(dayInSpecificWeekDate);
        ArrayList<Exercise> distinctExercisesList = new ArrayList<>();

        for (Workout workout : workoutsInSpecificWeek.getWorkoutArrayList()) {
            for (Exercise exercise : workout.getExercises()) {
                boolean isExistingExercise = false;
                for (Exercise distinctExercise : distinctExercisesList) {
                    if (exercise.getName().equals(distinctExercise.getName())) {
                        distinctExercise.setRepsPerSet(distinctExercise.getRepsPerSet() + " "
                                + exercise.getRepsPerSet());
                        isExistingExercise = true;
                        break;
                    }
                }
                if (!isExistingExercise) {
                    Exercise distinctExercise = new Exercise(exercise.getName(), exercise.getWeight(),
                            exercise.getRepsPerSet());
                    distinctExercisesList.add(distinctExercise);
                }
            }
        }
        return distinctExercisesList;
    }

    //@@ author guillaume-grn
    public void countSetsReps(Date dayInSpecificWeekDate) {
        ArrayList<Exercise> distinctExercisesList = countSetsRepsPreparation(dayInSpecificWeekDate);
        Ui.displayCountSetsReps(distinctExercisesList,dayInSpecificWeekDate);
    }


    //@@ author guillaume-grn
    public WorkoutList getWorkoutsInSpecificWeek(Date dayInSpecificWeekDate) {
        WorkoutList workoutsInSpecificWeek = new WorkoutList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInSpecificWeekDate);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date startOfWeekDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date endOfWeekDate = calendar.getTime();
        for (Workout workout : workoutArrayList) {
            Date workoutDate = workout.getDate();
            if (workoutDate.compareTo(startOfWeekDate) >= 0 && workoutDate.compareTo(endOfWeekDate) <= 0) {
                workoutsInSpecificWeek.addWorkout(workout);
            }
        }
        return workoutsInSpecificWeek;
    }
}
