import java.time.*;

public class MainTest {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.of(2026, 6, 2, 9, 8);
        LocalDateTime end = LocalDateTime.of(2026, 6, 3, 9, 32);
        float dureeTravail = 0;
        try {
            dureeTravail = calculateDT(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Durée de travail en heures: " + dureeTravail);
    }

    public static float calculateDT(LocalDateTime start, LocalDateTime end) {

        if (start == null || end == null || !start.isBefore(end)) {
            return 0f;
        }

        LocalTime workStart = LocalTime.of(8, 0);
        LocalTime workEnd = LocalTime.of(16, 0);

        long totalMinutes = 0;

        LocalDate currentDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        while (!currentDate.isAfter(endDate)) {

            DayOfWeek day = currentDate.getDayOfWeek();

            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {

                LocalDateTime dayStart = LocalDateTime.of(currentDate, workStart);
                LocalDateTime dayEnd = LocalDateTime.of(currentDate, workEnd);

                LocalDateTime effectiveStart = currentDate.equals(start.toLocalDate())
                        ? (start.isAfter(dayStart) ? start : dayStart)
                        : dayStart;

                LocalDateTime effectiveEnd = currentDate.equals(end.toLocalDate())
                        ? (end.isBefore(dayEnd) ? end : dayEnd)
                        : dayEnd;

                if (effectiveStart.isBefore(effectiveEnd)) {
                    totalMinutes += Duration.between(
                            effectiveStart,
                            effectiveEnd).toMinutes();
                }
            }

            currentDate = currentDate.plusDays(1);
        }

        return (float) totalMinutes;
    }
}
