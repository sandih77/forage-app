package com.core.forage_app.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.DemandeStatut;
import com.core.forage_app.repository.DemandeStatutRepository;

@Service
public class DemandeStatutService {
    @Autowired
    private DemandeStatutRepository demandeStatutRepository;

    public DemandeStatut findByDemandeId(int id) {
        return this.demandeStatutRepository.findByDemandeId(id);
    }

    public DemandeStatut findTopByDemandeIdOrderByIdDesc(int id) {
        return this.demandeStatutRepository.findTopByDemandeIdOrderByIdDesc(id);
    }

    public void save(DemandeStatut demandeStatut) {
        this.demandeStatutRepository.save(demandeStatut);
    }

    public float calculateDT(LocalDateTime start, LocalDateTime end) {

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

    public List<DemandeStatut> findAll() {
        return this.demandeStatutRepository.findAll();
    }
}
