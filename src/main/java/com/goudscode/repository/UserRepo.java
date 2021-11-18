package com.goudscode.repository;

import com.goudscode.domain.UserDomain;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepo extends CrudRepository<UserDomain, UUID> {
}
