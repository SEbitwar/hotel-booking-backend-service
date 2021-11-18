package com.goudscode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingsDto {
    private String bookingDates;
    private String userName;
    private UUID bookingId;
    private UUID userId;
    private String roomNos;
}
