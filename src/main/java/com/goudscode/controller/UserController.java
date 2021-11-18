package com.goudscode.controller;

import com.goudscode.model.BookingRequestDto;
import com.goudscode.model.RoomsDto;
import com.goudscode.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user/room")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/booking")
    public ResponseEntity<Set<RoomsDto>> getAllRooms () {
        return ResponseEntity.ok(userService.getAllRooms());
    }

    @PostMapping("/booking")
    public ResponseEntity<Object> bookRooms (@RequestBody BookingRequestDto bookingRequestDto) {

        userService.validateInputDataAndCheckRoomsAvailability(bookingRequestDto);

        return ResponseEntity.status(CREATED)
                .body(userService.bookRooms(bookingRequestDto));


    }
}
