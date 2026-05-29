import java.time.*;

public class MainTest {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 2, 8, 0);
        float dureeTravail = 0;
        try {
            dureeTravail = calculateDT(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Durée de travail en heures: " + dureeTravail);
    }

    public static float calculateDT(LocalDateTime start, LocalDateTime end) {
        long seconds = Duration.between(start, end).getSeconds();
        return seconds / 60.0f;
    }
}
