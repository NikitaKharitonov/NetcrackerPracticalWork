package static_classes;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import dwelling.SynchronizedFloor;
import factories.BuildingFactory;
import factories.DwellingFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Scanner;

public class Buildings {
    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void setBuildingFactory(BuildingFactory bf) {
        buildingFactory = bf;
    }

    public static Space createSpace(double area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(Class<? extends Space> tClass, double area) {
        try {
            return tClass.getConstructor(double.class).newInstance(area);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Space createSpace(double area, int roomsCount) {
        return buildingFactory.createSpace(area, roomsCount);
    }

    public static Space createSpace(Class<? extends Space> tClass, double area, int roomsCount) {
        try {
            return tClass.getConstructor(double.class, int.class).newInstance(area, roomsCount);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Floor createFloor(Class<? extends Floor> tClass, int spacesCount) {
        try {
            return tClass.getConstructor(int.class).newInstance(spacesCount);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Floor createFloor(Space... spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Floor createFloor(Class<? extends Floor> tClass, Space... spaces) {
        try {
            return tClass.getConstructor(Space[].class).newInstance(spaces);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Building createBuilding(int floorsCount, int... spacesCounts) {
        return buildingFactory.createBuilding(floorsCount, spacesCounts);
    }

    public static Building createBuilding(Class<? extends Building> tClass, int floorsCount, int... spacesCounts) {
        try {
            return tClass.getConstructor(int.class, int[].class).newInstance(floorsCount, spacesCounts);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static Building createBuilding(Class<? extends Building> tClass, Floor... floors) {
        try {
            return tClass.getConstructor(Floor[].class).newInstance(floors);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void outputBuilding (Building building, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeInt(building.getFloorCount());
        for (int i = 0; i < building.getFloorCount(); ++i) {
            Floor floor = building.getFloorByIndex(i);
            dataOutputStream.writeInt(floor.getSpaceCount());
            for (int j = 0; j < floor.getSpaceCount(); ++j) {
                Space space = floor.getSpaceByIndex(j);
                dataOutputStream.writeDouble(space.getArea());
                dataOutputStream.writeInt(space.getRoomCount());
            }
        }
    }

    public static Building inputBuilding (InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int floorCount = dataInputStream.readInt();
        Floor[] floors = new Floor[floorCount];
        for (int i = 0; i < floorCount; ++i) {
            int spaceCount = dataInputStream.readInt();
            Space[] spaces = new Space[spaceCount];
            for (int j = 0; j < spaceCount; ++j)
                spaces[j] = createSpace(dataInputStream.readDouble(), dataInputStream.readInt());
            floors[i] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

    public static Building inputBuilding (InputStream in, Class<? extends Building> buildingClass, Class<? extends Floor> floorClass, Class<? extends Space> spaceClass) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int floorCount = dataInputStream.readInt();
        Floor[] floors = new Floor[floorCount];
        for (int i = 0; i < floorCount; ++i) {
            int spaceCount = dataInputStream.readInt();
            Space[] spaces = new Space[spaceCount];
            for (int j = 0; j < spaceCount; ++j)
                spaces[j] = createSpace(spaceClass, dataInputStream.readDouble(), dataInputStream.readInt());
            floors[i] = createFloor(floorClass, spaces);
        }
        return createBuilding(buildingClass, floors);
    }

    public static void writeBuilding (Building building, Writer out) {
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.write(building.getFloorCount() + " ");
        for(int i = 0; i < building.getFloorCount(); ++i) {
            Floor floor = building.getFloorByIndex(i);
            printWriter.write(floor.getSpaceCount() + " ");
            for(int j = 0; j < floor.getSpaceCount(); ++j) {
                Space space = floor.getSpaceByIndex(j);
                printWriter.write(space.getArea() + " " + space.getRoomCount() + " ");
            }
        }
    }

    public static Building readBuilding (Reader in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        String string = bufferedReader.readLine();
        String[] tokens = string.split(" ");
        int tokenIndex = 0;
        int floorCount = Integer.parseInt(tokens[tokenIndex++]);
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; ++i) {
            int spaceCount = Integer.parseInt(tokens[tokenIndex++]);
            Space[] spaces = new Space[spaceCount];
            for (int j = 0; j < spaceCount; ++j)
                spaces[j] = createSpace(Double.parseDouble(tokens[tokenIndex++]), Integer.parseInt(tokens[tokenIndex++]));
            floors[i] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

    public static Building readBuilding (Reader in, Class<? extends Building> buildingClass, Class<? extends Floor> floorClass, Class<? extends Space> spaceClass) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        String string = bufferedReader.readLine();
        String[] tokens = string.split(" ");
        int tokenIndex = 0;
        int floorCount = Integer.parseInt(tokens[tokenIndex++]);
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; ++i) {
            int spaceCount = Integer.parseInt(tokens[tokenIndex++]);
            Space[] spaces = new Space[spaceCount];
            for (int j = 0; j < spaceCount; ++j)
                spaces[j] = createSpace(spaceClass, Double.parseDouble(tokens[tokenIndex++]), Integer.parseInt(tokens[tokenIndex++]));
            floors[i] = createFloor(floorClass, spaces);
        }
        return createBuilding(buildingClass, floors);
    }

    public static void serializeBuilding (Building building, OutputStream out) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(building);
    }

    public static Building deserializeBuilding (InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        Object object = objectInputStream.readObject();
        return (Building)object;
    }

    public static void writeBuildingFormat (Building building, Writer out) {
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.printf("%d ", building.getFloorCount());
        for(int i = 0; i < building.getFloorCount(); ++i) {
            Floor floor = building.getFloorByIndex(i);
            printWriter.printf("%d ", floor.getSpaceCount());
            for(int j = 0; j < floor.getSpaceCount(); ++j) {
                Space space = floor.getSpaceByIndex(j);
                printWriter.printf("%f %d ", space.getArea(), space.getRoomCount());
            }
        }
    }

    public static Building readBuilding (Scanner scanner) {
        int floorCount = scanner.nextInt();
        Floor[] floors = new Floor[floorCount];
        for (int i = 0; i < floorCount; ++i) {
            int spaceCount = scanner.nextInt();
            Space[] spaces = new Space[spaceCount];
            for (int j = 0; j < spaceCount; ++j)
                spaces[j] = createSpace(scanner.nextDouble(), scanner.nextInt());
            floors[i] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

    public static <T extends Comparable<T>> void sortByAscending(T[] ts) {
        boolean isChange;
        do {
            isChange = false;

            for (int i = 1; i < ts.length; ++i) {
                if (ts[i - 1].compareTo(ts[i]) > 0) {
                    T change = ts[i - 1];
                    ts[i - 1] = ts[i];
                    ts[i] = change;
                    isChange = true;
                }
            }
        } while (isChange);
    }

    public static <T> void sortByDescending(T[] ts, Comparator<T> comparator) {

        boolean isChange;
        do {
            isChange = false;

            for (int i = 1; i < ts.length; ++i) {
                if (comparator.compare(ts[i - 1], ts[i]) > 0) {
                    T change = ts[i - 1];
                    ts[i - 1] = ts[i];
                    ts[i] = change;
                    isChange = true;
                }
            }
        } while (isChange);
    }

    public static <T> void sort(T[] ts, java.util.function.BiFunction<T, T, Double> compare) {

        boolean isChange;
        do {
            isChange = false;

            for (int i = 1; i < ts.length; ++i) {
                if (compare.apply(ts[i - 1], ts[i]) > 0) {
                    T change = ts[i - 1];
                    ts[i - 1] = ts[i];
                    ts[i] = change;
                    isChange = true;
                }
            }
        } while (isChange);
    }

    public static Floor synchronizedFloor (Floor floor) {
        return new SynchronizedFloor(floor.getSpaces());
    }



}
