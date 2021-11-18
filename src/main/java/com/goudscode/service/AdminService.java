package com.goudscode.service;

import com.goudscode.domain.BookingDomain;
import com.goudscode.domain.RoomDomain;
import com.goudscode.domain.UserDomain;
import com.goudscode.error.InvalidDataException;
import com.goudscode.error.NotFoundException;
import com.goudscode.model.BookingsDto;
import com.goudscode.model.RoomsDto;
import com.goudscode.repository.BookingRepo;
import com.goudscode.repository.RoomRepo;
import com.goudscode.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final RoomRepo roomRepo;
    private final UserRepo userRepo;
    private final BookingRepo bookingRepo;

    public AdminService(RoomRepo roomRepo, UserRepo userRepo, BookingRepo bookingRepo) {
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
        this.bookingRepo = bookingRepo;
    }

    public Set<RoomsDto> getAllRooms() {

        return ((List<RoomDomain>) roomRepo.findAll()).stream().map(room -> RoomsDto.builder()
                .roomId(room.getRoomId())
                .roomNo(room.getRoomNo())
                .roomCharges(room.getPrice())
                .bookings(room.getBookingDomains().stream().map(booking -> BookingsDto.builder()
                        .bookingDates(booking.getBookingDates())
                        .bookingId(booking.getBookingId())
                        .roomNos(booking.getRoomDomains().stream().map(RoomDomain::getRoomNo).collect(Collectors.toSet()).toString())
                        .userName(booking.getUserDomain().getName())
                        .userId(booking.getUserDomain().getUserId())
                        .build()).collect(Collectors.toSet()))
                .build()).collect(Collectors.toSet());
    }

    public RoomDomain addRoom(RoomDomain roomDomain) {
        if (roomRepo.existsByRoomNo(roomDomain.getRoomNo()))
            throw new InvalidDataException("Room with roomNo : " + roomDomain.getRoomNo() + " is already present");
        RoomDomain savedRoomDomain = roomRepo.save(roomDomain);
        return savedRoomDomain;
    }

    public RoomDomain updateRoom(RoomDomain roomDomain) {
        if (roomDomain.getRoomId() == null || !roomRepo.existsById(roomDomain.getRoomId()))
            throw new NotFoundException("Room with roomId : " + roomDomain.getRoomId() + " Not found");
        RoomDomain domain = roomRepo.findById(roomDomain.getRoomId()).get();
        domain.setRoomNo(roomDomain.getRoomNo());
        domain.setPrice(roomDomain.getPrice());
        roomRepo.save(domain);
        return roomDomain;
    }

    public RoomDomain removeRoom(UUID roomId) {
        if (roomId == null || !roomRepo.existsById(roomId))
            throw new NotFoundException("Room with roomId : " + roomId + " Not found");
        RoomDomain roomDomain = roomRepo.findById(roomId).get();
        roomRepo.deleteById(roomId);
        return roomDomain;
    }

    public List<UserDomain> getAllUser() {
        return (List<UserDomain>) userRepo.findAll();
    }

    public Set<RoomsDto> getBookedRooms() {
        return getAllRooms().stream().filter(roomsDto -> !roomsDto.getBookings().isEmpty()).collect(Collectors.toSet());
    }

    public Set<RoomsDto> getAvailableRooms() {
        return getAllRooms().stream().filter(roomsDto -> roomsDto.getBookings().isEmpty()).collect(Collectors.toSet());
    }

    public double getTotalRevenue() {
        List<BookingDomain> bookingDomains = (List<BookingDomain>) bookingRepo.findAll();
        double totalRevenue = 0;
        for (BookingDomain bookingDomain : bookingDomains) {
            for (RoomDomain roomDomain : bookingDomain.getRoomDomains()) {
                totalRevenue += roomDomain.getPrice();
            }
        }
        return totalRevenue;
    }
}
