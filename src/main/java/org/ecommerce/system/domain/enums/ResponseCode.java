package org.ecommerce.system.domain.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(200, "Thành công"),
    FAIL(400, "Thất bại"),
    USER_EXISTS(409, "Người dùng đã tồn tại"),
    INVALID_INPUT(400, "Dữ liệu đầu vào không hợp lệ"),
    PUBLISHER_ALREADY_EXISTS(409, "Nhà phát hành đã tồn tại"),
    PUBLISHER_NOT_FOUND(404, "Không tìm thấy nhà phát hành"),
    PUBLISHER_INACTIVE(400, "Nhà phát hành không hoạt động"),
    USER_NOT_FOUND(404, "Không tìm thấy người dùng"),
    EMAIL_EXISTS(409, "Email đã tồn tại"),
    EMPLOYEE_NOT_FOUND(404, "Không tìm thấy nhân viên"),
    CATEGORY_NOT_FOUND(404, "Không tìm thấy danh mục"),
    CATEGORY_INACTIVE(400, "Danh mục không hoạt động"),
    CATEGORY_ALREADY_EXISTS(409, "Danh mục đã tồn tại"),
    CATEGORY_HAS_PRODUCTS(409, "Danh mục có sản phẩm không thể xóa"),
    NAME_EXISTS(409, "Tên đã tồn tại"),
    PRODUCT_NOT_FOUND(404, "Không tìm thấy sản phẩm"),
    PRODUCT_INACTIVE(400, "Sản phẩm không hoạt động"),
    PRODUCT_HAS_ACTIVE_ORDERS(400, "Sản phẩm có đơn hàng đang hoạt động không thể xóa"),
    PRODUCT_ALREADY_DELETED(400, "Sản phẩm đã bị xóa"),
    AUTHOR_NOT_FOUND(404, "Không tìm thấy tác giả"),
    VOUCHER_NOT_FOUND(404, "Không tìm thấy mã giảm giá"),
    ADDRESS_NOT_FOUND(404, "Không tìm thấy địa chỉ"),
    ORDER_NOT_FOUND(404, "Không tìm thấy đơn hàng"),
    UNAUTHORIZED(401, "Không được phép truy cập"),
    LOGIN_FAILED(401, "Thông tin tài khoản hoặc mật khẩu không chính xác"),
    INTERNAL_SERVER_ERROR(500, "Lỗi máy chủ"),
    PRODUCT_CODE_ALREADY_EXISTS(409, "Mã sản phẩm đã tồn tại"),
    INVALID_PRICE(400, "Giá sản phẩm không hợp lệ"),
    INVALID_PRICE_RANGE(400, "Giá tối thiểu không được lớn hơn giá tối đa"),
    ;
    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}