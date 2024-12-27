package by.speedteam.speedwagon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private BusStop startPoint;

    @ManyToOne
    @JoinColumn(nullable = false)
    private BusStop destinationPoint;

    @Column(nullable = false)
    private String listOfStopsId;

    public Route() {
    }

    public Route(BusStop startPoint, BusStop destinationPoint, String listOfStopsId) {
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.listOfStopsId = listOfStopsId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BusStop getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(BusStop startPoint) {
        this.startPoint = startPoint;
    }

    public BusStop getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(BusStop destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public String getListOfStopsId() {
        return listOfStopsId;
    }

    public void setListOfStopsId(String listOfStopsId) {
        this.listOfStopsId = listOfStopsId;
    }
}
