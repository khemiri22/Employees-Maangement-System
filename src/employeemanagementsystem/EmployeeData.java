package employeemanagementsystem;

import java.sql.Date;

public class EmployeeData {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String position;
    private String image;
    private Date date;

    public EmployeeData(Integer employeeId, String firstName, String lastName, String gender, String phone, String position, String image, Date date) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.position = position;
        this.image = image;
        this.date = date;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }
}
