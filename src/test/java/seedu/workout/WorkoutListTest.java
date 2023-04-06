package seedu.workout;


import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutListTest {

    //@@ author guillaume-grn
    @Test
    public void testGetWorkoutsInSpecificWeek() {
        // Create a new WorkoutList
        WorkoutList workoutList = new WorkoutList();

        // Add some workouts to the WorkoutList
        Calendar calendar0 = Calendar.getInstance();
        calendar0.set(2022, Calendar.DECEMBER, 31); // Saturday
        workoutList.addWorkout(new Workout(calendar0.getTime()));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2023, Calendar.JANUARY, 01); // Sunday
        workoutList.addWorkout(new Workout(calendar1.getTime()));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2023, Calendar.JANUARY, 02); // Monday
        workoutList.addWorkout(new Workout(calendar2.getTime()));

        // Get the workouts for the week of January 01, 2023 using any day in the week
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2023, Calendar.JANUARY, 03); // Tuesday
        WorkoutList workoutsInSpecificWeek = workoutList.getWorkoutsInSpecificWeek(calendar3.getTime());

        // Check that the correct workouts are returned
        assertEquals(2, workoutsInSpecificWeek.getWorkoutArrayList().size());
        assertEquals(calendar1.getTime(), workoutsInSpecificWeek.getWorkoutArrayList().get(0).getDate());
        assertEquals(calendar2.getTime(), workoutsInSpecificWeek.getWorkoutArrayList().get(1).getDate());
    }

    //@@ guillaume-grn
    @Test
    public void testCountSetsRepsPreparation() {
        // create some test data
        Exercise exercise1 = new Exercise("Bench press", "100", "8 6 4");
        Exercise exercise2 = new Exercise("Bench press", "150", "5 5 5 5");
        Calendar cal0 = Calendar.getInstance();
        cal0.set(2023, Calendar.MARCH, 1); // March 4th, 2023.Wednesday
        Workout workout1 = new Workout(cal0.getTime());
        cal0.set(2023, Calendar.MARCH, 3);
        Workout workout2 = new Workout(cal0.getTime());
        workout1.addExercise(exercise1);
        workout2.addExercise(exercise2);
        WorkoutList workoutList = new WorkoutList();
        workoutList.addWorkout(workout1);
        workoutList.addWorkout(workout2);

        // call the method being tested
        ArrayList<Exercise> distinctExercisesList = workoutList.countSetsRepsPreparation(cal0.getTime());

        // assert the expected output
        ArrayList<Exercise> expectedDistinctExercisesList = new ArrayList<>();
        Exercise expectedExercise1 = new Exercise("Bench press", "100", "8 6 4 5 5 5 5");
        expectedDistinctExercisesList.add(expectedExercise1);
        assertEquals(expectedDistinctExercisesList.get(0).getName(), distinctExercisesList.get(0).getName());
        assertEquals(expectedDistinctExercisesList.get(0).getRepsPerSet(),
                distinctExercisesList.get(0).getRepsPerSet());
    }

}
