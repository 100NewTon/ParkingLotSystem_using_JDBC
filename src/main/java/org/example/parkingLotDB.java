package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class parkingLotDB {
    int capacity;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public parkingLotDB(int capacity) {
        this.capacity = capacity;
        try {
            String URL = "jdbc:mysql://localhost:3306/PARKING";
            String USERNAME = "root";
            String PASSWORD = "root";
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            for (int i = 1; i <= capacity; i++) {
                preparedStatement = connection.prepareStatement("INSERT INTO parkingLot VALUES(?, NULL, TRUE)");
                preparedStatement.setInt(1, i);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean parkCar(Car car) {
        for (int i = 1; i <= capacity; i++) {
            try {
                preparedStatement = connection.prepareStatement("SELECT available FROM parkingLot WHERE spotNumber = ?");
                preparedStatement.setInt(1, i);
                resultSet = preparedStatement.executeQuery();
                boolean avail = false;
                if (resultSet.next()) {
                    avail = resultSet.getBoolean("available");
                }
                if (avail) {
                    preparedStatement = connection.prepareStatement("UPDATE parkingLot SET licenseNo = ?, available = FALSE WHERE spotNumber = ?");
                    preparedStatement.setString(1, car.getLicensePlateNo());
                    preparedStatement.setInt(2, i);
                    preparedStatement.executeUpdate();
                    System.out.println("Car with License Plate No " + car.getLicensePlateNo() + " is parked at Spot Number " + i);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("No spot available...");
        return false;
    }

    public boolean removeCar(String licenseNo) {
        try {
            preparedStatement = connection.prepareStatement("SELECT spotNumber FROM parkingLot WHERE licenseNo = ?");
            preparedStatement.setString(1, licenseNo);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int sNo = resultSet.getInt("spotNumber");
                preparedStatement = connection.prepareStatement("UPDATE parkingLot SET licenseNo = NULL, available = TRUE WHERE spotNumber = ?");
                preparedStatement.setInt(1, sNo);
                preparedStatement.executeUpdate();
                System.out.println("Car with License Plate No " + licenseNo + " has been removed from Spot Number " + sNo);
                return true;
            } else {
                System.out.println("Car not found...");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
class Test1{
    public static void main(String[] args) {
        parkingLotDB parkingLot=new parkingLotDB(6);
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
