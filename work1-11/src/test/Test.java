package test;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import dwelling.Dwelling;
import dwelling.DwellingFloor;
import dwelling.Flat;
import gui.Window;
import static_classes.Buildings;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        //Window window = new Window();
        //window.setVisible(true);

        //String curDir = System.getProperty("user.dir");
        //System.out.println(curDir);




        Floor[] floors = new Floor[16];
        for (int i = 0; i < 16; ++i)
            floors[i] = new DwellingFloor(i + 1);

        Building building = new Dwelling(floors);
        for (Floor floor: floors)
            System.out.println(floor.toString());

        Buildings.sort(floors, (Floor floor1, Floor floor2) -> ((double)floor2.getSpaceCount() - floor1.getSpaceCount()));

        System.out.println("\nSorted: ");
        for (Floor floor: floors)
            System.out.println(floor.toString());

        Buildings.sort(floors, (Floor floor1, Floor floor2) -> ((double)floor1.getSpaceCount() - floor2.getSpaceCount()));

        System.out.println("\nSorted: ");
        for (Floor floor: floors)
            System.out.println(floor.toString());

        System.out.println("\n\n");







        Space[] spaces = new Space[16];
        for (int i = 0; i < 16; ++i)
            spaces[i] = new Flat(i + 1);

        for (Space space: spaces)
            System.out.println(space.toString());

        Buildings.sort(spaces, (Space space1, Space space2) -> (space2.getArea() - space1.getArea()));

        System.out.println("\nSorted: ");
        for (Space space: spaces)
            System.out.println(space.toString());

        Buildings.sort(spaces, (Space space1, Space space2) -> (space1.getArea() - space2.getArea()));

        System.out.println("\nSorted: ");
        for (Space space: spaces)
            System.out.println(space.toString());


/*

      Dwelling dwelling = new Dwelling(new DwellingFloor(new Flat(10, 1), new Flat(20, 2)), new DwellingFloor(new Flat(30, 3), new Flat(40, 4)));
        FileWriter writer = new FileWriter("C:\\Users\\Никита\\IdeaProjects\\Netcracker\\PracticalWorks\\NetcrackerPracticalWork\\res\\res");
        Buildings.writeBuilding(dwelling, writer);
        writer.close();


        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("C:\\Users\\Никита\\IdeaProjects\\Netcracker\\PracticalWorks\\PracticalWork\\src\\buildings\\net\\client\\type"));
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Никита\\IdeaProjects\\Netcracker\\PracticalWorks\\PracticalWork\\src\\buildings\\net\\client\\res");
        for (int i = 0; i < 1; ++i) {
            Dwelling dwelling = new Dwelling(new DwellingFloor(1), new DwellingFloor(i + 1));
            dataOutputStream.writeChar('o');
            Buildings.serializeBuilding(dwelling, fileOutputStream);

        }*/








    }
}
