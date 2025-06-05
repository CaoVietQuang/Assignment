package org.ecommerce.system.application.service.Implements;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecommerce.system.application.service.PublisherService;
import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.PublisherDto;
import org.ecommerce.system.domain.entity.PublisherEntity;
import org.ecommerce.system.domain.enums.ResponseCode;
import org.ecommerce.system.domain.request.user.Publisher.CreatePublisherRequest;
import org.ecommerce.system.domain.request.user.Publisher.UpdatePublisherRequest;
import org.ecommerce.system.domain.response.user.Publisher.PublisherResponse;
import org.ecommerce.system.exception.ApiException;
import org.ecommerce.system.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    @Transactional
    public PublisherDto createPublisher(CreatePublisherRequest request) {
        try {
            if (publisherRepository.existsByNameIgnoreCase(request.getName())) {
                throw new ApiException(ResponseCode.PUBLISHER_ALREADY_EXISTS);
            }
            PublisherEntity publisher = new PublisherEntity()
                    .setName(request.getName())
                    .setDescription(request.getDescription())
                    .setIsActive(0);

            PublisherEntity savedPublisher = publisherRepository.save(publisher);

            return convertToDto(savedPublisher);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public PublisherDto updatePublisher(UpdatePublisherRequest request) {
        try {
            PublisherEntity existingPublisher = publisherRepository.findById(request.getId())
                    .orElseThrow(() -> {
                        return new ApiException(ResponseCode.PUBLISHER_NOT_FOUND);
                    });

            if (publisherRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), request.getId())) {
                throw new ApiException(ResponseCode.PUBLISHER_ALREADY_EXISTS);
            }

            existingPublisher.setName(request.getName());
            existingPublisher.setDescription(request.getDescription());
            PublisherEntity updatedPublisher = publisherRepository.save(existingPublisher);

            return convertToDto(updatedPublisher);

        } catch (ApiException e) {
            log.error("API error while updating publisher: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating publisher: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deletePublisher(Long id) {
        try {
            PublisherEntity publisher = publisherRepository.findById(id)
                    .orElseThrow(() -> {
                        return new ApiException(ResponseCode.PUBLISHER_NOT_FOUND);
                    });
            publisher.setIsActive(1);
            publisherRepository.save(publisher);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseCommon<List<PublisherEntity>> getPublisherById(Long id) {
        try {
            PublisherEntity publisher = publisherRepository.findById(id)
                    .orElseThrow(() -> {
                        return new ApiException(ResponseCode.PUBLISHER_NOT_FOUND);
                    });
            if (publisher.getIsActive() == 1) {
                throw new ApiException(ResponseCode.PUBLISHER_NOT_FOUND);
            }
            List<PublisherEntity> publishers = List.of(publisher);
            return ResponseCommon.success(publishers);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public PageResponse<PublisherResponse> getAllPublishers(int page, int size, String sortBy, String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);

            Page<PublisherEntity> publisherPage = publisherRepository.findAll(pageable);

            List<PublisherResponse> publishers = publisherPage.getContent().stream().map(publisher -> {
                PublisherResponse response = new PublisherResponse();

                response.setId(publisher.getId());
                response.setName(publisher.getName());
                response.setDescription(publisher.getDescription());
                response.setIsActive(publisher.getIsActive());
                response.setCreatedAt(publisher.getCreatedAt());
                response.setUpdatedAt(publisher.getUpdatedAt());
                return response;
            }).collect(Collectors.toList());

            return PageResponse.<PublisherResponse>builder()
                    .currentPage(publisherPage.getNumber())
                    .pageSize(publisherPage.getSize())
                    .pagesCount(publisherPage.getTotalPages())
                    .currentTotalElementsCount(publisherPage.getNumberOfElements())
                    .hasNext(publisherPage.hasNext())
                    .hasPrevious(publisherPage.hasPrevious())
                    .content(publishers)
                    .build();

        } catch (Exception e) {
            log.error("Unexpected error while fetching active publishers: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseCommon<List<PublisherEntity>> searchPublishersByName(String name) {
        try {
            List<PublisherEntity> publishers = publisherRepository
                    .findByNameContainingIgnoreCaseAndIsActive(name, 0);

            if (publishers.isEmpty()) {
                return ResponseCommon.error(ResponseCode.PUBLISHER_NOT_FOUND);
            }

            return ResponseCommon.success(publishers);

        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    private PublisherDto convertToDto(PublisherEntity entity) {
        return new PublisherDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive()
        );
    }
}
