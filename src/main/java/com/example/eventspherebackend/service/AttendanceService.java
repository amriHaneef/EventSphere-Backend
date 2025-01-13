package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.AttendenceDTO;
import com.example.eventspherebackend.model.Attendance;
import com.example.eventspherebackend.repository.AttendanceRepository;
import com.example.eventspherebackend.repository.EventRepository;
import com.example.eventspherebackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(com.example.eventspherebackend.repository.EventRepository eventRepository, UserRepository userRepository, AttendanceRepository attendanceRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
    }

    //Mark attendance
    public void markAttendance(List<AttendenceDTO> attendenceDTO) {
        for(AttendenceDTO attendence : attendenceDTO) {
            Attendance attendance = new Attendance();
            attendance.setMarks(attendence.getMarks());
            attendance.setEvent(eventRepository.findById(String.valueOf(attendence.getEventId())).orElse(null));
            attendance.setStudent(userRepository.findById(String.valueOf(attendence.getStudentId())).orElse(null));
            attendance.setAttendanceStatus(attendence.getAttendanceStatus());
            attendanceRepository.save(attendance);
        }
    }

    //Update attendance
    public void updateAttendance(List<AttendenceDTO> attendenceDTO) {
        for(AttendenceDTO attendenceDto : attendenceDTO) {
            Attendance attendance = new Attendance();
            attendance.setId(attendenceDto.getId());
            attendance.setMarks(attendenceDto.getMarks());
            attendance.setEvent(eventRepository.findById(String.valueOf(attendenceDto.getEventId())).orElse(null));
            attendance.setStudent(userRepository.findById(String.valueOf(attendenceDto.getStudentId())).orElse(null));
            attendance.setAttendanceStatus(attendenceDto.getAttendanceStatus());
            attendanceRepository.save(attendance);
        }
    }

    //Get attendance for an event
    public List<AttendenceDTO> getAttendanceForEvent(String eventId) {
        List<Attendance> attendanceList = attendanceRepository.findByEventId(Integer.parseInt(eventId));
        List<AttendenceDTO> attendanceDTOList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            AttendenceDTO attendanceDTO = new AttendenceDTO();
            attendanceDTO.setId(attendance.getId());
            attendanceDTO.setEventId(attendance.getEvent().getId());
            attendanceDTO.setStudentId(attendance.getStudent().getId());
            attendanceDTO.setAttendanceStatus(attendance.getAttendanceStatus());
            attendanceDTOList.add(attendanceDTO);
        }
        return attendanceDTOList;
    }


}
