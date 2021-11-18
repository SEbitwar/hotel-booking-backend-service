package com.goudscode.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    private UUID userId;
    private String nameOfUser;
    private List<SingleBookingDto> singleBookingDtos;
}



