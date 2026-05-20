
package com.example.coursecatalog.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank(message = "Tên khóa học không được để trống")
    private String name;

    private String description;

    @Min(value = 1, message = "Giá phải lớn hơn 0")
    private Double price;
}
