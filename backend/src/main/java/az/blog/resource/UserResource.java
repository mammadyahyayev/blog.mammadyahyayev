package az.blog.resource;

import az.blog.domain.dto.UserDTO;
import az.blog.domain.entity.User;
import az.blog.repository.UserRepository;
import az.blog.resource.errors.BadRequestException;
import az.blog.resource.errors.Operation;
import az.blog.resource.errors.UserAlreadyExistException;
import az.blog.resource.vm.LoginVM;
import az.blog.resource.vm.OperationResult;
import az.blog.resource.vm.UserResponseVM;
import az.blog.security.JwtSecurityConstant;
import az.blog.security.TokenProvider;
import az.blog.service.UserService;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public UserResource(UserService userService, UserRepository userRepository,
                        AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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
    public ResponseEntity<List<UserResponseVM>> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }

        List<UserResponseVM> responseUsers = users.stream().map(user -> {
            UserResponseVM responseVM = new UserResponseVM();
            responseVM.setFirstname(user.getFirstname());
            responseVM.setLastname(user.getLastname());
            responseVM.setUsername(user.getUsername());
            responseVM.setEmail(user.getEmail());
            return responseVM;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(responseUsers, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseVM> getIndividualUser(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("id", "Id cannot be null or less than a zero");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        User user = optionalUser.get();
        UserResponseVM userResponse = new UserResponseVM();
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    //TODO: Create a methods that allow update password, then come back
    // add that method documentation in here

    /**
     * Update user properties except password
     */
    @PutMapping("/user")
    public ResponseEntity<OperationResult> updateUser(@Valid @RequestBody UserDTO userDTO) {
        if (userDTO.getId() == null || userDTO.getId() <= 0) {
            throw new BadRequestException("id", "Id cannot be null or less than or equal to zero");
        }

        userService.updateUser(userDTO);
        return new ResponseEntity<>(Operation.updatedSuccessfully("User updated"), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<OperationResult> deleteUser(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("id", "Id cannot be null or less than a zero");
        }

        userService.deleteUser(id);
        return new ResponseEntity<>(Operation.deletedSuccessfully("User deleted"), HttpStatus.OK);
    }

    @PostMapping("/user/authenticate")
    public ResponseEntity<JwtToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = TokenProvider.generateToken(loginVM.getUsername());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtSecurityConstant.AUTHORIZATION, "Bearer " + token);
        return new ResponseEntity<>(new JwtToken(token), httpHeaders, HttpStatus.OK);
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

    static class JwtToken {
        private final String token;

        JwtToken(String idToken) {
            this.token = idToken;
        }

        public String getToken() {
            return token;
        }
    }
}
