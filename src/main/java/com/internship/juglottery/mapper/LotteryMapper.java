package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.LotteryRequest;
import com.internship.juglottery.dto.response.LotteryResponse;
import com.internship.juglottery.entity.Lottery;
import com.internship.juglottery.entity.enums.Status;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class LotteryMapper {

    public abstract LotteryResponse mapToResponse(Lottery lottery);

    @Mapping(source = "userId", target = "appUser.id")
    public abstract Lottery mapToEntity(LotteryRequest lotteryRequest);

    @BeforeMapping
    protected void setNotActiveStatus(@MappingTarget Lottery lottery) {
        lottery.setStatus(Status.NOT_ACTIVE);
    }

    @AfterMapping
    protected void setVoucherListToNull(@MappingTarget Lottery lottery) {
       lottery.setVouchers(null);
    }
}

