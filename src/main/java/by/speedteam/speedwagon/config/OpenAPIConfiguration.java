package by.speedteam.speedwagon.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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

//        Components components = new Components();
//        components.addSecuritySchemes("bearer-key", new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER)
//                .name("Authorization")
//        );
//
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearer-key");
//
//        return new OpenAPI()
//                .info(information)
//                .servers(List.of(server))
//                .components(components)
//                .security(List.of(securityRequirement));

        return new OpenAPI()
                .info(information)
                .servers(List.of(server));
    }
}
