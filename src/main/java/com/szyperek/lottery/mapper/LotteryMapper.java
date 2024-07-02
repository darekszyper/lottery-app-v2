package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.LotteryRequest;
import com.szyperek.lottery.dto.response.FinishedLotteryResponse;
import com.szyperek.lottery.dto.response.LotteryResponse;
import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.enums.Status;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class LotteryMapper {

    public abstract LotteryResponse mapToResponse(Lottery lottery);

    @Mapping(source = "userId", target = "appUser.id")
    public abstract Lottery mapToEntity(LotteryRequest lotteryRequest);


    public abstract FinishedLotteryResponse mapToFinishedLotteryResponse(Lottery lottery);

    @BeforeMapping
    protected void setNotActiveStatus(@MappingTarget Lottery lottery) {
        lottery.setStatus(Status.NOT_ACTIVE);
    }

    @AfterMapping
    protected void setVoucherListToNull(@MappingTarget Lottery lottery) {
       lottery.setVouchers(null);
    }
}

