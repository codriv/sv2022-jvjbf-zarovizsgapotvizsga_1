package org.training360.sv2022jvjbfzarovizsgapotvizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreateHospitalCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreatePatientCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.HospitalDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.exceptions.EntityNotFoundException;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Hospital;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Patient;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.HospitalRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HospitalService {

    private HospitalRepository hospitalRepository;
    PatientService patientService;
    private ModelMapper modelMapper;

    public HospitalService(HospitalRepository hospitalRepository, PatientService patientService, ModelMapper modelMapper) {
        this.hospitalRepository = hospitalRepository;
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    public HospitalDto createHospital(CreateHospitalCommand command) {
        Hospital hospital = new Hospital(command.getName());
        hospitalRepository.save(hospital);
        return modelMapper.map(hospital, HospitalDto.class);
    }

    public PatientDto createPatient(Long id, CreatePatientCommand command) {
        Hospital hospital = findHospitalById(id);
        Patient patient = new Patient(command.getName(), command.getRegistrationDate(), command.getHospitalWard());
        patientService.savePatient(patient);
        hospital.addPatient(patient);
        return modelMapper.map(patient, PatientDto.class);
    }

    public Hospital findHospitalById(Long id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hospital", id));
    }

    public List<PatientDto> getPatients(Long id, Optional<HospitalWard> hospitalWard) {
        findHospitalById(id);
        return patientService.getPatients(id, hospitalWard);
    }

    public void removePatient(Long hospitalId, Long patientId) {
        Hospital hospital = findHospitalById(hospitalId);
        Patient patient = patientService.findPatientById(patientId);
        if (!hospital.getPatients().contains(patient)) {
            throw new EntityNotFoundException("Patient", patientId);
        }
        hospital.removePatient(patient);
    }
}
