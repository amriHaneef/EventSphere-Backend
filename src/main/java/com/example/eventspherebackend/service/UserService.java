package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.PortfolioDTO;
import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.model.Portfolio;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.PortfolioRepository;
import com.example.eventspherebackend.repository.UserRepository;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, PortfolioRepository portfolioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = userRepository.findByUsername(username);
        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

        return userDetails;
    }

    //add user
    public void registerUser(UsersDTO usersDto) {
        Users users = toUserEntity(usersDto);
        userRepository.save(users);

        createPortfolio(users);

    }

    //update user
    public Users updateUser(UsersDTO usersDto) {
        // Fetch the existing user from the database
        Users existingUser = userRepository.findById(String.valueOf(usersDto.getId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + usersDto.getId()));

        // Update fields from the DTO, preserving existing data
        if (usersDto.getUsername() != null) {
            existingUser.setUsername(usersDto.getUsername());
        }

        if (usersDto.getPassword() != null && !usersDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        }

        if (usersDto.getRole() != null) {
            existingUser.setRole(usersDto.getRole());
        }

        if (usersDto.getName() != null) {
            existingUser.setName(usersDto.getName());
        }

        if (usersDto.getEmail() != null) {
            existingUser.setEmail(usersDto.getEmail());
        }

        if (usersDto.getDob() != null) {
            existingUser.setDob(usersDto.getDob());
        }

        if (usersDto.getAge() != null) {
            existingUser.setAge(usersDto.getAge());
        }

        if (usersDto.getStatus() != null) {
            existingUser.setStatus(usersDto.getStatus());
        }

        // Always update the updatedAt field
        existingUser.setUpdatedAt(LocalDateTime.now());

        // Save the updated entity
        return userRepository.save(existingUser);
    }

    //remove user
    public void removeUser(String id) {
        userRepository.deleteById(id);
    }

    //get user by id
    public UsersDTO getUserById(String id) {
        Users user = userRepository.findById(id).orElse(null);
        return toUserDto(user);
    }

    //get all users
    public List<UsersDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    //verify user
    public String verifyUser(String username, String password) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {

                System.out.println(authentication.getPrincipal());
                String role = getUserRole(username);
                String token = jwtUtil.generateToken(username,role);

                System.out.println("Role: " + role);

                return token;
            }
        } catch (Exception e) {

            return "bad credentials"; // Handle exceptions (e.g., bad credentials)
        }
        return "fail";
    }

    //get user role
    public String getUserRole(String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user.getRole();
    }

    //get user by username
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //portfolio creation
    public void createPortfolio (Users user) {
        Portfolio portfolio = new Portfolio();
        portfolio.setStudent(getUserByUsername(user.getUsername()));
        portfolioRepository.save(portfolio);
    }

    //update portfolio
    public void updatePortfolio(PortfolioDTO portfolioDTO) {
        Portfolio portfolio = toPortEntity(portfolioDTO, getUserByUsername(portfolioDTO.getStudentName()));
        portfolioRepository.save(portfolio);
    }

    //get portfolio by student id
    public PortfolioDTO getPortfolioByStudentId(int studentId) {
        Portfolio portfolio = portfolioRepository.findByStudentId(studentId);
        return toPortDto(portfolio);
    }


    //convert entity to dto
    public UsersDTO toUserDto(Users user) {
        if (user == null) {
            return null;
        }

        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setDob(user.getDob());
        dto.setAge(user.getAge());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    //convert dto to entity
    public Users toUserEntity(UsersDTO dto) {
        if (dto == null) {
            return null;
        }

        Users user = new Users();

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if(String.valueOf(dto.getId()) != null) {
            user.setId(dto.getId());
        }

        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setDob(dto.getDob());
        user.setAge(dto.getAge());
        user.setStatus(dto.getStatus());
//        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        return user;
    }

    //convert portfolio entity to dto
    public PortfolioDTO toPortDto(Portfolio portfolio) {
        if (portfolio == null) {
            return null;
        }

        PortfolioDTO dto = new PortfolioDTO();
        dto.setId(portfolio.getId());
        dto.setStudentId(portfolio.getStudent() != null ? portfolio.getStudent().getId() : null);
        dto.setStudentName(portfolio.getStudent() != null ? portfolio.getStudent().getName() : null);
        dto.setAchievements(portfolio.getAchievements());
        dto.setProjects(portfolio.getProjects());
        dto.setSkills(portfolio.getSkills());
        dto.setCertifications(portfolio.getCertifications());
        dto.setGpa(portfolio.getGpa());
        dto.setCreatedAt(portfolio.getCreatedAt());
        dto.setUpdatedAt(portfolio.getUpdatedAt());

        return dto;
    }

    //convert portfolio dto to entity
    public Portfolio toPortEntity(PortfolioDTO dto, Users student) {
        if (dto == null) {
            return null;
        }

        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getId());
        portfolio.setStudent(student); // Assign the student entity
        portfolio.setAchievements(dto.getAchievements());
        portfolio.setProjects(dto.getProjects());
        portfolio.setSkills(dto.getSkills());
        portfolio.setCertifications(dto.getCertifications());
        portfolio.setGpa(dto.getGpa());
        portfolio.setCreatedAt(dto.getCreatedAt());
        portfolio.setUpdatedAt(dto.getUpdatedAt());

        return portfolio;
    }


    public List<Users> getUserByRole(String role) {
        return userRepository.findByRole(role);
    }
}
