package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.VoucherRequest;
import com.internship.juglottery.dto.response.VoucherResponse;
import com.internship.juglottery.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    @Mapping(source = "userId", target = "appUser.id")
    Voucher mapToEntity (VoucherRequest voucherRequest);
    VoucherResponse mapToVoucherResponse(Voucher voucher);
}
