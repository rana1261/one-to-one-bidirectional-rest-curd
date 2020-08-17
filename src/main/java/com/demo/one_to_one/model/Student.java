package com.demo.one_to_one.model;



import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "student")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 65)
    @Column(name = "Student_name")
    private String studentName;

    @NotNull
    @Size(max = 65)
    @Column(name = "Student_mark")
    private int studentMark;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
    private Laptop laptop;

    public Student() {

    }

    public Student(String studentName, int studentMark) {
        this.studentName = studentName;
        this.studentMark = studentMark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String SName) {
        this.studentName = SName;
    }

    public int getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(int SMark) {
        this.studentMark = SMark;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

}