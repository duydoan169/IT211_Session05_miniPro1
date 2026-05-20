
package com.example.coursecatalog.service.impl;

import com.example.coursecatalog.dto.request.CourseRequestDTO;
import com.example.coursecatalog.dto.response.CourseResponseDTO;
import com.example.coursecatalog.entity.Course;
import com.example.coursecatalog.exception.ResourceNotFoundException;
import com.example.coursecatalog.repository.CourseRepository;
import com.example.coursecatalog.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    // Lấy danh sách khóa học có phân trang
    @Override
    public Page<CourseResponseDTO> getAllCourses(int page, int size, String sort) {

        String[] sortData = sort.split(",");

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortData[1]), sortData[0])
        );

        return courseRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // Lấy khóa học theo ID
    @Override
    public CourseResponseDTO getCourseById(Long id) {

        Course course = findCourseById(id);

        return mapToResponse(course);
    }

    // Thêm khóa học mới
    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO request) {

        Course course = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        return mapToResponse(courseRepository.save(course));
    }

    // Cập nhật toàn bộ khóa học
    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request) {

        Course course = findCourseById(id);

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        return mapToResponse(courseRepository.save(course));
    }

    // Cập nhật một phần dữ liệu
    @Override
    public CourseResponseDTO patchCourse(Long id, Map<String, Object> updates) {

        Course course = findCourseById(id);

        if (updates.containsKey("name")) {
            course.setName((String) updates.get("name"));
        }

        if (updates.containsKey("description")) {
            course.setDescription((String) updates.get("description"));
        }

        if (updates.containsKey("price")) {
            course.setPrice(Double.valueOf(updates.get("price").toString()));
        }

        return mapToResponse(courseRepository.save(course));
    }

    // Xóa khóa học
    @Override
    public void deleteCourse(Long id) {

        Course course = findCourseById(id);

        courseRepository.delete(course);
    }

    // Upload ảnh cho khóa học
    @Override
    public CourseResponseDTO uploadImage(Long id, String imageUrl) {

        Course course = findCourseById(id);

        course.setImageUrl(imageUrl);

        return mapToResponse(courseRepository.save(course));
    }

    // Xóa ảnh khóa học
    @Override
    public void deleteImage(Long id) {

        Course course = findCourseById(id);

        course.setImageUrl(null);

        courseRepository.save(course);
    }

    // Hàm tìm course dùng lại nhiều lần
    private Course findCourseById(Long id) {

        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học"));
    }

    // Chuyển Entity -> Response DTO
    private CourseResponseDTO mapToResponse(Course course) {

        return CourseResponseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .price(course.getPrice())
                .imageUrl(course.getImageUrl())
                .build();
    }
}
