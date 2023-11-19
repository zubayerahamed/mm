package com.zayaanit.mm.repo;

import java.util.Optional;

import com.zayaanit.mm.entity.Arhed;
import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.entity.User;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
public interface ArhedRepo extends ServiceRepository<Arhed> {

	Optional<Arhed> findByTrnHistoryAndUser(TrnHistory trnHistory, User user);
}
