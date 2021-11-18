package com.goudscode.repository;

import com.goudscode.domain.RoomDomain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepo extends CrudRepository<RoomDomain, UUID> {

    Optional<RoomDomain> findByRoomNo(Integer roomNo);
    boolean existsByRoomNo(Integer roomNo);
}
