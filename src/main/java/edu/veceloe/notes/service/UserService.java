package edu.veceloe.notes.service;

import edu.veceloe.notes.dto.UserDTO;
import edu.veceloe.notes.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
