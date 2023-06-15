package com.ivan.web4back.utils;

import com.ivan.web4back.model.areapoint.AreaPointEntity;

import java.util.Comparator;

public class AreaPointComparator implements Comparator<AreaPointEntity> {

    @Override
    public int compare(AreaPointEntity o1, AreaPointEntity o2) {
        long id1 = (o1.getId() > Integer.MAX_VALUE) ? Integer.MAX_VALUE : o1.getId();
        long id2 = o2.getId() > Integer.MAX_VALUE ? Integer.MAX_VALUE : o2.getId();
        return (int) (id1 - id2);
    }
}
