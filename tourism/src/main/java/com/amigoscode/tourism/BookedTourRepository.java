package com.amigoscode.tourism;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedTourRepository extends JpaRepository<BookedTour, Integer> {

}
