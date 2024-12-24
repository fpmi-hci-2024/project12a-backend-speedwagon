package by.speedteam.speedwagon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bus_stop")
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean isBusStation;

    public BusStop() {
    }

    public BusStop(String city, String address, boolean isBusStation) {
        this.city = city;
        this.address = address;
        this.isBusStation = isBusStation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isBusStation() {
        return isBusStation;
    }

    public void setBusStation(boolean busStation) {
        isBusStation = busStation;
    }
}
