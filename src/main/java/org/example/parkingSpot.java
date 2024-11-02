package org.example;

public class parkingSpot {
    private int spotNumber;
    private Car car;
    private boolean Available;

    public parkingSpot(int spotNumber){
        this.car = null;
        this.spotNumber = spotNumber;
        this.Available = true;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public Car getCar() {
        return car;
    }

    public boolean isAvailable() {
        return Available;
    }
    public void vacate(){
        Available = true;
        this.car = null;
    }
    public void occupy(Car car){
        Available = false;
        this.car = car;
    }

}
