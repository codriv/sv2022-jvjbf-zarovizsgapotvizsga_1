package org.training360.sv2022jvjbfzarovizsgapotvizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.exceptions.EntityNotFoundException;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Patient;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    PatientRepository patientRepository;
    private ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Patient", id));
    }

    public Patient savePatient(Patient patient) {
        patientRepository.save(patient);
        return patient;
    }

    public List<PatientDto> getPatients(Long id, Optional<HospitalWard> hospitalWard) {
        List<Patient> patients = patientRepository.getPatients(id, hospitalWard);
        return patients.stream().map(p -> modelMapper.map(p, PatientDto.class)).toList();
    }
}
