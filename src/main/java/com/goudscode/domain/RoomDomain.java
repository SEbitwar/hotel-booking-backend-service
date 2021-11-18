package com.goudscode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "ROOM")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID roomId;
    @Column(unique = true, nullable = false)
    private Integer roomNo;
    private Double price;
    @ManyToMany (mappedBy = "roomDomains")
    @JsonIgnore
    private Set<BookingDomain> bookingDomains = new HashSet<>();
}
