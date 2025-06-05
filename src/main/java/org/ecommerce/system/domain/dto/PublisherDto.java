package org.ecommerce.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PublisherDto {
    private Long id;
    private String name;
    private String description;
    private Integer isActive;
}
