package com.goudscode.controller;


import com.goudscode.domain.RoomDomain;
import com.goudscode.domain.UserDomain;
import com.goudscode.error.InvalidDataException;
import com.goudscode.model.RoomRequestDto;
import com.goudscode.model.RoomsDto;
import com.goudscode.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/room")
    ResponseEntity<Set<RoomsDto>> getAllRooms (){
        Set<RoomsDto> allRooms = adminService.getAllRooms();
        return ResponseEntity.ok(allRooms);
    }

    @PostMapping("/room")
    ResponseEntity<RoomDomain> addRoom (@RequestBody RoomRequestDto roomRequestDto){
        if (roomRequestDto.getRoomId() != null)
            throw new InvalidDataException("Room Id needs to be null while making POST call");
        RoomDomain roomDomain = RoomDomain.builder()
                .bookingDomains(new HashSet<>())
                .roomNo(roomRequestDto.getRoomNo())
                .price(roomRequestDto.getPrice()).build();
        return ResponseEntity.status(CREATED).body(adminService.addRoom(roomDomain));
    }

    @PutMapping("/room")
    ResponseEntity<RoomDomain> updateRoom (@RequestBody RoomRequestDto roomRequestDto){

        RoomDomain roomDomain = RoomDomain.builder()
                .roomNo(roomRequestDto.getRoomNo())
                .roomId(roomRequestDto.getRoomId())
                .price(roomRequestDto.getPrice()).build();
        adminService.updateRoom(roomDomain);
        return ResponseEntity.ok(roomDomain);
    }

    @DeleteMapping("/room/{roomId}")
    ResponseEntity<RoomDomain> removeRoom (@PathVariable UUID roomId){
        RoomDomain roomDomain = adminService.removeRoom(roomId);
        return ResponseEntity.status(OK).body(roomDomain);
    }

    @GetMapping("/user")
    ResponseEntity<List<UserDomain>> getAllUser() {
        List<UserDomain> userDomains = adminService.getAllUser();
        return ResponseEntity.ok(userDomains);
    }

    @GetMapping("/room/booked")
    ResponseEntity<Set<RoomsDto>> getBookedRooms (){
        Set<RoomsDto> bookedRooms = adminService.getBookedRooms();
        return ResponseEntity.ok(bookedRooms);
    }

    @GetMapping("/room/available")
    ResponseEntity<Set<RoomsDto>> getAvailableRooms (){
        Set<RoomsDto> availableRooms = adminService.getAvailableRooms();
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/room/totalRevenue")
    ResponseEntity<String> getTotalRevenue() {
        double revenue = adminService.getTotalRevenue();
        return ResponseEntity.ok("Total revenue is : " + revenue);
    }
}
