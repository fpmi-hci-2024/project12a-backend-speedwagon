package by.speedteam.speedwagon;

import by.speedteam.speedwagon.models.*;
import by.speedteam.speedwagon.payload.requests.reservations.CreateReservationRequest;
import by.speedteam.speedwagon.payload.requests.users.RegisterRequest;
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
            userService.registerUser(new RegisterRequest(
                    "admin1@gmail.com", "123456789", "+375296850298",
                    "Костецкий", "Павел", "Сергеевич"));
            userService.registerUser(new RegisterRequest(
                    "admin2@gmail.com", "123456790", "+375448658619",
                    "Гулин", "Егор", "Николаевич"));
            userService.registerUser(new RegisterRequest(
                    "admin3@gmail.com", "123456791", "+375295481650",
                    "Дёмин", "Владимир", "Олегович"));
            userService.registerUser(new RegisterRequest(
                    "passenger1@gmail.com", "Jgdsyasp1", "+375442347019",
                    "Петрачков", "Степан", "Леонидович"));
            userService.registerUser(new RegisterRequest(
                    "passenger2@gmail.com", "Ngfdsu21", "+375448260091",
                    "Петрачков", "Василий", "Леонидович"));
            userService.registerUser(new RegisterRequest(
                    "passenger3@gmail.com", "55Kgfipa", "+375445551841",
                    "Петрачков", "Андрей", "Павлович"));
            userService.registerUser(new RegisterRequest(
                    "passenger4@gmail.com", "JAJA228", "+375449090412",
                    "Петрачков", "Арсений", "Михайлович"));
            userService.registerUser(new RegisterRequest(
                    "passenger5@gmail.com", "1337MOSA", "+375298766592",
                    "Петрачков", "Артем", "Сергеевич"));
            userService.registerUser(new RegisterRequest(
                    "passenger6@gmail.com", "Mlautgausten", "+375290566911",
                    "Петрачков", "Ярослав", "Евгеньевич"));
            userService.registerUser(new RegisterRequest(
                    "passenger7@gmail.com", "Mhusdfj0Log", "+375298512984",
                    "Петрачков", "Илья", "Васильевич"));

            // 3. Назначаем роль админа пользователям
            userService.assignAdminRole(1);
            userService.assignAdminRole(2);
            userService.assignAdminRole(3);

            // 4. Создаем способы оплаты
            paymentMethodService.createPaymentMethod(new PaymentMethod(EPaymentMethod.PAYMENT_METHOD_CARD));
            paymentMethodService.createPaymentMethod(new PaymentMethod(EPaymentMethod.PAYMENT_METHOD_CASH));

            // 5. Создаем остановки
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

            // 6. Создаем маршруты
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

            // 7. Создаем автобусы
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

            // 8. Создаем рейсы
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

            // 9. Создаем бронирования
            reservationService.createReservation(new CreateReservationRequest(
                    4, 1, EPaymentMethod.PAYMENT_METHOD_CARD,
                    1, 1, "Предпочитаю сидеть у окна"));

            reservationService.createReservation(new CreateReservationRequest(
                    5, 1, EPaymentMethod.PAYMENT_METHOD_CASH,
                    2, 1, ""));

            reservationService.createReservation(new CreateReservationRequest(
                    6, 1, EPaymentMethod.PAYMENT_METHOD_CARD,
                    3, 1, "Хочу у окна"));

            reservationService.createReservation(new CreateReservationRequest(
                    7, 1, EPaymentMethod.PAYMENT_METHOD_CASH,
                    4, 1, ""));

            reservationService.createReservation(new CreateReservationRequest(
                    8, 4, EPaymentMethod.PAYMENT_METHOD_CARD,
                    10, 1, "Буду с собакой"));

            reservationService.createReservation(new CreateReservationRequest(
                    9, 4, EPaymentMethod.PAYMENT_METHOD_CASH,
                    10, 1, ""));

            reservationService.createReservation(new CreateReservationRequest(
                    10, 4, EPaymentMethod.PAYMENT_METHOD_CARD,
                    11, 1, "Мне нужно одиночное место у выхода"));
        };
    }
}
