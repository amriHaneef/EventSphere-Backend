package com.example.eventspherebackend.service;

import com.example.eventspherebackend.model.Batch;
import com.example.eventspherebackend.model.BatchEvent;
import com.example.eventspherebackend.repository.BatchEventRepository;
import com.example.eventspherebackend.repository.BatchRepository;
import com.example.eventspherebackend.repository.EventRepository;
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

    public void assignBatch(String eventId, String batchId) {
        BatchEvent batchEvent = new BatchEvent();
        batchEvent.setEvent(EventRepository.findById(eventId).orElse(null));
        batchEvent.setBatch(BatchRepository.findById(batchId).orElse(null));
        BatchEventRepository.save(batchEvent);
    }

    public List<Batch> getAsignedBatches(String id) {
        List<BatchEvent> batchEventList = BatchEventRepository.findBatchByEventId(id);
        List<Batch> batchList = new ArrayList<>();
        for (BatchEvent batchEvent : batchEventList) {
            batchList.add(batchEvent.getBatch());
        }
        return batchList;
    }


}
