package org.ecommerce.system.application.service;


import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.request.user.Voucher.VoucherCreateRequest;
import org.ecommerce.system.domain.response.user.Voucher.VoucherResponse;

import java.util.List;

public interface VoucherService {
    VoucherResponse createVoucher(VoucherCreateRequest request);
    VoucherResponse getVoucherById(Long id);
    VoucherResponse getVoucherByCode(String code);
    List<VoucherResponse> getActiveVouchers();
    PageResponse<VoucherResponse> getAllVouchers(int page, int size);
    VoucherResponse updateVoucher(Long id, VoucherCreateRequest request);
    void deleteVoucher(Long id);
    boolean isVoucherValid(String code);
    void useVoucher(String code);
}
