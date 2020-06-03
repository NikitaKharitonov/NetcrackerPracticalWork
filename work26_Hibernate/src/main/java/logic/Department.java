package logic;

import java.util.HashSet;
import java.util.Set;

public class Department {
    private Integer departmentID;
    private String name;
    private Set<Employee> emps = new HashSet<>();

    public Department() {
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmps() {
        return emps;
    }

    public void setEmps(Set<Employee> emps) {
        this.emps = emps;
    }
}
