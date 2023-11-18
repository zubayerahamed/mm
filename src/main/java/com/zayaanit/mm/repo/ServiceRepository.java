package com.zayaanit.mm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.zayaanit.mm.entity.BaseEntity;

/**
 * @author Zubayer Ahamed
 * @sincce Sep 27, 2022
 */
@NoRepositoryBean
public interface ServiceRepository<E extends BaseEntity> extends JpaRepository<E, Long> {

	
}
