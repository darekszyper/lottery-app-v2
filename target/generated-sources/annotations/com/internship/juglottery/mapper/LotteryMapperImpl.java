package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.LotteryRequest;
import com.internship.juglottery.dto.response.FinishedLotteryResponse;
import com.internship.juglottery.dto.response.LotteryResponse;
import com.internship.juglottery.dto.response.ParticipantResponse;
import com.internship.juglottery.dto.response.VoucherResponse;
import com.internship.juglottery.dto.response.WinnerResponse;
import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.entity.Lottery;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.entity.Winner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-27T22:26:19+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (JetBrains s.r.o.)"
)
@Component
public class LotteryMapperImpl extends LotteryMapper {

    @Override
    public LotteryResponse mapToResponse(Lottery lottery) {
        if ( lottery == null ) {
            return null;
        }

        Long id = null;
        String eventName = null;

        id = lottery.getId();
        eventName = lottery.getEventName();

        LotteryResponse lotteryResponse = new LotteryResponse( id, eventName );

        return lotteryResponse;
    }

    @Override
    public Lottery mapToEntity(LotteryRequest lotteryRequest) {
        if ( lotteryRequest == null ) {
            return null;
        }

        Lottery lottery = new Lottery();

        setNotActiveStatus( lottery );

        lottery.setAppUser( lotteryRequestToAppUser( lotteryRequest ) );
        lottery.setEventName( lotteryRequest.getEventName() );
        lottery.setCity( lotteryRequest.getCity() );

        setVoucherListToNull( lottery );

        return lottery;
    }

    @Override
    public FinishedLotteryResponse mapToFinishedLotteryResponse(Lottery lottery) {
        if ( lottery == null ) {
            return null;
        }

        Long id = null;
        String eventName = null;
        List<WinnerResponse> winners = null;
        LocalDate lotteryDate = null;
        String city = null;

        id = lottery.getId();
        eventName = lottery.getEventName();
        winners = winnerListToWinnerResponseList( lottery.getWinners() );
        lotteryDate = lottery.getLotteryDate();
        city = lottery.getCity();

        FinishedLotteryResponse finishedLotteryResponse = new FinishedLotteryResponse( id, eventName, winners, lotteryDate, city );

        return finishedLotteryResponse;
    }

    protected AppUser lotteryRequestToAppUser(LotteryRequest lotteryRequest) {
        if ( lotteryRequest == null ) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setId( lotteryRequest.getUserId() );

        return appUser;
    }

    protected ParticipantResponse participantToParticipantResponse(Participant participant) {
        if ( participant == null ) {
            return null;
        }

        ParticipantResponse participantResponse = new ParticipantResponse();

        participantResponse.setId( participant.getId() );
        participantResponse.setFirstName( participant.getFirstName() );
        participantResponse.setEmail( participant.getEmail() );

        return participantResponse;
    }

    protected VoucherResponse voucherToVoucherResponse(Voucher voucher) {
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

    protected WinnerResponse winnerToWinnerResponse(Winner winner) {
        if ( winner == null ) {
            return null;
        }

        ParticipantResponse participant = null;
        VoucherResponse voucher = null;

        participant = participantToParticipantResponse( winner.getParticipant() );
        voucher = voucherToVoucherResponse( winner.getVoucher() );

        WinnerResponse winnerResponse = new WinnerResponse( participant, voucher );

        return winnerResponse;
    }

    protected List<WinnerResponse> winnerListToWinnerResponseList(List<Winner> list) {
        if ( list == null ) {
            return null;
        }

        List<WinnerResponse> list1 = new ArrayList<WinnerResponse>( list.size() );
        for ( Winner winner : list ) {
            list1.add( winnerToWinnerResponse( winner ) );
        }

        return list1;
    }
}
