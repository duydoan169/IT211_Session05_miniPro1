
package com.example.coursecatalog.service;

import com.example.coursecatalog.dto.request.CourseRequestDTO;
import com.example.coursecatalog.dto.response.CourseResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CourseService {

    Page<CourseResponseDTO> getAllCourses(int page, int size, String sort);

    CourseResponseDTO getCourseById(Long id);

    CourseResponseDTO createCourse(CourseRequestDTO request);

    CourseResponseDTO updateCourse(Long id, CourseRequestDTO request);

    CourseResponseDTO patchCourse(Long id, Map<String, Object> updates);

    void deleteCourse(Long id);

    CourseResponseDTO uploadImage(Long id, String imageUrl);

    void deleteImage(Long id);
}
