package org.ecommerce.system.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping("/create")
    public ResponseEntity<ResponseCommon<PublisherDto>> createPublisher(
            @RequestBody CreatePublisherRequest request) {
        try {
            PublisherDto publisher = publisherService.createPublisher(request);
            ResponseCommon<PublisherDto> response = ResponseCommon.success(publisher);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseCommon<PublisherDto>> updatePublisher(
            @RequestBody UpdatePublisherRequest request,
            @PathVariable Long id) {
        try {
            request.setId(id);
            PublisherDto publisher = publisherService.updatePublisher(request);
            ResponseCommon<PublisherDto> response = ResponseCommon.success(publisher);
            return ResponseEntity.ok(response);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseCommon<String>> deletePublisher(@PathVariable Long id) {
        try {
            publisherService.deletePublisher(id);
            ResponseCommon<String> response = ResponseCommon.success("Publisher deleted successfully");
            return ResponseEntity.ok(response);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseCommon<List<PublisherEntity>>> getPublisherById(@PathVariable Long id) {
        try {
            ResponseCommon<List<PublisherEntity>> response = publisherService.getPublisherById(id);
            return ResponseEntity.ok(response);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllPublishers")
    public ResponseEntity<ResponseCommon<PageResponse<PublisherResponse>>> getAllPublishers(int page, int size, String sortBy, String sortDirection) {
        try {
            PageResponse<PublisherResponse> response = publisherService.getAllPublishers(page, size, sortBy, sortDirection);
            return ResponseEntity.ok(ResponseCommon.success(response));
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
