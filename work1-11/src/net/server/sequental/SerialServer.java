package net.server.sequental;

import interfaces.Building;
import static_classes.Buildings;
import factories.DwellingFactory;
import factories.HotelFactory;
import factories.OfficeFactory;
import net.server.BuildingUnderArrestException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channel;
import java.util.Random;

public class SerialServer {
    private static double calculateCost(char type, Building building) throws BuildingUnderArrestException {
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

    private static boolean isArrested() {
        Random random = new Random();
        int number = random.nextInt(10);
        return number == 0;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();
        System.out.println("Connected to client");
        try (
            ObjectInputStream clientIn = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream clientOut = new ObjectOutputStream(socket.getOutputStream())
        ) {
            boolean end = false;
            while (!end) {
                try {
                    System.out.println("Receiving the building type...");
                    Character type = (Character) clientIn.readObject();
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
                } catch (IOException e) {
                    end = true;
                } catch (BuildingUnderArrestException e) {
                    clientOut.writeObject(e);
                    System.out.println("Sent the exception message");
                }
            }
        }
    }
}
