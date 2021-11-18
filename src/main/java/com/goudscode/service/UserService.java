package com.goudscode.service;

import com.goudscode.domain.BookingDomain;
import com.goudscode.domain.RoomDomain;
import com.goudscode.domain.UserDomain;
import com.goudscode.error.InvalidDataException;
import com.goudscode.error.NotFoundException;
import com.goudscode.error.RoomAlreadyBookedException;
import com.goudscode.model.*;
import com.goudscode.repository.BookingRepo;
import com.goudscode.repository.RoomRepo;
import com.goudscode.repository.UserRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AdminService adminService;
    private final RoomRepo roomRepo;
    private final UserRepo userRepo;
    private final BookingRepo bookingRepo;




    public UserService(AdminService adminService, RoomRepo roomRepo, UserRepo userRepo, BookingRepo bookingRepo) {
        this.adminService = adminService;
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
        this.bookingRepo = bookingRepo;
    }

    public Set<RoomsDto> getAllRooms() {
        Set<RoomsDto> allRooms = adminService.getAllRooms();
                allRooms.forEach(roomsDto -> roomsDto.getBookings()
                        .forEach(bookingsDto -> {
                            bookingsDto.setUserId(null);
                            bookingsDto.setUserName("Not Available");
                            bookingsDto.setBookingId(null);
                        }));
       return allRooms;
    }

    @Transactional
    public BookingResponseDto bookRooms(BookingRequestDto bookingRequestDto) {

        if (!userRepo.existsById(bookingRequestDto.getUserId()))
            throw new NotFoundException("User with userId : " + bookingRequestDto.getUserId().toString() + " not found");

        UserDomain userDomain = userRepo.findById(bookingRequestDto.getUserId()).get();

        Set<BookingDomain> bookingDomains = bookingRequestDto.getSingleBookingDtos().stream().map(singleBookingDto -> {
            Set<RoomDomain> roomDomains = singleBookingDto.getRoomNumbers().stream()
                    .map(roomNo -> roomRepo.findByRoomNo(roomNo).orElse(null))
                    .filter(Objects::nonNull).collect(Collectors.toSet());
            BookingDomain bookingDomain = BookingDomain.builder()
                    .roomDomains(roomDomains)
                    .bookingDates(singleBookingDto.getBookingDates())
                    .userDomain(userDomain)
                    .build();
            roomDomains.forEach(room -> room.getBookingDomains().add(bookingDomain));
            roomRepo.saveAll(roomDomains);
            userDomain.getBookingDomains().add(bookingDomain);
            return bookingDomain;
        }).collect(Collectors.toSet());
        bookingRepo.saveAll(bookingDomains);
        userRepo.save(userDomain);
        double totalCost = 0.00;
        for (BookingDomain bookingDomain : bookingDomains) {
            for (RoomDomain roomDomain : bookingDomain.getRoomDomains())
                totalCost += roomDomain.getPrice();
        }

        return BookingResponseDto.builder()
                .bookings(bookingDomains.stream().map(booking -> {
                    return BookingsDto.builder()
                            .userId(booking.getUserDomain().getUserId())
                            .bookingId(booking.getBookingId())
                            .bookingDates(booking.getBookingDates())
                            .userName(booking.getUserDomain().getName())
                            .roomNos(booking.getRoomDomains().stream().map(RoomDomain::getRoomNo).collect(Collectors.toSet()).toString())
                            .build();
                }).collect(Collectors.toList()))
                .Name(userDomain.getName())
                .totalCharges(totalCost)
                .build();
    }

    public void validateInputDataAndCheckRoomsAvailability(BookingRequestDto bookingRequestDto) {

       for (SingleBookingDto sdto: bookingRequestDto.getSingleBookingDtos()) {

           List<String> dates = areValidDates(sdto.getBookingDates());

           if (sdto.getRoomNumbers().size() != new HashSet<>(sdto.getRoomNumbers()).size())
               throw new InvalidDataException("Found duplicate room numbers in input request");

           StringBuilder bookedDates = new StringBuilder();

           for (Integer roomNo : sdto.getRoomNumbers()) {
               RoomDomain roomDomain = roomRepo.findByRoomNo(roomNo).orElseThrow(() ->
                       new NotFoundException("Room with roomNo : " + roomNo + " not found"));
               roomDomain.getBookingDomains().forEach(booking -> bookedDates.append(", " + booking.getBookingDates()));
               for (String bookDate : dates)
                    if (bookedDates.toString().contains(bookDate))
                        throw new RoomAlreadyBookedException("Room with room no : " + roomDomain.getRoomNo()
                                + " is already booked on date : " + bookDate);

           }
       }

    }

    private List<String> areValidDates(String bookingDates) {
        List<String> dates = Arrays.stream(bookingDates.trim().split(","))
                .map(String::trim).collect(Collectors.toList());
        Set<LocalDate> localDates = dates.stream()
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_DATE)).collect(Collectors.toSet());
        for (LocalDate localDate : localDates) {
            if (localDate.isBefore(LocalDate.now()))
                throw new InvalidDataException("Entered date : " + localDate + " is before current date");
        }
        if (localDates.size() != dates.size())
            throw new InvalidDataException("Found duplicate dates in input request");
        return dates;

    }
}
