package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Announcement;
import com.example.eventspherebackend.model.StudentAnnoun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnnounRepository extends JpaRepository<StudentAnnoun, String> {
    List<StudentAnnoun> findByStudentUsername(String username);

    void deleteByAnnouncementIdAndStudentId(int announId, int studentId);

    List<StudentAnnoun> findStudentByAnnouncementId(int announcementId);
}
