
package com.example.coursecatalog.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên khóa học
    @Column(nullable = false)
    private String name;

    // Mô tả khóa học
    private String description;

    // Giá khóa học
    private Double price;

    // Đường dẫn ảnh
    private String imageUrl;
}
