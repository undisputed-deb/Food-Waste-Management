package com.foodwaste.foodwaste.controller;

import com.foodwaste.foodwaste.model.Processor;
import com.foodwaste.foodwaste.service.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processors")
@CrossOrigin(origins = "*")
public class ProcessorController {
    
    @Autowired
    private ProcessorService processorService;
    
    @GetMapping
    public List<Processor> getAllProcessors() {
        return processorService.getAllProcessors();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Processor> getProcessorById(@PathVariable Long id) {
        return ResponseEntity.ok(processorService.getProcessorById(id));
    }
    
    @PostMapping
    public Processor createProcessor(@RequestBody Processor processor) {
        return processorService.createProcessor(processor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Processor> updateProcessor(@PathVariable Long id, @RequestBody Processor processorDetails) {
        return ResponseEntity.ok(processorService.updateProcessor(id, processorDetails));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessor(@PathVariable Long id) {
        processorService.deleteProcessor(id);
        return ResponseEntity.noContent().build();
    }
}