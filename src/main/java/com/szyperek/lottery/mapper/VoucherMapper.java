package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.dto.response.VoucherResponse;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.Voucher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VoucherMapper {

    public Voucher mapToEntity(VoucherRequest voucherRequest) {
        if (voucherRequest == null) {
            return null;
        }

        Voucher voucher = new Voucher();

        voucher.setAppUser(voucherRequestToAppUser(voucherRequest));
        voucher.setVoucherName(voucherRequest.getVoucherName());
        voucher.setActivationCode(voucherRequest.getActivationCode());
        voucher.setExpirationDate(voucherRequest.getExpirationDate());

        return voucher;
    }

    public VoucherResponse mapToVoucherResponse(Voucher voucher) {
        if (voucher == null) {
            return null;
        }

        Long id = null;
        String voucherName = null;
        String activationCode = null;
        LocalDate expirationDate = null;

        id = voucher.getId();
        voucherName = voucher.getVoucherName();
        activationCode = voucher.getActivationCode();
        expirationDate = voucher.getExpirationDate();

        return new VoucherResponse(id, voucherName, activationCode, expirationDate);
    }

    protected AppUser voucherRequestToAppUser(VoucherRequest voucherRequest) {
        if (voucherRequest == null) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setId(voucherRequest.getUserId());

        return appUser;
    }
}
