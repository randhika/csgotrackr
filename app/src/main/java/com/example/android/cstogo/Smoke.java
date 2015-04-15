package com.example.android.cstogo;

import java.io.Serializable;

/**
 * - Yuro - 10.4.2015.
 */
public class Smoke implements Serializable{

    private String mapId;
    private int thumbId;
    private int fullMapId;

    public Smoke(String mapId, int thumbId, int fullMapId) {
        this.mapId = mapId;
        this.thumbId = thumbId;
        this.fullMapId = fullMapId;
    }

    public String getMapId() {
        return mapId;
    }

    public int getThumbId() {
        return thumbId;
    }

    public int getFullMapId() {
        return fullMapId;
    }
}
