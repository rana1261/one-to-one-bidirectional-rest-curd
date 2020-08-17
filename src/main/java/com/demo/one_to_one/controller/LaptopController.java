package com.demo.one_to_one.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.one_to_one.exception.NotFoundException;
import com.demo.one_to_one.model.Laptop;
import com.demo.one_to_one.repository.LaptopRepository;
import com.demo.one_to_one.repository.StudentRepository;

@RestController
public class LaptopController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LaptopRepository laptopRepository;

    /*add only a Laptop and not adding any student data*/
    @PostMapping(value = "students/{sudentId}/laptops")
    public Laptop saveLaptop(@PathVariable Long sudentId, @Valid @RequestBody Laptop laptop) {
        return studentRepository.findById(sudentId)
                .map(student -> {
                    laptop.setStudent(student);
                    return laptopRepository.save(laptop);
                }).orElseThrow(() -> new NotFoundException("Student not found"));
    }


    /*get all laptop and shhowing also it student data */
    @GetMapping(value = "laptops")
    public List<Laptop> getAllLaptop() {
        return laptopRepository.findAll();
    }


    /*get a laptop by Student’s ID and shhowing also it student data */
    @GetMapping("/students/{studentId}/laptops")
    public Laptop getLaptopByStudentId(@PathVariable Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }

        List<Laptop> laptops = laptopRepository.findByStudentId(studentId);
        if (laptops.size() > 0) {
            return laptops.get(0);
        } else {
            throw new NotFoundException("Laptop not found!");
        }
    }

    /*
     * @GetMapping(value="student/{id}/laptops") public Laptop
     * getLaptop(@PathVariable Long id) { return laptopRepository.getOne(id); }
     */




    /*delete a Laptop table row by ID but not delete student table row*/
    @DeleteMapping("laptops/{laptopId}")
    public String deleteContact(@PathVariable Long laptopId) {
        return laptopRepository.findById(laptopId)
                .map(laptop -> {
                    laptopRepository.delete(laptop);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Laptop not found!"));
    }

    /*
     * // Without student table row, Delete only Laptop row
     *
     * @DeleteMapping(value="laptops/{id}")
     * public void delete(@PathVariable Long id) {
     * laptopRepository.deleteById(id);
     *  }
     */



    /*
     * // Delete both sutdent and laptop relationship table row
     * @DeleteMapping(value="students/{id}/laptops")
     * public void delete(@PathVariable Long id) {
     * studentRepository.deleteById(id);
     *  }
     */


    /*update only a laptop and not update student data */
    @PutMapping("/laptops/{laptopId}")
    public Laptop updateContact(@PathVariable Long laptopId,
                                @Valid @RequestBody Laptop laptopUpdated) {
        return laptopRepository.findById(laptopId)
                .map(laptop -> {
                    laptop.setLaptopName(laptopUpdated.getLaptopName());
                    return laptopRepository.save(laptop);
                }).orElseThrow(() -> new NotFoundException("Laptop not found!"));
    }

    /*
     * @PutMapping(value="laptops/update") public void updateStudent(@RequestBody
     * Laptop laptop) { Laptop lap=laptopRepository.getOne(laptop.getLid());
     * lap.setlName(laptop.getlName()); laptopRepository.save(lap); }
     */
}
