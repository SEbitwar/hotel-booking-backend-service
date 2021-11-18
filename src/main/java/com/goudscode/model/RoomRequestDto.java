package com.goudscode.model;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    private UUID roomId;
    private int roomNo;
    private double price;
}
