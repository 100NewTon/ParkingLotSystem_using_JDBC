package org.example;

import java.util.ArrayList;
import java.util.List;
//import java.util.Collection;

public class parkingLot {
    int capacity;
    List<parkingSpot> ps;
    public parkingLot(int capacity){
        this.capacity = capacity;
        this.ps = new ArrayList<>();
        for(int i = 1; i <= capacity; i++) {
            ps.add(new parkingSpot(i));
        }
    }
    public boolean parkCar(Car car) {
        for(parkingSpot spot : ps) {
            if(spot.isAvailable()) {
                spot.occupy(car);
                System.out.println("Car with License No " + car.getLicensePlateNo() + " is parked at spot number " + spot.getSpotNumber());
                return true;
            }
        }
        System.out.println("No spot available...");
        return false;
    }
    public boolean removeCar(String LicenseNo){
        for(parkingSpot spot: ps){
            if(!spot.isAvailable() && spot.getCar().getLicensePlateNo().equalsIgnoreCase(LicenseNo)){
                spot.vacate();
                System.out.println("Car with License No " + LicenseNo + " is being moved out from spot number " +spot.getSpotNumber());
                return true;
            }
        }
        System.out.println("Car Not Found...");
        return false;
    }
}
class Test{
    public static void main(String[] args) {
        parkingLot parkingLot=new parkingLot(5);
        Car car1=new Car("UP807673");
        Car car2=new Car("DL837273");
        Car car3=new Car("MP841732");
        Car car4=new Car("MP8414732");
        Car car5=new Car("MP841882");
        Car car6=new Car("MP8415442");

        parkingLot.parkCar(car1);
        parkingLot.parkCar(car2);
        parkingLot.parkCar(car3);
        parkingLot.parkCar(car4);
        parkingLot.parkCar(car5);


        parkingLot.removeCar("UP807673");

        parkingLot.parkCar(car6);
    }
}
