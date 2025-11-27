package ecomhub.PromotionDiscountService.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PromotionDiscountRequest {
    @NotBlank(message = "Tên khuyến mãi là bắt buộc")
    @Size(max = 255, message = "Tên khuyến mãi không được vượt quá 255 ký tự")
    private String name;

    @NotBlank(message = "Mã khuyến mãi là bắt buộc")
    @Pattern(regexp = "^[A-Z0-9]{4,20}$", message = "Mã khuyến mãi chỉ gồm chữ in hoa và số, dài 4-20 ký tự")
    @Size(max = 20, message = "Mã khuyến mãi không được vượt quá 20 ký tự")
    private String code;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;

    @NotBlank(message = "Loại giảm giá là bắt buộc")
    @Pattern(regexp = "PERCENTAGE|FIXED", message = "Loại giảm giá phải là PERCENTAGE hoặc FIXED")
    private String discountType;

    @NotNull(message = "Giá trị giảm giá là bắt buộc")
    @Positive(message = "Giá trị giảm giá phải lớn hơn 0")
    @DecimalMax(value = "100.0", message = "Giá trị giảm giá tối đa là 100 (cho PERCENTAGE)")
    private Double discountValue;

    @NotNull(message = "Ngày bắt đầu là bắt buộc")
    @FutureOrPresent(message = "Ngày bắt đầu phải là hiện tại hoặc tương lai")
    private Instant startDate;

    @NotNull(message = "Ngày kết thúc là bắt buộc")
    @Future(message = "Ngày kết thúc phải trong tương lai")
    private Instant endDate;

    @NotBlank(message = "Trạng thái là bắt buộc")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Trạng thái phải là ACTIVE hoặc INACTIVE")
    private String status;
}