package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.dto.StudentBatchDTO;
import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.service.BatchService;
import com.example.eventspherebackend.service.StudentBatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final BatchService batchService;
    private final StudentBatchService studentBatchService;

    public BatchController(BatchService batchService, StudentBatchService studentBatchService) {
        this.batchService = batchService;
        this.studentBatchService = studentBatchService;
    }

    @GetMapping("/getAll")
    public List<BatchDTO> getAllBatches() {
        return batchService.getAllBatches();
    }

    @GetMapping("/getBatchById")
    public BatchDTO getBatchById(@RequestParam("batchId") String id) {
        return batchService.getBatchById(id);
    }

    @GetMapping("/getBatchByConsultantId")
    public BatchDTO getBatchByConsultantId(@RequestParam("consultantId") String consultantId) {
        return batchService.getBatchByConsultantId(consultantId);
    }

    @PostMapping("/add")
    public String addBatch(@RequestBody BatchDTO batchDTO) {
        batchService.addBatch(batchDTO);
        return "Batch added successfully";
    }

    @PostMapping("/addStudent")
    public String addStudentToBatch(@RequestBody StudentBatchDTO studentBatchDTO) {
        studentBatchService.addStudentToBatch(studentBatchDTO.getBatchId(), studentBatchDTO.getStudentIds());
        return "Student added to batch successfully";
    }

    @PostMapping("/removeStudent")
    public String removeStudentFromBatch(@RequestBody StudentBatchDTO studentBatchDTO) {
        studentBatchService.removeStudentFromBatch(studentBatchDTO.getBatchId(), studentBatchDTO.getStudentIds());
        return "Student removed from batch successfully";
    }

    @GetMapping("/getStudents")
    public List<UsersDTO> getStudentsInBatch(@RequestParam("batchId") String batchId) {
        return studentBatchService.getStudentsInBatch(batchId);
    }

    @PostMapping("/update")
    public String updateBatch(@RequestBody BatchDTO batchDTO) {
        batchService.updateBatch(batchDTO);
        return "Batch updated successfully";
    }

    @PostMapping("/delete")
    public String deleteBatch(@RequestParam("batchId") String id) {
        batchService.deleteBatch(id);
        return "Batch deleted successfully";
    }

}
