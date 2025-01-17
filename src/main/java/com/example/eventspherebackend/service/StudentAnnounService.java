package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.model.Announcement;
import com.example.eventspherebackend.model.StudentAnnoun;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.AnnouncementRepository;
import com.example.eventspherebackend.repository.StudentAnnounRepository;
import com.example.eventspherebackend.repository.StudentBatchRepository;
import com.example.eventspherebackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAnnounService {

    private final StudentAnnounRepository StudentAnnounRepository;
    private final AnnouncementRepository AnnouncementRepository;
    final private UserRepository userRepository;

    public StudentAnnounService(StudentAnnounRepository studentAnnounRepository, AnnouncementRepository announcementRepository, UserRepository userRepository) {
        StudentAnnounRepository = studentAnnounRepository;
        AnnouncementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    //add student announcement
    public void addStudentAnnouncement(String announcementId, List<String> studentIds) {
        // Fetch the announcement once
        Announcement announcement = AnnouncementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));

        // Loop through the student IDs and create StudentAnnoun records
        for (String studentId : studentIds) {
            Users student = userRepository.findById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found for ID: " + studentId));

            StudentAnnoun studentAnnoun = new StudentAnnoun();
            studentAnnoun.setAnnouncement(announcement);
            studentAnnoun.setStudent(student);

            StudentAnnounRepository.save(studentAnnoun);
        }
    }

    //remove student announcement
    @Transactional
    public void removeStudentAnnouncement(String announcementId, List<String> studentIds) {
        for (String studentId : studentIds) {
            StudentAnnounRepository.deleteByAnnouncementIdAndStudentId(
                    Integer.parseInt(announcementId),
                    Integer.parseInt(studentId)
            );
        }
    }

    public List<UsersDTO> getStudentsInAnnouncement(int announcementId) {
        List<StudentAnnoun> studentAnnounList = StudentAnnounRepository.findStudentByAnnouncementId(announcementId);

        // Convert the StudentAnnoun entities to UsersDTOs
        List<UsersDTO> usersDTOList = new java.util.ArrayList<>();
        for (StudentAnnoun studentAnnoun : studentAnnounList) {
            Users student = studentAnnoun.getStudent();
            UsersDTO usersDTO = toUserDto(student);
            usersDTOList.add(usersDTO);
        }

        return usersDTOList;
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
}
