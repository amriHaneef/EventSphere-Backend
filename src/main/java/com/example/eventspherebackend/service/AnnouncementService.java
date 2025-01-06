package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.AnnouncementDTO;
import com.example.eventspherebackend.model.Announcement;
import com.example.eventspherebackend.model.BatchAnnoun;
import com.example.eventspherebackend.model.StudentAnnoun;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.*;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementService {
    final private AnnouncementRepository AnnouncementRepository;
    final private StudentAnnounRepository StudentAnnounRepository;
    final private BatchAnnounRepository BatchAnnounRepository;
    final private StudentBatchRepository StudentBatchRepository;
    final private UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, StudentAnnounRepository studentAnnounRepository, BatchAnnounRepository batchAnnounRepository, StudentBatchRepository studentBatchRepository, UserRepository userRepository) {
        AnnouncementRepository = announcementRepository;
        StudentAnnounRepository = studentAnnounRepository;
        BatchAnnounRepository = batchAnnounRepository;
        StudentBatchRepository = studentBatchRepository;
        this.userRepository = userRepository;
    }

    public List<AnnouncementDTO> getAllAnnouncements() {
        String role = JwtUtil.role;
        if (role.equals("Admin")) {
            List<Announcement> announcements = AnnouncementRepository.findAll();;
            List<AnnouncementDTO> announcementDTO = new ArrayList<>();
            for(Announcement announcement : announcements) {
                announcementDTO.add(toDTO(announcement));
            }

            return announcementDTO;
        } else if (role.equals("Teacher")) {
            List<Announcement> announcements = AnnouncementRepository.findByCreatedBy(JwtUtil.username);
            List<AnnouncementDTO> announcementDTO = new ArrayList<>();
            for(Announcement announcement : announcements) {
                announcementDTO.add(toDTO(announcement));
            }

            return announcementDTO;
        } else {
            List<StudentAnnoun> announcements = StudentAnnounRepository.findByStudentUsername(JwtUtil.username);
            List<BatchAnnoun> batchAnnouncements = BatchAnnounRepository.findByBatchId(StudentBatchRepository.findByStudentUsername(JwtUtil.username).getBatch().getId());

            List<AnnouncementDTO> announcementDTO = new ArrayList<>();
            for(StudentAnnoun announcement : announcements) {
                announcementDTO.add(toDTO(announcement.getAnnouncement()));
            }
            for (BatchAnnoun announcement : batchAnnouncements) {
                announcementDTO.add(toDTO(announcement.getAnnouncement()));
            }

            return announcementDTO;

        }

    }

    //add a new announcement
    public void addAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setCreatedBy(JwtUtil.username);
        announcement.setRole(JwtUtil.role);

        AnnouncementRepository.save(announcement);
    }


    public static AnnouncementDTO toDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setCreatedBy(announcement.getCreatedBy());
        dto.setRole(announcement.getRole());

        return dto;
    }
}
