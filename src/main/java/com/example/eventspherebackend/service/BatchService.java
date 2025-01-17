package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.model.Batch;
import com.example.eventspherebackend.repository.BatchRepository;
import com.example.eventspherebackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {
    private final BatchRepository batchRepository;
    private final UserRepository userRepository;

    public BatchService(BatchRepository batchRepository, UserRepository userRepository) {
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
    }

    //add batch
    public void addBatch(BatchDTO batchDTO) {
        Batch batch = toEntity(batchDTO);
        batchRepository.save(batch);
    }

    //get all batches
    public List<BatchDTO> getAllBatches() {

        List<Batch> batches = batchRepository.findAll();
        List<BatchDTO> batchDTO = new ArrayList<>();
        for(Batch batch : batches) {
            batchDTO.add(toDTO(batch));
        }

        return batchDTO;
    }

    //get batch by id
    public BatchDTO getBatchById(String id) {
        Batch batch = batchRepository.findById(id).orElse(null);
        return toDTO(batch);
    }

    //update batch
    public void updateBatch(BatchDTO batchDTO) {
        Batch batch = toEntity(batchDTO);
        batchRepository.save(batch);
    }

    //delete batch
    public void deleteBatch(String id) {
        batchRepository.deleteById(id);
    }

    public BatchDTO getBatchByConsultantId(String consultantId) {
        Batch batch = batchRepository.findByConsultantId(Integer.parseInt(consultantId));
        return toDTO(batch);
    }

    public Batch toEntity(BatchDTO dto) {
        Batch batch = new Batch();
        batch.setName(dto.getName());
        batch.setConsultant(userRepository.findById(String.valueOf(dto.getConsultantId())).orElse(null));
        batch.setStartDate(dto.getStartDate());
        batch.setUpdatedAt(dto.getUpdatedAt());
        batch.setStatus(dto.getStatus());

        if(String.valueOf(dto.getId()) != null) {
            batch.setId(dto.getId());
        }

        return batch;
    }


    public static BatchDTO toDTO(Batch batch) {
        BatchDTO dto = new BatchDTO();
        dto.setId(batch.getId());
        dto.setName(batch.getName());
        dto.setConsultantId(batch.getConsultant().getId());
        dto.setConsultantName(batch.getConsultant().getName());
        dto.setStartDate(batch.getStartDate());
        dto.setCreatedAt(batch.getCreatedAt());
        dto.setUpdatedAt(batch.getUpdatedAt());
        dto.setStatus(batch.getStatus());

        return dto;
    }


}
