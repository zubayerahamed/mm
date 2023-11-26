package com.zayaanit.mm.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.zayaanit.mm.entity.Token;

/**
 * @author Zubayer Ahamed
 * @since Nov 26, 2023
 */
public interface TokenRepo extends ServiceRepository<Token> {

	@Query(value = "SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
	List<Token> findAllValidTokenByUser(Long id);

	Optional<Token> findByToken(String token);
}
