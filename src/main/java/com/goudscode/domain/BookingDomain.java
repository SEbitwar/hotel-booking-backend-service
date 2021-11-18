package com.goudscode.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "BOOKINGS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID bookingId;
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name = "room_booking",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "roomId"))
    private Set<RoomDomain> roomDomains = new HashSet<>();
    @ManyToOne (cascade = CascadeType.ALL)
    private UserDomain userDomain;
    @Lob
    private String bookingDates; // contains all the booking dates with "," separated
}
