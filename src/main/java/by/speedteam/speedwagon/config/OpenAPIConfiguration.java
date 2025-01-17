package by.speedteam.speedwagon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.description("Speed Wagon");

        Info information = new Info()
                .title("Speed Wagon API")
                .version("1.0")
                .description("This API provides endpoints for Speed Wagon application.");

        return new OpenAPI()
                .info(information)
                .servers(List.of(server));
    }
}
