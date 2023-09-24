package com.khali.api3.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khali.api3.domain.appointment.Appointment;
import com.khali.api3.domain.user.User;
import com.khali.api3.repositories.AppointmentRepository;
import com.khali.api3.services.AppointmentService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 AppointmentService appointmentService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public List<Appointment> getAppointmentsByUser(User user) {
        return appointmentRepository.findAppointmentByUser(user.getId());
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));

        // Update the appointment object with the details from the request body
        appointment.setUser(appointmentDetails.getUser());
        appointment.setAppointmentType(appointmentDetails.getAppointmentType());
        appointment.setStartDate(appointmentDetails.getStartDate());
        appointment.setEndDate(appointmentDetails.getEndDate());
        appointment.setInsertDate(appointmentDetails.getInsertDate());
        appointment.setResultCenter(appointmentDetails.getResultCenter());
        appointment.setClient(appointmentDetails.getClient());
        appointment.setProject(appointmentDetails.getProject());
        appointment.setJustification(appointmentDetails.getJustification());
        appointment.setStatus(appointmentDetails.getStatus());
        appointment.setFeedback(appointmentDetails.getFeedback());
        appointment.setApt_updt(appointmentDetails.getApt_updt());

        return appointmentRepository.save(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/manager/{id}")
    public List<Appointment> getManagerAppointments(User user){
        return appointmentRepository.findByManager(user.getId());
    }

    public List<Appointment> getAppointmentByDate(List<Appointment> appointmentsList, LocalDate dataInit, LocalDate dataFim){
        return appointmentService.findAppointmentByDate(appointmentsList, dataInit, dataFim);
    }

    @GetMapping("/manager/time/{id}")
    public List<Appointment> getAppointmentByHour(User user){
        LocalTime dataInit = LocalTime.of(9, 30, 00);
        LocalTime dataFim = LocalTime.of(12, 00, 00);
        List<Appointment> appointmentsList = appointmentRepository.findByManager(user.getId());

        return appointmentService.findAppointmentByHour(appointmentsList, dataInit, dataFim);
    }
    
    public List<Appointment> getAppointmentByDateHour(List<Appointment> appointmentsList, LocalDateTime dataInit, LocalDateTime dataFim){
        return appointmentService.findAppointmentByDateHour(appointmentsList, dataInit, dataFim);
    }
}