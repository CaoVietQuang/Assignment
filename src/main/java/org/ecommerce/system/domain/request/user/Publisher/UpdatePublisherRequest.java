package org.ecommerce.system.domain.request.user.Publisher;

import lombok.Data;
@Data
public class UpdatePublisherRequest {
    private Long id;
    private String name;
    private String description;
}
