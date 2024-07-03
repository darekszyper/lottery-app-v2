package com.szyperek.lottery.service;

import com.szyperek.lottery.entity.*;
import com.szyperek.lottery.entity.enums.Status;
import com.szyperek.lottery.exception.LotteryNotActiveException;
import com.szyperek.lottery.repository.LotteryRepo;
import com.szyperek.lottery.repository.WinnerRepo;
import com.szyperek.lottery.service.impl.LotteryServiceImpl;
import com.szyperek.lottery.service.impl.RandomOrgServiceImpl;
import com.szyperek.lottery.service.impl.RandomizeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LotteryServiceTest {

    @Mock
    private ParticipantService participantService;
    @Mock
    private VoucherService voucherService;
    @Mock
    private RandomizeServiceImpl randomizeService;
    @Mock
    private RandomOrgServiceImpl randomOrgService;
    @Mock
    private LotteryRepo lotteryRepo;
    @Mock
    private WinnerRepo winnerRepo;
    @Mock
    private Lottery lottery;
    @Mock
    private Voucher voucher;
    @Mock
    private AppUser appUser;

    @InjectMocks
    private LotteryServiceImpl lotteryServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should change status of lottery to active")
    void shouldChangeStatusToActive() {
        //given
        Long lotteryId = 1L;
        doNothing().when(lotteryRepo).changeStatusToActive(lotteryId);

        //when
        lotteryServiceImpl.changeLotteryStatusToActive(lotteryId);

        //then
        verify(lotteryRepo, times(1)).changeStatusToActive(lotteryId);
    }

    @Test
    @DisplayName("Should change status of lottery to finished and set current date")
    void shouldChangeStatusToFinished() {
        //given
        Long lotteryId = 1L;
        doNothing().when(lotteryRepo).changeStatusToFinished(lotteryId);

        //when
        lotteryServiceImpl.changeLotteryStatusToFinished(lotteryId);

        //then
        verify(lotteryRepo, times(1)).changeStatusToFinished(lotteryId);
        verify(lotteryRepo, times(1)).setEventDate(lotteryId);
    }

    @Test
    @DisplayName("Should do something when lists are empty")
    public void shouldDoSomethingWhenListsAreEmpty() {
        //given
        Long lotteryId = 1L;
        when(participantService.getParticipantsByLotteryId(lotteryId)).thenReturn(new ArrayList<>());
        when(voucherService.getVouchersByLotteryId(lotteryId)).thenReturn(new ArrayList<>());
        when(lotteryRepo.getStatusFromDb(lotteryId)).thenReturn(Status.ACTIVE);


        //when
        List<Winner> winners = lotteryServiceImpl.pickWinners(lotteryId);

        //then
        assertEquals(0, winners.size());
        verify(lotteryRepo, times(1)).findById(lotteryId);
    }

    @Test
    @DisplayName("Should return winners when list is not empty")
    public void shouldReturnWinnersWhenListIsNotEmpty() {
        //given
        Long lotteryId = 1L;

        Participant participant1 = new Participant("John", "john@example.com");
        Participant participant2 = new Participant("Jane", "jane@example.com");

        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);

        Voucher voucher1 = new Voucher("Voucher1", LocalDate.of(2023, 1, 2));
        Voucher voucher2 = new Voucher("Voucher2", LocalDate.of(2023, 1, 4));

        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(voucher1);
        vouchers.add(voucher2);

        Lottery lottery = new Lottery();
        lottery.setId(lotteryId);
        lottery.setEventName("Lottery1");
        lottery.setCity("Bialystok");
        lottery.setLotteryDate(LocalDate.of(2023, 8, 8));
        lottery.setStatus(Status.ACTIVE);
        lottery.setParticipants(participants);
        lottery.setVouchers(vouchers);
        lottery.setAppUser(appUser);


        List<Winner> winners1 = new ArrayList<>();
        winners1.add(new Winner(participant1, voucher1, lottery));
        winners1.add(new Winner(participant2, voucher2, lottery));

        when(randomOrgService.randomize(participants.size() - 1, vouchers.size())).thenReturn(null);
        when(participantService.getParticipantsByLotteryId(lotteryId)).thenReturn(participants);
        when(voucherService.getVouchersByLotteryId(lotteryId)).thenReturn(vouchers);
        when(lotteryRepo.findById(lotteryId)).thenReturn(Optional.of(lottery));
        when(randomizeService.randomize(participants.size() - 1, vouchers.size())).thenReturn(List.of(0, 1));
        when(winnerRepo.saveAll(any())).thenReturn(winners1);
        when(lotteryRepo.getStatusFromDb(lotteryId)).thenReturn(Status.ACTIVE);

        //when
        List<Winner> winners2 = lotteryServiceImpl.pickWinners(lotteryId);

        //then
        assertEquals(2, winners2.size());
        verify(lotteryRepo, times(1)).findById(lotteryId);
        verify(lotteryRepo, times(1)).changeStatusToFinished(lotteryId);
        verify(lotteryRepo, times(1)).setEventDate(lotteryId);
    }

    @Test
    @DisplayName("Should return List of lotteries")
    void shouldReturnListOfLotteries() {
        //given
        when(lotteryRepo.findByStatus(Status.NOT_ACTIVE)).thenReturn(List.of(lottery));

        //when
        List<Lottery> lotteries = lotteryServiceImpl.getAllNotActiveLotteries();

        //then
        assertInstanceOf(List.class, lotteries);
        verify(lotteryRepo, times(1)).findByStatus(Status.NOT_ACTIVE);
    }

    @Test
    @DisplayName("Should return lottery")
    void shouldReturnLottery() {
        //given
        when(lotteryRepo.save(lottery)).thenReturn(lottery);

        //when
        Lottery lotteryEntity = lotteryServiceImpl.createLotteryWithAssignedVouchers(lottery, List.of(voucher));

        //then
        assertInstanceOf(Lottery.class, lotteryEntity);
        verify(lotteryRepo, times(1)).save(lotteryEntity);
    }

    @Test
    @DisplayName("Should call deleteLottery method")
    void shouldCallDeleteLottery() {
        //given
        Long lotteryId = 1L;

        //when
        lotteryServiceImpl.deleteLottery(lotteryId);

        //then
        verify(lotteryRepo, times(1)).deleteLottery(lotteryId);
    }

    @Test
    @DisplayName("Should return list of lotteries created by user")
    void shouldReturnAllLotteriesAssignedToUser() {
        //given
        Long userId = 1L;
        when(lotteryRepo.findAllByAppUserIdAndStatus(userId, Status.NOT_ACTIVE)).thenReturn(List.of(lottery));

        //when
        List<Lottery> lotteries = lotteryServiceImpl.getAllLotteriesAssignedToUser(userId);

        //then
        assertNotNull(lotteries);
    }

    @Test
    @DisplayName("Should throw LotteryIsFinishedException when lottery status is finished")
    void shouldThrowLotteryIsFinishedException() {
        //given
        Long lotteryId = 1L;
        when(lotteryRepo.getStatusFromDb(lotteryId)).thenReturn(Status.FINISHED);

        //then
        assertThrows(LotteryNotActiveException.class, () -> lotteryServiceImpl.pickWinners(lotteryId));
    }
}