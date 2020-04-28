package gui;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import dwelling.Dwelling;
import dwelling.DwellingFloor;
import dwelling.Flat;
import factories.BuildingFactory;
import factories.DwellingFactory;
import factories.OfficeFactory;
import static_classes.Buildings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class Window extends JFrame {

    private JLabel buildingInfo = new JLabel();
    private JLabel floorInfo = new JLabel();
    private JLabel spaceInfo = new JLabel();
    private JPanel buildingPlan = new JPanel();
    private Building building;
    private int selectedSpaceIndex = 0;
    private String lookAndFeel;

    private void writeInfo() {
        int floorIndex = 0;
        int spaceIndex = this.selectedSpaceIndex;
        for (int i = 0; i < building.getFloorCount(); ++i) {
            if (building.getFloorByIndex(i).getSpaceCount() <= spaceIndex) {
                spaceIndex -= building.getFloorByIndex(i).getSpaceCount();
                ++floorIndex;
            }
            else break;
        }
        buildingInfo.setText(building.getClass().getSimpleName() + " floors: " + building.getFloorCount() + " total area: " + building.getTotalSpaceArea());
        Floor floor = building.getFloorByIndex(floorIndex);
        floorInfo.setText("Floor number: " + floorIndex + " spaces: " + floor.getSpaceCount() + " total area: " + floor.getTotalSpaceArea());
        Space space = building.getSpaceByIndex(selectedSpaceIndex);
        spaceInfo.setText("Space number: " + selectedSpaceIndex + " rooms: " + space.getRoomCount() + " total area: " + space.getArea());
    }

    private void readBuildingFromFile(BuildingFactory factory) {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        int returnValue = chooser.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(file);
                Buildings.setBuildingFactory(factory);
                building = Buildings.readBuilding(reader);
                reader.close();
                writeInfo();
                drawBuildingPlan();
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void drawBuildingPlan() {
        buildingPlan.removeAll();
        if (building != null) {
            buildingPlan.setLayout(new BoxLayout(buildingPlan, BoxLayout.Y_AXIS));
            int spaceNumber = 0;
            for (int i = 0; i < building.getFloorCount(); ++i) {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                for (int j = 0; j < building.getFloorByIndex(i).getSpaceCount(); ++j) {
                    JButton button = new JButton(String.valueOf(spaceNumber++));
                    button.addActionListener((ActionEvent e) -> {
                            selectedSpaceIndex = Integer.valueOf(button.getText());
                            writeInfo();
                        }
                    );
                    panel.add(button);
                }
                buildingPlan.add(panel);
            }
        }
    }

    private void initLookAndFeel(String lookAndFeelName) {
        switch (lookAndFeelName) {
            case "Metal":
                lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
                break;

            case "Nimbus":
                lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                break;

            case "CDE/Motif":
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                break;

            case "Windows":
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                break;

            case "Windows Classic":
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
        }
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public Window() {

        Floor[] floors = new Floor[8];
        for (int i = 0; i < floors.length; ++i) {
            Space[] spaces = new Space[16];
            for (int j = 0; j < spaces.length; ++j)
                spaces[j] = new Flat(32, 2);
            floors[i] = new DwellingFloor(spaces);
        }
        building = new Dwelling(floors);

        drawComponents();
    }

    private void drawComponents() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");

        JMenuItem item = new JMenuItem("Open dwelling...");
        item.addActionListener((ActionEvent e) -> readBuildingFromFile(new DwellingFactory()));
        menu.add(item);

        item = new JMenuItem("Open office building...");
        item.addActionListener((ActionEvent e) -> readBuildingFromFile(new OfficeFactory()));
        menu.add(item);

        menuBar.add(menu);

        menu = new JMenu("Look&Feel");
        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("Metal");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Metal"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Nimbus");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Nimbus"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("CDE/Motif");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("CDE/Motif"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Windows");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Windows"));
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Windows Classic");
        menuItem.addActionListener((ActionEvent e) -> initLookAndFeel("Windows Classic"));
        group.add(menuItem);
        menu.add(menuItem);

        menuBar.add(menu);

        drawBuildingPlan();

        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(menuBar);
        container.add(buildingInfo);
        container.add(floorInfo);
        container.add(spaceInfo);
        container.add(buildingPlan);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setLayout(new ScrollPaneLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(scrollPane);
        this.pack();
    }
}


