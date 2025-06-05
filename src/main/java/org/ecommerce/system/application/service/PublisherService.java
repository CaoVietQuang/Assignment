package org.ecommerce.system.application.service;

import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.PublisherDto;
import org.ecommerce.system.domain.entity.PublisherEntity;
import org.ecommerce.system.domain.request.user.Publisher.CreatePublisherRequest;
import org.ecommerce.system.domain.request.user.Publisher.UpdatePublisherRequest;
import org.ecommerce.system.domain.response.user.Publisher.PublisherResponse;

import java.util.List;

public interface PublisherService {
    PublisherDto createPublisher(CreatePublisherRequest request);

    PublisherDto updatePublisher(UpdatePublisherRequest request);

    void deletePublisher(Long id);

    ResponseCommon<List<PublisherEntity>> getPublisherById(Long id);

    PageResponse<PublisherResponse> getAllPublishers(int page, int size, String sortBy, String sortDirection);

    ResponseCommon<List<PublisherEntity>> searchPublishersByName(String name);

}
