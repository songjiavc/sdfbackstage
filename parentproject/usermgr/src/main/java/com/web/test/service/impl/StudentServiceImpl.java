package com.web.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.test.entity.Student;
import com.web.test.repository.StudentRepository;
import com.web.test.service.StudentService;
/**
 * 
 * @author xuzebin
 *
 */
@Service("studentService")
@Transactional(propagation=Propagation.REQUIRED)
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public void add(Student student) {
		studentRepository.save(student);
	}

}
