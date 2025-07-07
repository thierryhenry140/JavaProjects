package com.example.lesson42_thymeleaf.controller;


import com.example.lesson42_thymeleaf.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();
    private int nextId = 1;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", students);
        return "list";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add";
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam String name) {
        students.add(new Student(nextId++, name));
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        students.removeIf(student -> student.getId() == id);
        return "redirect:/students";
    }
}