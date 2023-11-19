package com.zayaanit.mm.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.zayaanit.mm.entity.BaseEntity;
import com.zayaanit.mm.entity.User;

/**
 * @author Zubayer Ahamed
 * @sincce Sep 27, 2022
 */
@NoRepositoryBean
public interface ServiceRepository<E extends BaseEntity> extends JpaRepository<E, Long> {

	Optional<E> findByIdAndUser(Long id, User user);

	List<E> findAllByUser(User user);
}
