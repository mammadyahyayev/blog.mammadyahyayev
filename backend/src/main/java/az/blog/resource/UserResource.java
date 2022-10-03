package az.blog.resource;

import az.blog.domain.dto.UserDTO;
import az.blog.repository.UserRepository;
import az.blog.resource.errors.Operation;
import az.blog.resource.errors.UserAlreadyExistException;
import az.blog.resource.vm.OperationResult;
import az.blog.resource.vm.UserResponseVM;
import az.blog.service.UserService;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final UserRepository userRepository;

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<OperationResult> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request for create user : {}", userDTO);
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User with " + userDTO.getUsername() + " is exist");
        } else {
            userService.createUser(userDTO);
            return new ResponseEntity<>(Operation.createdSuccessfully("User created"), HttpStatus.CREATED);
        }
    }

    @GetMapping("/users")
    public List<UserResponseVM> getAllUsers() {
        return Collections.emptyList();
    }

    @GetMapping("/user/{id}")
    public UserResponseVM getIndividualUser(@PathVariable("id") Long id) {
        return null;
    }

    //TODO: Create a methods that allow update password, then come back
    // add that method documentation in here

    /**
     * Update user properties except password
     */
    @PutMapping("/user")
    public void updateUser(@Valid @RequestBody UserDTO userDTO) {

    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long id) {

    }

    /**
     * Search users based on some criteria
     *
     * @throws NotImplementedException until ElasticSearch configured
     */
    public void searchUser() {
        throw new NotImplementedException();
    }

    /**
     * It allows for granting user role, only admins can do this process
     */
    public void grantUserRole() {
        throw new NotImplementedException();
    }
}
