package org.training360.sv2022jvjbfzarovizsgapotvizsga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long id;
    private String name;
    private LocalDate registrationDate;
    private HospitalWard hospitalWard;
}
