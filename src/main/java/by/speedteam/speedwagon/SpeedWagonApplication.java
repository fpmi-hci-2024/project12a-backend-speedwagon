package by.speedteam.speedwagon;

import by.speedteam.speedwagon.models.*;
import by.speedteam.speedwagon.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class SpeedWagonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpeedWagonApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleService roleService,
                           UserService userService,
                           PaymentMethodService paymentMethodService,
                           BusStopService busStopService,
                           RouteService routeService,
                           BusService busService,
                           TripService tripService,
                           ReservationService reservationService) {
        return args -> {
            // 1. Создаем роли
            roleService.createRole(new Role(ERole.ROLE_USER));
            roleService.createRole(new Role(ERole.ROLE_ADMIN));

            // 2. Создаем пользователей
            userService.createUser(new User(
                    "admin1@gmail.com", "123456789", "+375296850298",
                    "Костецкий", "Павел", "Сергеевич", roleService.getRoleById(1).get()));
            userService.createUser(new User(
                    "admin2@gmail.com", "123456790", "+375448658619",
                    "Гулин", "Егор", "Николаевич", roleService.getRoleById(1).get()));
            userService.createUser(new User(
                    "admin3@gmail.com", "123456791", "+375295481650",
                    "Дёмин", "Владимир", "Олегович", roleService.getRoleById(1).get()));
            userService.createUser(new User(
                    "passenger1@gmail.com", "Jgdsyasp1", "+375442347019",
                    "Петрачков", "Степан", "Леонидович", roleService.getRoleById(2).get()));
            userService.createUser(new User(
                    "passenger2@gmail.com", "Ngfdsu21", "+375448260091",
                    "Петрачков", "Василий", "Леонидович", roleService.getRoleById(2).get()));
            userService.createUser(new User(
                    "passenger3@gmail.com", "55Kgfipa", "+375445551841",
                    "Петрачков", "Андрей", "Павлович", roleService.getRoleById(2).get()));
            userService.createUser(new User(
                    "passenger4@gmail.com", "JAJA228", "+375449090412",
                    "Петрачков", "Арсений", "Михайлович", roleService.getRoleById(2).get()));
            userService.createUser(new User(
                    "passenger5@gmail.com", "1337MOSA", "+375298766592",
                    "Петрачков", "Артем", "Сергеевич", roleService.getRoleById(2).get()));
            userService.createUser(new User(
                    "passenger6@gmail.com", "Mlautgausten", "+375290566911",
                    "Петрачков", "Ярослав", "Евгеньевич", roleService.getRoleById(2).get()));
            userService.createUser(new User(
                    "passenger7@gmail.com", "Mhusdfj0Log", "+375298512984",
                    "Петрачков", "Илья", "Васильевич", roleService.getRoleById(2).get()));

            // 3. Создаем способы оплаты
            paymentMethodService.createPaymentMethod(new PaymentMethod(EPaymentMethod.PAYMENT_METHOD_CARD));
            paymentMethodService.createPaymentMethod(new PaymentMethod(EPaymentMethod.PAYMENT_METHOD_CASH));

            // 4. Создаем остановки
            busStopService.createBusStop(new BusStop(
                    "Минск", "Улица Кирова, 8, парковка 2", false));
            busStopService.createBusStop(new BusStop(
                    "Минск", "Улица Ленина, 15", false));
            busStopService.createBusStop(new BusStop(
                    "Минск", "Проспект Дзержинского, 100", true));
            busStopService.createBusStop(new BusStop(
                    "Минск", "Улица Немига, 3", false));
            busStopService.createBusStop(new BusStop(
                    "Гродно", "Улица Советская, 25", true));
            busStopService.createBusStop(new BusStop(
                    "Гродно", "Улица Горького, 18", false));
            busStopService.createBusStop(new BusStop(
                    "Гродно", "Проспект Янки Купалы, 45", true));
            busStopService.createBusStop(new BusStop(
                    "Гродно", "Улица Замковая, 10", false));
            busStopService.createBusStop(new BusStop(
                    "Брест", "Проспект Машерова, 30", true));
            busStopService.createBusStop(new BusStop(
                    "Брест", "Улица Гоголя, 7", false));
            busStopService.createBusStop(new BusStop(
                    "Брест", "Улица Пушкинская, 22", true));
            busStopService.createBusStop(new BusStop(
                    "Брест", "Проспект Республики, 12", false));
            busStopService.createBusStop(new BusStop(
                    "Могилев", "Проспект Мира, 60", true));
            busStopService.createBusStop(new BusStop(
                    "Могилев", "Улица Первомайская, 5", false));
            busStopService.createBusStop(new BusStop(
                    "Могилев", "Улица Гагарина, 30", true));
            busStopService.createBusStop(new BusStop(
                    "Могилев", "Улица Кирова, 20", true));

            // 5. Создаем маршруты
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(1).get(), busStopService.getBusStopById(5).get(), "[1, 2, 3, 4]"));
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(5).get(), busStopService.getBusStopById(1).get(), "[5, 6, 7, 8]"));
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(1).get(), busStopService.getBusStopById(9).get(), "[1, 2, 3]"));
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(9).get(), busStopService.getBusStopById(1).get(), "[10, 11, 12, 13]"));
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(9).get(), busStopService.getBusStopById(5).get(), "[10, 11, 12]"));
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(5).get(), busStopService.getBusStopById(9).get(), "[5, 6, 7]"));
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(1).get(), busStopService.getBusStopById(16).get(), "[1, 2, 3, 4]"));
            routeService.createRoute(new Route(
                    busStopService.getBusStopById(16).get(), busStopService.getBusStopById(1).get(), "[16, 15, 14, 13]"));

            // 6. Создаем автобусы
            busService.createBus(new Bus(
                    "АК 1234-6", "Сметанов Игнат Васильевич",
                    15, "Mercedes", "Белый"));
            busService.createBus(new Bus("АВ 2654-6", "Васильков Павел Олегович",
                    15, "Volkswagen", "Черный"));
            busService.createBus(new Bus("АВ 3479-6", "Соловьев Илья Евгеньевич",
                    15, "Mercedes", "Белый"));
            busService.createBus(new Bus("АВ 9901-6", "Соловьев Илья Евгеньевич",
                    15, "Mercedes", "Белый"));
            busService.createBus(new Bus("АК 1858-6", "Несметых Юрий Олегович",
                    15, "Volkswagen", "Синий"));
            busService.createBus(new Bus("АК 8830-6", "Головьев Виталий Юрьевич",
                    15, "Mercedes", "Черный"));
            busService.createBus(new Bus("АВ 7899-6", "Новиков Михаил Степанович",
                    15, "Volkswagen", "Синий"));

            // 7. Создаем поездки
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АК 1234-6").get(),
                    routeService.getRouteById(1).get(),
                    15, 11,
                    LocalDate.of(2024, 12, 27),
                    LocalTime.of(9, 0), 15));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АВ 2654-6").get(),
                    routeService.getRouteById(2).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 26),
                    LocalTime.of(15, 0), 20));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АВ 3479-6").get(),
                    routeService.getRouteById(3).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 27),
                    LocalTime.of(12, 0), 18));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АВ 9901-6").get(),
                    routeService.getRouteById(4).get(),
                    15, 12,
                    LocalDate.of(2024, 12, 28),
                    LocalTime.of(10, 30), 22));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АК 1858-6").get(),
                    routeService.getRouteById(5).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 29),
                    LocalTime.of(16, 0), 17));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АК 8830-6").get(),
                    routeService.getRouteById(6).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 30),
                    LocalTime.of(8, 15), 25));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АВ 7899-6").get(),
                    routeService.getRouteById(7).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 31),
                    LocalTime.of(19, 0), 30));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АК 1234-6").get(),
                    routeService.getRouteById(8).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 25),
                    LocalTime.of(11, 0), 14));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АВ 2654-6").get(),
                    routeService.getRouteById(6).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 26),
                    LocalTime.of(14, 0), 16));
            tripService.createTrip(new Trip(
                    busService.getBusByNumber("АВ 3479-6").get(),
                    routeService.getRouteById(7).get(),
                    15, 15,
                    LocalDate.of(2024, 12, 27),
                    LocalTime.of(20, 0), 19));

            // 8. Создаем бронирования
            reservationService.createReservation(new Reservation(
                    userService.getUserById(4).get(),
                    tripService.getTripById(1).get(),
                    paymentMethodService.getPaymentMethodById(1).get(),
                    busStopService.getBusStopById(1).get(),
                    1,
                    "Предпочитаю сидеть у окна"
            ));
            reservationService.createReservation(new Reservation(
                    userService.getUserById(5).get(),
                    tripService.getTripById(1).get(),
                    paymentMethodService.getPaymentMethodById(2).get(),
                    busStopService.getBusStopById(2).get(),
                    1,
                    ""
            ));
            reservationService.createReservation(new Reservation(
                    userService.getUserById(6).get(),
                    tripService.getTripById(1).get(),
                    paymentMethodService.getPaymentMethodById(1).get(),
                    busStopService.getBusStopById(3).get(),
                    1,
                    "Хочу у окна"
            ));
            reservationService.createReservation(new Reservation(
                    userService.getUserById(7).get(),
                    tripService.getTripById(1).get(),
                    paymentMethodService.getPaymentMethodById(2).get(),
                    busStopService.getBusStopById(4).get(),
                    1,
                    ""
            ));
            reservationService.createReservation(new Reservation(
                    userService.getUserById(8).get(),
                    tripService.getTripById(4).get(),
                    paymentMethodService.getPaymentMethodById(1).get(),
                    busStopService.getBusStopById(10).get(),
                    1,
                    "Буду с собакой"
            ));
            reservationService.createReservation(new Reservation(
                    userService.getUserById(9).get(),
                    tripService.getTripById(4).get(),
                    paymentMethodService.getPaymentMethodById(2).get(),
                    busStopService.getBusStopById(10).get(),
                    1,
                    ""
            ));
            reservationService.createReservation(new Reservation(
                    userService.getUserById(10).get(),
                    tripService.getTripById(4).get(),
                    paymentMethodService.getPaymentMethodById(1).get(),
                    busStopService.getBusStopById(11).get(),
                    1,
                    "Мне нужно одиночное место у выхода"
            ));
        };
    }
}
