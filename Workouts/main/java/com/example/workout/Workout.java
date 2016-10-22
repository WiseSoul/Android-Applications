package com.example.workout;

public class Workout {

    private String name;
    private String description;

    // An array of workouts which will be transmitted to our activities through an adapter
    public static final Workout[] workouts = {new Workout("Titan Chest", "\n4 Sets of Decline Benchpress (8-10 reps)\n4 Sets of Incline Benchpress (8-10 reps)\n4 Sets of Dumbell Incline Flyes (10-12 reps)\n4 Sets of Cable Flyes (12 reps)\n4 Sets of Pressing Machine (to failuire)"),
                                              new Workout("Iron Arms", "\nBICEPS:\n\n4 Sets of Pullups (12-15 reps)\n4 Sets of Bicep Curls (8-12 reps)\n4 Sets of Hammer Curls (8-10 reps)\n4 Sets of 21's\n\nTRICEPS:\n\n4 Sets of Weighted Dips (8-10)\n4 Sets of SkullCrushers (8-10 reps)\n4Sets of Cable Triceps Pulldowns (8-10 reps)\n4 Sets of Reverse Cable Triceps Pulldowns (8-10 reps)\n4 Sets of single-arm Triceps Pulldowns (to failuire)"),
                                              new Workout("Silver Back", "\n4 Sets of Weighted Pullups (8-10 reps)\n4 Sets of Bench Rows (8-10 reps)\n4 Sets of Cable Pullups (8-10 reps)\n4 Sets of Reverse Cable Pullups (8-10 reps)\n4 Sets of Back Flyes (to failuire"),
                                              new Workout("Firm Legs", "\n4 Sets of Squats (8-10 reps)\n4 Sets of Lunges(or Reverse Lunges) (8-12 reps)\n4 Sets of Deadlifts(light weight) (10-15 reps)\n4 Sets of Calf Raises (15 reps)\n4 Sets of Squat Machine's (10-12 reps)")
                                            };
// The class construtor
    private Workout(String name,String description)
    {
        this.name=name;
        this.description=description;
    }

    String getName()
    {
        return name;
    }

    String getDescription()
    {
        return description;
    }

    // The String representation of a Workout is it's name
   public String toString()
    {
        return this.name;
    }
}
