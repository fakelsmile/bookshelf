package com.fakelsmile.bookshelf.service.services;

import com.fakelsmile.bookshelf.service.errors.UserNotFoundException;
import com.fakelsmile.bookshelf.service.models.entity.UserEntity;
import com.fakelsmile.bookshelf.service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for users.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Get all the users.
     *
     * @return list of users
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Save a new user.
     *
     * @param user - user
     * @return saved user
     */
    public UserEntity saveUser(UserEntity user) {
        user = userRepository.save(user);
        return user;
    }

    /**
     * Get a user by id.
     *
     * @param id - user id
     * @return found user
     * @throws UserNotFoundException if the user is not found
     */
    public UserEntity getUser(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Update a user by id.
     *
     * @param id - user id
     * @param user - user
     * @return updated user
     * @throws UserNotFoundException if the user is not found
     */
    public UserEntity updateUser(long id,
                                 UserEntity user) throws UserNotFoundException {

        UserEntity foundUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        foundUser.setName(user.getName());
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        foundUser.setRole(user.getRole());
        foundUser = saveUser(foundUser);
        return foundUser;
    }

    /**
     * Delete a user by id.
     *
     * @param id - user id
     */
    public void deleteUser(long id) {
        try {
            userRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
        }
    }
}
