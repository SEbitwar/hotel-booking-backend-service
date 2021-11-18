package com.goudscode.bootsrtap;

import com.goudscode.domain.BookingDomain;
import com.goudscode.domain.RoomDomain;
import com.goudscode.domain.UserDomain;
import com.goudscode.repository.BookingRepo;
import com.goudscode.repository.RoomRepo;
import com.goudscode.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.format.DateTimeFormatter.ISO_DATE;

@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepo userRepo;
    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;

    public Bootstrap(UserRepo userRepo, BookingRepo bookingRepo, RoomRepo roomRepo) {
        this.userRepo = userRepo;
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {


        userRepo.saveAll(getUsers());
        roomRepo.saveAll(getRooms());
        List<RoomDomain> roomDomains = (List<RoomDomain>) roomRepo.findAll();
        List<UserDomain> userDomains = (List<UserDomain>) userRepo.findAll();

        UserDomain sansa = userDomains.get(1);
        UserDomain motherOfdDragons = userDomains.get(2);
        BookingDomain bookingDomain1 = BookingDomain.builder()
                .roomDomains(Set.of(roomDomains.get(1), roomDomains.get(3)))
                .bookingDates(LocalDate.now().format(ISO_DATE) + ", " + LocalDate.now().plusDays(1).format(ISO_DATE))
                .userDomain(sansa)
                .build();
        roomDomains.get(1).getBookingDomains().add(bookingDomain1);
        roomDomains.get(3).getBookingDomains().add(bookingDomain1);
        sansa.getBookingDomains().add(bookingDomain1);
        BookingDomain bookingDomain2 = BookingDomain.builder()
                .roomDomains(Set.of(roomDomains.get(2), roomDomains.get(3)))
                .bookingDates(LocalDate.now().plusDays(2).format(ISO_DATE) + ", " + LocalDate.now().plusDays(3).format(ISO_DATE))
                .userDomain(motherOfdDragons)
                .build();
        roomDomains.get(2).getBookingDomains().add(bookingDomain2);
        roomDomains.get(3).getBookingDomains().add(bookingDomain2);
        motherOfdDragons.getBookingDomains().add(bookingDomain2);
        BookingDomain bookingDomain3 = BookingDomain.builder()
                .roomDomains(Set.of(roomDomains.get(4)))
                .bookingDates(LocalDate.now().plusDays(4).format(ISO_DATE))
                .userDomain(motherOfdDragons)
                .build();
        roomDomains.get(4).getBookingDomains().add(bookingDomain3);
        motherOfdDragons.getBookingDomains().add(bookingDomain3);
        bookingRepo.saveAll(List.of(bookingDomain1, bookingDomain2, bookingDomain3));

    }

    private List<UserDomain> getUsers() {
        return List.of(new UserDomain(null, "Jhon Snow", new HashSet<>()),
                new UserDomain(null, "Sansa Stark", new HashSet<>()),
                new UserDomain(null, "Daenerys Targaryen", new HashSet<>()),
                new UserDomain(null, "Tyrion Lannister", new HashSet<>()),
                new UserDomain(null, "Cersei Lannister", new HashSet<>()),
                new UserDomain(null, "Ramsy Boltan", new HashSet<>()),
                new UserDomain(null, "Ygritte", new HashSet<>()),
                new UserDomain(null, "Khal Drogo", new HashSet<>()),
                new UserDomain(null, "Arya Stark", new HashSet<>()));
    }

    private List<RoomDomain> getRooms() {
        return List.of(new RoomDomain(null, 1, 100.00, new HashSet<>()),
                new RoomDomain(null, 2, 200.00, new HashSet<>()),
                new RoomDomain(null, 3, 300.00, new HashSet<>()),
                new RoomDomain(null, 4, 400.00, new HashSet<>()),
                new RoomDomain(null, 5, 400.00, new HashSet<>()),
                new RoomDomain(null, 6, 200.00, new HashSet<>()),
                new RoomDomain(null, 7, 300.00, new HashSet<>()),
                new RoomDomain(null, 8, 400.00, new HashSet<>()),
                new RoomDomain(null, 9, 400.00, new HashSet<>()),
                new RoomDomain(null, 10, 500.00, new HashSet<>()));
    }
}
