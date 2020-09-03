package com.cm.datalog;

import com.cm.datalog.entity.CarInfo;
import com.cm.datalog.entity.ClientInfo;
import com.cm.datalog.entity.LocationInfo;

import java.io.Serializable;

public abstract class BaseLog implements Serializable {

    private String ver;				// Version of log
    private ClientInfo client;      // Person, Device (Who)
    private CarInfo car;            // Car (Who)
    private LocationInfo location;  // Location (Where)
    private String tracingId;		// HTTP Session ID
    private String timestamp;       // Request time (When)
    private Object extra;           // For reservation

    public BaseLog() {}

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public ClientInfo getClient() {
        return client;
    }

    public void setClient(ClientInfo client) {
        this.client = client;
    }

    public CarInfo getCar() {
        return car;
    }

    public void setCar(CarInfo car) {
        this.car = car;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public String getTracingId() {
        return tracingId;
    }

    public void setTracingId(String tracingId) {
        this.tracingId = tracingId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    BaseLog(Builder<?> builder) {
        this.ver = builder.ver;
        this.client = builder.client;
        this.car = builder.car;
        this.location = builder.location;
        this.tracingId = builder.tracingId;
        this.timestamp = builder.timestamp;
        this.extra = builder.extra;
    }

    public abstract static class Builder<T extends Builder<T>> {
        private String ver;
        private ClientInfo client;
        private CarInfo car;
        private LocationInfo location;
        private String tracingId;
        private String timestamp;
        private Object extra;

        public T ver(String ver) {
            this.ver = ver;
            return self();
        }
        public T id(String id) {
            if (id != null) {
                createClientIfNecessary();
                this.client.setId(id);
            }
            return self();
        }
        public T userId(String userId) {
            if (userId != null) {
                createClientIfNecessary();
                this.client.setUserId(userId);
            }
            return self();
        }
        public T daimlerId(String userId) {
            if (userId != null) {
                createClientIfNecessary();
                this.client.setDaimlerId(userId);
            }
            return self();
        }
        public T deviceId(String deviceId) {
            if (deviceId != null) {
                createClientIfNecessary();
                this.client.setDeviceId(deviceId);
            }
            return self();
        }
        public T deviceType(String deviceType) {
            if (deviceType != null) {
                createClientIfNecessary();
                this.client.setDeviceType(deviceType);
            }
            return self();
        }
        public T deviceSystem(String deviceSystem) {
            if (deviceSystem != null) {
                createClientIfNecessary();
                this.client.setDeviceSystem(deviceSystem);
            }
            return self();
        }
        public T carID(String id) {
            if (id != null) {
                createCarIfNecessary();
                this.car.setId(id);
            }
            return self();
        }
        public T carVIN(String vin) {
            if (vin != null) {
                createCarIfNecessary();
                this.car.setVin(vin);
            }
            return self();
        }
        public T carModel(String model) {
            if (model != null) {
                createCarIfNecessary();
                this.car.setModel(model);
            }
            return self();
        }
        public T carPlateNo(String plateNo) {
            if (plateNo != null) {
                createCarIfNecessary();
                this.car.setPlateNo(plateNo);
            }
            return self();
        }
        public T latitude(Double latitude) {
            if (latitude != null) {
                createLocationIfNecessary();
                this.location.setLatitude(latitude);
            }
            return self();
        }
        public T longitude(Double longitude) {
            if (longitude != null) {
                createLocationIfNecessary();
                this.location.setLongitude(longitude);
            }
            return self();
        }
        public T coordinateType(String coordinateType) {
            if (coordinateType != null) {
                createLocationIfNecessary();
                this.location.setCoordinateType(coordinateType);
            }
            return self();
        }
        public T country(String country) {
            if (country != null) {
                createLocationIfNecessary();
                this.location.setCountry(country);
            }
            return self();
        }
        public T province(String province) {
            if (province != null) {
                createLocationIfNecessary();
                this.location.setProvince(province);
            }
            return self();
        }
        public T city(String city) {
            if (city != null) {
                createLocationIfNecessary();
                this.location.setCity(city);
            }
            return self();
        }
        public T district(String district) {
            if (district != null) {
                createLocationIfNecessary();
                this.location.setDistrict(district);
            }
            return self();
        }
        public T street(String street) {
            if (street != null) {
                createLocationIfNecessary();
                this.location.setStreet(street);
            }
            return self();
        }
        public T tracingId(String tracingId) {
            this.tracingId = tracingId;
            return self();
        }
        public T timestamp(String timestamp) {
            this.timestamp = timestamp;
            return self();
        }
        public T extra(Object extra) {
            this.extra = extra;
            return self();
        }

        public abstract BaseLog build();
        protected abstract T self();

        private void createClientIfNecessary() {
            if (this.client == null) {
                this.client = new ClientInfo();
            }
        }
        private void createCarIfNecessary() {
            if (this.car == null) {
                this.car = new CarInfo();
            }
        }
        private void createLocationIfNecessary() {
            if (this.location == null) {
                this.location = new LocationInfo();
            }
        }
    }
}
