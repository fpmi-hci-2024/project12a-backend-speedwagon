package by.speedteam.speedwagon;

import by.speedteam.speedwagon.models.EPaymentMethod;
import by.speedteam.speedwagon.models.PaymentMethod;
import by.speedteam.speedwagon.models.ERole;
import by.speedteam.speedwagon.models.Role;
import by.speedteam.speedwagon.payload.requests.RegisterRequest;
import by.speedteam.speedwagon.services.PaymentMethodService;
import by.speedteam.speedwagon.services.RoleService;
import by.speedteam.speedwagon.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpeedWagonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpeedWagonApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserService userService,
                           RoleService roleService,
                           PaymentMethodService paymentMethodService) {
        return args -> {
            // Создаем роли
            roleService.createRole(new Role(ERole.ROLE_USER));
            roleService.createRole(new Role(ERole.ROLE_DISPATCHER));

            // Создаем пользователей
            for (int i = 1; i <= 5; i++) {
                String phone = "+" + String.valueOf(i).repeat(12);
                RegisterRequest registerRequest = new RegisterRequest(
                        "user" + i + "@example.com",
                        "password" + i,
                        phone,
                        "Firstname" + i,
                        "Lastname" + i,
                        "Surname" + i
                );
                userService.registerUser(registerRequest);
            }

            // Создаем способы оплаты
            paymentMethodService.createPaymentMethod(new PaymentMethod(EPaymentMethod.PAYMENT_METHOD_CARD));
            paymentMethodService.createPaymentMethod(new PaymentMethod(EPaymentMethod.PAYMENT_METHOD_CASH));

            // ...




        };
    }
}
