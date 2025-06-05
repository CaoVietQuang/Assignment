package org.ecommerce.system.application.service;

import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.request.user.Feedback.FeedbackCreateRequest;
import org.ecommerce.system.domain.response.user.Feedback.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse createFeedback(Long userId, FeedbackCreateRequest request);
    PageResponse<FeedbackResponse> getFeedbacksByProductId(Long productId, int page, int size);
    PageResponse<FeedbackResponse> getFeedbacksByUserId(Long userId, int page, int size);
    void deleteFeedback(Long feedbackId);
    Double getAverageRatingByProductId(Long productId);
    FeedbackResponse getFeedbackById(Long id);
    FeedbackResponse updateFeedback(Long id, FeedbackCreateRequest request);
}
