package by.speedteam.speedwagon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bus")
public class Bus {
    @Id
    private String busNumber;

    @Column(nullable = false)
    private String driverName;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private String carFirm;

    @Column(nullable = false)
    private String color;

    public Bus() {
    }

    public Bus(String busNumber, String driverName, int totalSeats, String carFirm, String color) {
        this.busNumber = busNumber;
        this.driverName = driverName;
        this.totalSeats = totalSeats;
        this.carFirm = carFirm;
        this.color = color;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getCarFirm() {
        return carFirm;
    }

    public void setCarFirm(String carFirm) {
        this.carFirm = carFirm;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
