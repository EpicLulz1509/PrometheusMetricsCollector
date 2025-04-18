package com.example.prometheuscollector.controller;

import com.example.prometheuscollector.model.Metric;
import com.example.prometheuscollector.repository.MetricRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

    private final MetricRepository metricRepository;

    public MetricController(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    // GET all metrics
    @GetMapping
    public List<Metric> getAll() {
        return metricRepository.findAll();
    }

    // GET metrics by name
    @GetMapping("/name/{name}")
    public List<Metric> getByName(@PathVariable String name) {
        return metricRepository.findByMetricName(name);
    }

    // GET metric by ID
    @GetMapping("/{id}")
    public ResponseEntity<Metric> getById(@PathVariable Long id) {
        Optional<Metric> optionalMetric = metricRepository.findById(id);
        return optionalMetric.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE a new metric
    @PostMapping
    public Metric createMetric(@RequestBody Metric metric) {
        metric.setTimestamp(java.time.LocalDateTime.now());
        return metricRepository.save(metric);
    }

    // UPDATE an existing metric
    @PutMapping("/{id}")
    public ResponseEntity<Metric> updateMetric(@PathVariable Long id, @RequestBody Metric updatedMetric) {
        return metricRepository.findById(id).map(metric -> {
            metric.setMetricName(updatedMetric.getMetricName());
            metric.setInstance(updatedMetric.getInstance());
            metric.setJob(updatedMetric.getJob());
            metric.setMetricValue(updatedMetric.getMetricValue());
            metric.setTimestamp(java.time.LocalDateTime.now());
            metricRepository.save(metric);
            return ResponseEntity.ok(metric);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a metric by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable Long id) {
        if (metricRepository.existsById(id)) {
            metricRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

