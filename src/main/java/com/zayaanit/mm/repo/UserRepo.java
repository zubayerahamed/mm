package com.zayaanit.mm.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.zayaanit.mm.entity.User;

/**
 * @author Zubayer Ahamed
 * @since Nov 17, 2023
 */
@Repository
public interface UserRepo extends ServiceRepository<User> {

	Optional<User> findByUsername(String username);
}
