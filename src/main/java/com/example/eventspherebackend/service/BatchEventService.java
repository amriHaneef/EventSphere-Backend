package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.model.Batch;
import com.example.eventspherebackend.model.BatchEvent;
import com.example.eventspherebackend.repository.BatchEventRepository;
import com.example.eventspherebackend.repository.BatchRepository;
import com.example.eventspherebackend.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchEventService {
    final private EventRepository EventRepository;
    final private BatchEventRepository BatchEventRepository;
    final private BatchRepository BatchRepository;

    public BatchEventService(com.example.eventspherebackend.repository.EventRepository eventRepository, com.example.eventspherebackend.repository.BatchEventRepository batchEventRepository, com.example.eventspherebackend.repository.BatchRepository batchRepository) {
        EventRepository = eventRepository;
        BatchEventRepository = batchEventRepository;
        BatchRepository = batchRepository;
    }

    // assign a batch to an event
    public void assignBatch(String eventId, String batchId) {

        BatchEvent batchEvent = new BatchEvent();
        batchEvent.setEvent(EventRepository.findById(eventId).orElse(null));
        batchEvent.setBatch(BatchRepository.findById(batchId).orElse(null));
        BatchEventRepository.save(batchEvent);
    }

    // get all batches assigned to an event
    public List<BatchDTO> getAsignedBatches(int id) {
        List<BatchEvent> batchEventList = BatchEventRepository.findBatchByEventId(id);

        // Convert the Batch entities to BatchDTOs
        List<BatchDTO> batchDTOList = new ArrayList<>();
        for (BatchEvent batchEvent : batchEventList) {
            Batch batch = batchEvent.getBatch();
            BatchDTO batchDTO = convertToBatchDTO(batch);
            batchDTOList.add(batchDTO);
        }

        return batchDTOList;
    }

    // get all events assigned to a batch
    public List<BatchEvent> getBatchEvents(String id) {
        return BatchEventRepository.findEventByBatchId(Integer.parseInt(id));
    }

    // remove a batch from an event
    @Transactional
    public void removeBatch(String eventId, String batchId) {
        BatchEventRepository.deleteByEventIdAndBatchId(Integer.parseInt(eventId), Integer.parseInt(batchId));
    }


    // convert Batch to BatchDTO
    private BatchDTO convertToBatchDTO(Batch batch) {
        BatchDTO dto = new BatchDTO();
        dto.setId(batch.getId());
        dto.setName(batch.getName());
        dto.setConsultantId(batch.getConsultant() != null ? batch.getConsultant().getId() : null);
        dto.setConsultantName(batch.getConsultant() != null ? batch.getConsultant().getName() : null);
        dto.setStartDate(batch.getStartDate());
        dto.setCreatedAt(batch.getCreatedAt());
        dto.setUpdatedAt(batch.getUpdatedAt());
        dto.setStatus(batch.getStatus());
        return dto;
    }


}
