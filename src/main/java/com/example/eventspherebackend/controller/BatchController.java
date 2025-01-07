package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.service.BatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
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
