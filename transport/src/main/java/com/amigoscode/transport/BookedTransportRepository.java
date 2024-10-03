package com.amigoscode.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedTransportRepository extends JpaRepository<Booking, Long> {

}
