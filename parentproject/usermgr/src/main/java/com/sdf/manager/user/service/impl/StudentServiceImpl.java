package com.sdf.manager.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.user.entity.Student;
import com.sdf.manager.user.repository.StudentRepository;
import com.sdf.manager.user.service.StudentService;
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
