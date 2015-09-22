package com.web.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.test.entity.Student;
/**
 * 
 * @author xuzebin
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
