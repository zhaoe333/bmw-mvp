package com.cm.datalog.entity;

public class LocationInfo {

    private Double latitude;
    private Double longitude;
    private String coordinateType;  // "Baidu", "GCD" etc.
    private String country;
    private String province;
    private String city;
    private String district;
    private String street;
    private Object extra;

    public LocationInfo() {
    }

    public LocationInfo(double latitude, double longitude, String coordinateType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.coordinateType = coordinateType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(String coordinateType) {
        this.coordinateType = coordinateType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
