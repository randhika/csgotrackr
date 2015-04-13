package com.example.android.cstogo;

import java.io.Serializable;

/**
 * - Yuro - 10.4.2015.
 */
public class Smoke implements Serializable{

    private String mapId;

    public Smoke(String mapId) {
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }
}
