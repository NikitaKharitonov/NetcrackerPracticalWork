package net.client;

import interfaces.Building;
import static_classes.Buildings;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BinaryClient {
    public static void main(String[] args) throws IOException {
        String curDir = System.getProperty("user.dir");
        String infoFile = curDir + "\\src\\buildings\\net\\client\\res\\info";
        String typeFile = curDir + "\\src\\buildings\\net\\client\\res\\type";
        String costFile = curDir + "\\src\\buildings\\net\\client\\res\\cost";

        Socket socket = new Socket("127.0.0.1", 1234);
        System.out.println("Connected to server");

        try(FileInputStream infoIn = new FileInputStream(infoFile);
            Scanner scanner = new Scanner(new FileReader(typeFile));
            PrintWriter costOut = new PrintWriter(costFile);
            DataInputStream serverIn = new DataInputStream(socket.getInputStream());
            DataOutputStream serverOut = new DataOutputStream(socket.getOutputStream())
        ) {
            while (scanner.hasNextLine()) {
                System.out.println("Reading the building type from " + typeFile);
                char type = scanner.next().toLowerCase().charAt(0);
                System.out.println("Sending the type: " + type);
                serverOut.writeChar(type);
                System.out.println("Inputting the building from " + infoFile);
                Building building = Buildings.inputBuilding(infoIn);
                System.out.println("Sending the building: " + building.toString());
                Buildings.outputBuilding(building, serverOut);
                System.out.println("Receiving the reply...");
                double number = serverIn.readDouble();
                System.out.println("Received the reply: " + number);
                System.out.println("Printing the reply to " + costFile);
                if (number == -1)
                    costOut.println("The building is under arrest");
                else costOut.println(number);
            }
        }
    }
}
