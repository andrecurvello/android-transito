package com.fincatto.osm.classes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PointType {

    PARDAL(1, Arrays.asList(20, 30, 40, 50, 60, 80, 90, 100, 110)),
    LOMBADA(2, Arrays.asList(20, 30, 40, 50, 60, 80, 90, 100, 110)),
    PRF(3, Arrays.asList(40));

    private final int code;
    private final List<Integer> speedOptions;

    private PointType(final int code, final List<Integer> speedOptions) {
        this.code = code;
        this.speedOptions = speedOptions;
    }

    public int getCode() {
        return code;
    }

    public List<Integer> getSpeedOptions() {
        return Collections.unmodifiableList(speedOptions);
    }
}