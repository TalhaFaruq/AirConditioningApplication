package org.app.AirConditioningApplication.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class WorkLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workLogId;
    private LocalDate date;
    private int numberOfHours;
}
