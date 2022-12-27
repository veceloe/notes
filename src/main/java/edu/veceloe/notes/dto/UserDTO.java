package edu.veceloe.notes.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов.")
    @NotEmpty(message = "Поле имени не должно быть пустым")
    private String firstName;
    @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов")
    @NotEmpty(message = "Поле фамилии не должно быть пустым")
    private String lastName;
    @NotEmpty(message = "Поле почты не должно быть пустым")
    @Email
    private String email;
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%.;!:]).{6,20})",
            message = "Введите корректный пароль")
    @NotEmpty(message = "Поле пароля не должно быть пустым")
    private String password;
    @NotEmpty(message = "Поле пароля не должно быть пустым")
    private String passwordConfirm;

    private Boolean passwordsEquals;

    public Boolean isPasswordsEquals() {
        System.out.println(password != null && password.equals(passwordConfirm));
        return password != null && password.equals(passwordConfirm);
    }

    @AssertTrue(message = "Пароли не совпадают.")
    public Boolean getPasswordsEquals(){
        return isPasswordsEquals();
    }
}