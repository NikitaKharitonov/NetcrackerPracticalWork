package net.server.parallel;

import interfaces.Building;
import static_classes.Buildings;
import factories.DwellingFactory;
import factories.HotelFactory;
import factories.OfficeFactory;
import net.server.BuildingUnderArrestException;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SerialClientThread extends Thread {
    private Socket socket;

    public SerialClientThread(Socket socket) {
        this.socket = socket;
    }

    private double calculateCost(char type, Building building) throws BuildingUnderArrestException {
        if (isArrested())
            throw new BuildingUnderArrestException();
        double totalSpaceArea = building.getTotalSpaceArea();
        if (type == 'd')
            return totalSpaceArea * 1000;
        if (type == 'o')
            return totalSpaceArea * 1500;
        if (type == 'h')
            return  totalSpaceArea * 2000;
        return 0;
    }

    private boolean isArrested() {
        Random random = new Random();
        int number = random.nextInt(10);
        return number == 0;
    }

    @Override
    public void run() {
        try (ObjectInputStream clientIn = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream clientOut = new ObjectOutputStream(socket.getOutputStream())
        ) {
            boolean end = false;
            while (!end) {
                try {
                    System.out.println("Receiving the building type...");
                    Character type = (Character)clientIn.readObject();
                    System.out.println("Received the building type: " + type);
                    if (type == 'd')
                        Buildings.setBuildingFactory(new DwellingFactory());
                    else if (type == 'o')
                        Buildings.setBuildingFactory(new OfficeFactory());
                    else if (type == 'h')
                        Buildings.setBuildingFactory(new HotelFactory());
                    System.out.println("Receiving the building...");
                    Building building = Buildings.deserializeBuilding(clientIn);
                    System.out.println("Received the building: " + building.toString());
                    double cost = calculateCost(type, building);
                    clientOut.writeObject(cost);
                    System.out.println("Sent the cost: " + cost);
                }
                catch (IOException e) {
                    end = true;
                }
                catch (BuildingUnderArrestException e) {
                    clientOut.writeObject(e);
                    System.out.println("Sent the arrest message");
                }
                catch (ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
