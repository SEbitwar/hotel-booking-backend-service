package com.goudscode.repository;

import com.goudscode.domain.BookingDomain;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BookingRepo extends CrudRepository<BookingDomain, UUID>{
}
