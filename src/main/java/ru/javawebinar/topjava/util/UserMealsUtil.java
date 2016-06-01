package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        for(UserMealWithExceed userMealWithExceed : getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000)){
            System.out.println(userMealWithExceed);
        }
        //        .toLocalDate();
        //        .toLocalTime();

    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> list = new ArrayList<>();
        Map<LocalDate,ArrayList<UserMeal>> map = new HashMap<>();

        for(UserMeal userMeal : mealList){
            if(map.containsKey(userMeal.getDateTime().toLocalDate())){
                map.get(userMeal.getDateTime().toLocalDate()).add(userMeal);
            }
            else {
                ArrayList<UserMeal> tempList = new ArrayList<>();
                tempList.add(userMeal);
                map.put(userMeal.getDateTime().toLocalDate(), tempList);
            }
        }
        for(Map.Entry<LocalDate, ArrayList<UserMeal>> tempMap : map.entrySet()){
            int tempCalories = 0;
            for(UserMeal userMeal : tempMap.getValue()){
                tempCalories += userMeal.getCalories();
            }
            for(UserMeal userMeal : tempMap.getValue()){
                if(userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime)){
                    list.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), tempCalories > caloriesPerDay));
                }
            }
        }
        return list;
    }
}
