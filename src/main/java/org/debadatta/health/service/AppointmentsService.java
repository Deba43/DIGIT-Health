package org.debadatta.health.service;

import org.debadatta.health.repo.AppointmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentsService {

    @Autowired
    private AppointmentsRepo appointmentsRepo;

}
