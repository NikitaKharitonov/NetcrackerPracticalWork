import java.util.ArrayList;

public class Student {

    public static class Subject {
        private String title;
        private String mark;

        public Subject(String title, String mark) {
            this.title = title;
            this.mark = mark;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }
    }

    private String firstName;
    private String lastName;
    private String groupNumber;
    private ArrayList<Subject> subjects;

    public Student(String firstName, String lastName, String groupNumber, ArrayList<Subject> subjects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNumber = groupNumber;
        this.subjects = subjects;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public double getAverage() {
        int average = 0;

        for (Subject subject: subjects) {
            average += Integer.parseInt(subject.getMark());
        }

        return (double) average / subjects.size();
    }
}
