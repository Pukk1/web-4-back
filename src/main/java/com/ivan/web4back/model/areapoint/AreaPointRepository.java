package com.ivan.web4back.model.areapoint;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaPointRepository extends CrudRepository<AreaPointEntity, Long> {
    @Modifying
    @Query(value = "delete from area_points a where a.owner_id=:ownerId", nativeQuery = true)
    void deleteAllByOwnerId(@Param("ownerId") Long ownerId);
}
