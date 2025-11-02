package com.foodwaste.foodwaste.service;

import com.foodwaste.foodwaste.model.Processor;
import com.foodwaste.foodwaste.repository.ProcessorRepository;
import com.foodwaste.foodwaste.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProcessorService {
    
    @Autowired
    private ProcessorRepository processorRepository;
    
    public List<Processor> getAllProcessors() {
        return processorRepository.findAll();
    }
    
    public Processor getProcessorById(Long id) {
        return processorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processor not found with id: " + id));
    }
    
    public Processor createProcessor(Processor processor) {
        return processorRepository.save(processor);
    }
    
    public Processor updateProcessor(Long id, Processor processorDetails) {
        Processor processor = getProcessorById(id);
        processor.setName(processorDetails.getName());
        processor.setLocation(processorDetails.getLocation());
        processor.setMaxProcessingCapacity(processorDetails.getMaxProcessingCapacity());
        return processorRepository.save(processor);
    }
    
    public void deleteProcessor(Long id) {
        Processor processor = getProcessorById(id);
        processorRepository.delete(processor);
    }
}