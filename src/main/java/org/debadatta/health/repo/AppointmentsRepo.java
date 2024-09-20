package org.debadatta.health.repo;

import org.debadatta.health.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsRepo extends JpaRepository<Appointments, Integer> {

}
