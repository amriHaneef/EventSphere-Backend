package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.model.Announcement;
import com.example.eventspherebackend.model.Batch;
import com.example.eventspherebackend.model.BatchAnnoun;
import com.example.eventspherebackend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchAnnounService {

    private final BatchAnnounRepository BatchAnnounRepository;
    private final AnnouncementRepository AnnouncementRepository;
    private final BatchRepository batchRepository;

    public BatchAnnounService(BatchAnnounRepository batchAnnounRepository,AnnouncementRepository announcementRepository, BatchRepository batchRepository) {
        BatchAnnounRepository = batchAnnounRepository;
        AnnouncementRepository = announcementRepository;
        this.batchRepository = batchRepository;
    }


    //add batch announcement
    public void addBatchAnnouncements(String announcementId, List<String> batchIds) {
        // Fetch the announcement object once
        Announcement announcement = AnnouncementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));

        // Loop through the list of batch IDs and create BatchAnnoun entries
        for (String batchId : batchIds) {
            Batch batch = batchRepository.findById(batchId)
                    .orElseThrow(() -> new IllegalArgumentException("Batch not found for ID: " + batchId));

            BatchAnnoun batchAnnoun = new BatchAnnoun();
            batchAnnoun.setAnnouncement(announcement);
            batchAnnoun.setBatch(batch);

            // Save the association
            BatchAnnounRepository.save(batchAnnoun);
        }
    }

    //remove batch announcement
    @Transactional
    public void removeBatchAnnouncement(String announcementId, List<String> batchIds) {
        for (String batchId : batchIds) {
            BatchAnnounRepository.deleteByAnnouncementIdAndBatchId(
                    Integer.parseInt(announcementId),
                    Integer.parseInt(batchId)
            );
        }
    }

    //get batches in announcement
    public List<BatchDTO> getBatchesInAnnouncement(int announcementId) {
        List<BatchAnnoun> batchAnnounList = BatchAnnounRepository.findBatchByAnnouncementId(announcementId);

        // Convert the Batch entities to BatchDTOs
        List<BatchDTO> batchDTOList = new java.util.ArrayList<>();
        for (BatchAnnoun batchAnnoun : batchAnnounList) {
            Batch batch = batchAnnoun.getBatch();
            BatchDTO batchDTO = toBatchDTO(batch);
            batchDTOList.add(batchDTO);
        }

        return batchDTOList;
    }

    public static BatchDTO toBatchDTO(Batch batch) {
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
