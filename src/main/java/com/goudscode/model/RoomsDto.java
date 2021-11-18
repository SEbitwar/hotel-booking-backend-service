package com.goudscode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomsDto {
    private Integer roomNo;
    private double roomCharges;
    private UUID roomId;
    private Set<BookingsDto> bookings;
}



