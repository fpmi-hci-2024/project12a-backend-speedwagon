package by.speedteam.speedwagon.payload.requests.users;

public class UpdateUserRequest extends RegisterRequest {

    public UpdateUserRequest(String email, String password, String phone,
                             String firstname, String lastname, String surname) {
        super(email, password, phone, firstname, lastname, surname);
    }
}
