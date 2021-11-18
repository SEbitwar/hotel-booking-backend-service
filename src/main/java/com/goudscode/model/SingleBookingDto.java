package com.goudscode.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingleBookingDto {
    private String bookingDates;
    private List<Integer> roomNumbers;
}

