import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class XMLFileChecker {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input file: ");

        if (scanner.hasNext()) {
            String inputFileName = scanner.next();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document inputDocument = documentBuilder.parse(inputFileName);
            Element element = inputDocument.getDocumentElement();
            NodeList studentNodeList = element.getElementsByTagName("student");
            ArrayList<Student> studentArrayList = new ArrayList<>();

            for (int i = 0; i < studentNodeList.getLength(); ++i) {
                Element studentNode = (Element)studentNodeList.item(i);
                String firstname = studentNode.getAttributes().getNamedItem("firstname").getNodeValue();
                String lastname = studentNode.getAttributes().getNamedItem("lastname").getNodeValue();
                String groupnumber = studentNode.getAttributes().getNamedItem("groupnumber").getNodeValue();
                NodeList subjectsNodeList = studentNode.getElementsByTagName("subject");
                ArrayList<Student.Subject> subjectArrayList = new ArrayList<>();

                for (int j = 0; j < subjectsNodeList.getLength(); ++j) {
                    Node subjectNode = subjectsNodeList.item(j);
                    String title = subjectNode.getAttributes().getNamedItem("title").getNodeValue();
                    String mark = subjectNode.getAttributes().getNamedItem("mark").getNodeValue();
                    Student.Subject subject = new Student.Subject(title, mark);
                    subjectArrayList.add(subject);
                }

                Student student = new Student(firstname, lastname, groupnumber, subjectArrayList);
                studentArrayList.add(student);
            }

            System.out.print("Output file: ");
            if(scanner.hasNext()) {
                String outputFileName = scanner.next();
                Document document = documentBuilder.newDocument();
                Element root = document.createElement("group");
                document.appendChild(root);

                for (Student student : studentArrayList) {
                    Element studentElement = document.createElement("student");
                    root.appendChild(studentElement);
                    Attr firstnameAttr = document.createAttribute("firstname");
                    firstnameAttr.setValue(student.getFirstName());
                    studentElement.setAttributeNode(firstnameAttr);
                    Attr lastnameAttr = document.createAttribute("lastname");
                    lastnameAttr.setValue(student.getLastName());
                    studentElement.setAttributeNode(lastnameAttr);
                    Attr groupnameAttr = document.createAttribute("groupname");
                    groupnameAttr.setValue(student.getGroupNumber());
                    studentElement.setAttributeNode(groupnameAttr);
                    ArrayList<Student.Subject> subjectArrayList = student.getSubjects();

                    for (Student.Subject subject : subjectArrayList) {
                        Element subjectElement = document.createElement("subject");
                        Attr titleAttr = document.createAttribute("title");
                        titleAttr.setValue(subject.getTitle());
                        subjectElement.setAttributeNode(titleAttr);
                        Attr markAttr = document.createAttribute("mark");
                        markAttr.setValue(subject.getMark());
                        subjectElement.setAttributeNode(markAttr);
                        studentElement.appendChild(subjectElement);
                    }

                    Element average = document.createElement("average");
                    average.setTextContent(Double.toString(student.getAverage()));
                    studentElement.appendChild(average);
                    root.appendChild(studentElement);
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(outputFileName));
                transformer.transform(domSource, streamResult);
            }
        }
    }
}
