package com.fincatto.osm.classes;

public class Point {

    private Long id;
    private PointType type;
    private Integer speed;
    private Double latitude;
    private Double longitude;

    public Point() {
        this.id = null;
        this.type = null;
        this.speed = null;
        this.latitude = null;
        this.longitude = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointType getType() {
        return type;
    }

    public void setType(PointType type) {
        this.type = type;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;
        if (id != null ? !id.equals(point.id) : point.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("Point{id=%s, latitude=%s, longitude=%s, type=%s, speed=%s}", id, latitude, longitude, type, speed);
    }
}
