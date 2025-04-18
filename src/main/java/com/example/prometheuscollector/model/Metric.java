package com.example.prometheuscollector.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metricName;
    private String instance;
    private String job;

    @Column(name = "metric_value") // renamed to avoid SQL keyword issue
    private Double metricValue;

    private LocalDateTime timestamp;

    public Metric() {}

    // Getters and setters
    public Long getId() { return id; }

    public String getMetricName() { return metricName; }
    public void setMetricName(String metricName) { this.metricName = metricName; }

    public String getInstance() { return instance; }
    public void setInstance(String instance) { this.instance = instance; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public Double getMetricValue() { return metricValue; }
    public void setMetricValue(Double metricValue) { this.metricValue = metricValue; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
