package com.web.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.test.entity.Student;
import com.web.test.service.StudentService;


/**
 * 
 * @author xuzebin
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
	private StudentService studentService;
	@RequestMapping("/show")
	public String show(){
		System.out.println("hello world");
		Student student=new Student();
		student.setName("hhh");
		student.setNumber(123);
		studentService.add(student);
		return "test";
	}

	
}
