package net.client;

import interfaces.Building;
import static_classes.Buildings;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SerialClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String curDir = System.getProperty("user.dir");
        String infoFile = curDir + "\\src\\buildings\\net\\client\\res\\info";
        String typeFile = curDir + "\\src\\buildings\\net\\client\\res\\type";
        String costFile = curDir + "\\src\\buildings\\net\\client\\res\\cost";

        Socket socket = new Socket("127.0.0.1", 1234);
        System.out.println("Connected to server");

        try(FileInputStream infoIn = new FileInputStream(infoFile);
            Scanner scanner = new Scanner(new FileReader(typeFile));
            PrintWriter costOut = new PrintWriter(costFile);
            ObjectInputStream serverIn = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream serverOut = new ObjectOutputStream(socket.getOutputStream());
        ) {
            while (scanner.hasNextLine()) {
                System.out.println("Reading the building type from " + typeFile);
                Character type = scanner.next().toLowerCase().charAt(0);
                System.out.println("Sending the type: " + type);
                serverOut.writeObject(type);
                System.out.println("Inputting the building from " + infoFile);
                Building building = Buildings.inputBuilding(infoIn);
                System.out.println("Sending the building: " + building.toString());
                Buildings.serializeBuilding(building, serverOut);
                System.out.println("Receiving the reply...");
                Object reply = serverIn.readObject();
                System.out.print("Received the reply: " + reply);
                costOut.println(reply);
                System.out.println("Printed the reply to " + costFile);
            }
        }
    }
}
