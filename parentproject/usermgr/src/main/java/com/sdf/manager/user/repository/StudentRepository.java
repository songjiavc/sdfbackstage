package com.sdf.manager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdf.manager.user.entity.Student;
/**
 * 
 * @author xuzebin
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
