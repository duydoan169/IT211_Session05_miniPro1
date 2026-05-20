package com.example.coursecatalog.controller;

import com.example.coursecatalog.dto.request.CourseRequestDTO;
import com.example.coursecatalog.dto.response.CourseResponseDTO;
import com.example.coursecatalog.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.nio.file.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // API lấy danh sách khóa học
    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort
    ) {

        return ResponseEntity.ok(courseService.getAllCourses(page, size, sort));
    }

    // API lấy khóa học theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {

        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // API tạo khóa học mới
    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(
            @Valid @RequestBody CourseRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(request));
    }

    // API cập nhật toàn bộ dữ liệu
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDTO request
    ) {

        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    // API cập nhật một phần dữ liệu
    @PatchMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> patchCourse(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {

        return ResponseEntity.ok(courseService.patchCourse(id, updates));
    }

    // API xóa khóa học
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }

    // API upload ảnh
    @PostMapping("/{id}/image")
    public ResponseEntity<CourseResponseDTO> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        String uploadDir = "uploads/";

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadDir + fileName);

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return ResponseEntity.ok(
                courseService.uploadImage(id, "/" + uploadDir + fileName)
        );
    }

    // API xóa ảnh
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {

        courseService.deleteImage(id);

        return ResponseEntity.noContent().build();
    }
}
