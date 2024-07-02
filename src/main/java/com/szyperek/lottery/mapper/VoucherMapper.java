package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.dto.response.VoucherResponse;
import com.szyperek.lottery.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    @Mapping(source = "userId", target = "appUser.id")
    Voucher mapToEntity (VoucherRequest voucherRequest);
    VoucherResponse mapToVoucherResponse(Voucher voucher);
}
