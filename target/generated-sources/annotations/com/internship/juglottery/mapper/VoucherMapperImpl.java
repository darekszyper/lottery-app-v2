package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.VoucherRequest;
import com.internship.juglottery.dto.response.VoucherResponse;
import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.entity.Voucher;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-21T15:07:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (JetBrains s.r.o.)"
)
@Component
public class VoucherMapperImpl implements VoucherMapper {

    @Override
    public Voucher mapToEntity(VoucherRequest voucherRequest) {
        if ( voucherRequest == null ) {
            return null;
        }

        Voucher voucher = new Voucher();

        voucher.setAppUser( voucherRequestToAppUser( voucherRequest ) );
        voucher.setVoucherName( voucherRequest.getVoucherName() );
        voucher.setActivationCode( voucherRequest.getActivationCode() );
        voucher.setExpirationDate( voucherRequest.getExpirationDate() );

        return voucher;
    }

    @Override
    public VoucherResponse mapToVoucherResponse(Voucher voucher) {
        if ( voucher == null ) {
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

        VoucherResponse voucherResponse = new VoucherResponse( id, voucherName, activationCode, expirationDate );

        return voucherResponse;
    }

    protected AppUser voucherRequestToAppUser(VoucherRequest voucherRequest) {
        if ( voucherRequest == null ) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setId( voucherRequest.getUserId() );

        return appUser;
    }
}
