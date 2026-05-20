
package com.example.coursecatalog.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
}
