package com.ivan.web4back.model.areapoint;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaPointRepository extends CrudRepository<AreaPointEntity, Long> {
}
