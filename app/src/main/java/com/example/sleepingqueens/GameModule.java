package com.example.sleepingqueens;

import java.util.ArrayList;

public class GameModule {
    private ArrayList <Card> mainDeck, queens, player1,player2,q1,q2;



    public boolean AddExercise(CardNumbers c1,CardNumbers c2,CardNumbers c3,CardNumbers c4,CardNumbers c5)
    {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(c1.getNumber());
        nums.add(c2.getNumber());
        nums.add(c3.getNumber());
        if(c4!=null)
            nums.add(c4.getNumber());
        if(c5!=null)
            nums.add(c5.getNumber());

        for (int i = 0; i < nums.size(); i++)
        {
            int target = nums.get(i);
            int sum = 0;

            // סכום כולם חוץ מ-i
            for (int j = 0; j < nums.size(); j++)
            {
                if (j != i)
                {
                    sum += nums.get(j);
                }
            }
            if (sum == target)
                return true;
        }
        return false;
    }

}
