
package za.ac.cput;

import java.io.Serializable;


public class Student implements Serializable{
    private String StudentName;
    private String StudentNumber;
    private double Score;

    public Student(String StudentName, String StudentNumber, double Score) {
        this.StudentName = StudentName;
        this.StudentNumber = StudentNumber;
        this.Score = Score;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String StudentNumber) {
        this.StudentNumber = StudentNumber;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double Score) {
        this.Score = Score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student{");
        sb.append("StudentName=").append(StudentName);
        sb.append(", StudentNumber=").append(StudentNumber);
        sb.append(", Score=").append(Score);
        sb.append('}');
        return sb.toString();
    }
    
    
}
