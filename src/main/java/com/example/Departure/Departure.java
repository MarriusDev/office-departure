package com.example.Departure;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Departure {

    public LocalTime userInput(String inputTime) {
        try {
            return LocalTime.parse(inputTime);
        } catch (Exception e) {
            System.out.println("Špatný formát, zkus to znovu.");
        }
        return LocalTime.of(0, 0);
    }

    public String start(LocalTime userInputTime) {
        String label;
        LocalDate currentDay = currentDay();

        label = switch (currentDay.getDayOfWeek()) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY ->
                    mondayThursday(userInputTime) + "\n" + mondayThursdayTimeleft(userInputTime);
            case FRIDAY -> friday(userInputTime);
            default -> "Dnes nepracuješ, je víkend.";
        };
        return label;
    }

    public String mondayThursday(LocalTime userInputTime) {
        LocalTime leaveTime = calculateLeaveTime(userInputTime, workTimeFromMondayToThursday());
        return "Čas odchodu: " + leaveTime;
    }

    public String mondayThursdayTimeleft(LocalTime userInputTime) {
        LocalTime leaveTime = calculateLeaveTime(userInputTime, workTimeFromMondayToThursday());
        return timeLeft(leaveTime);
    }

    public String friday(LocalTime userInputTime) {
        LocalTime leaveTimeWithPause = calculateLeaveTime(userInputTime, workTimeFridayWithPause());
        LocalTime leaveTimeWithoutPause = calculateLeaveTime(userInputTime, workTimeFridayWithoutPause());

        return "Čas odchodu s pauzou: " + leaveTimeWithPause + "\n" +
                fridayWithPauseTimeleft(userInputTime) + "\n" +
                "Čas odchodu bez pauzy: " + leaveTimeWithoutPause + "\n" +
                fridayWithoutPauseTimeleft(userInputTime);
    }

    public String fridayWithPauseTimeleft(LocalTime userInputTime) {
        LocalTime leaveTime = calculateLeaveTime(userInputTime, workTimeFridayWithPause());
        return timeLeft(leaveTime);
    }

    public String fridayWithoutPauseTimeleft(LocalTime userInputTime) {
        LocalTime leaveTime = calculateLeaveTime(userInputTime, workTimeFridayWithoutPause());
        return timeLeft(leaveTime);
    }

    public LocalTime calculateLeaveTime(LocalTime userInputTime, LocalTime workTime) {
        long entranceTimeInSeconds = userInputTime.toSecondOfDay();
        long workTimeInSeconds = workTime.toSecondOfDay();
        long leaveTimeInSeconds = entranceTimeInSeconds + workTimeInSeconds;
        return LocalTime.ofSecondOfDay(leaveTimeInSeconds % 86400);
    }

    public LocalTime workTimeFromMondayToThursday() {
        return LocalTime.of(8, 45, 0);
    }

    public LocalTime workTimeFridayWithPause() {
        return LocalTime.of(7, 0, 0);
    }

    public LocalTime workTimeFridayWithoutPause() {
        return LocalTime.of(6, 30, 0);
    }

    public LocalDate currentDay() {
        return LocalDate.now();
    }

    public String timeLeft(LocalTime leaveTime) {
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        Duration timeleft = Duration.between(now, leaveTime);
        long hours = Math.abs(timeleft.toHours());
        long minutes = Math.abs(timeleft.toMinutes() % 60);
        String sign = timeleft.isNegative() ? "-" : "";

        return String.format("Zbývá do odchodu: %s%02d:%02d\n", sign, hours, minutes);
    }

}
