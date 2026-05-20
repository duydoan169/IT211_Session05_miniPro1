
package com.example.coursecatalog.repository;

import com.example.coursecatalog.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
