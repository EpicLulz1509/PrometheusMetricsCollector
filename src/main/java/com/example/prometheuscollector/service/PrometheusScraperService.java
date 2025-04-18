package com.example.prometheuscollector.service;

import com.example.prometheuscollector.model.Metric;
import com.example.prometheuscollector.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class PrometheusScraperService {

    @Value("${prometheus.url}")
    private String prometheusUrl;

    private final MetricRepository metricRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PrometheusScraperService(MetricRepository metricRepository, RestTemplate restTemplate) {
        this.metricRepository = metricRepository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 60000)
    public void collectMetrics() {
        try {
            String query = "up";
            String url = prometheusUrl + "/api/v1/query?query=" + query;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            Metric metric = new Metric();
            metric.setMetricName("up");
            metric.setInstance("localhost:9090");
            metric.setJob("prometheus");
            metric.setMetricValue(1.0);
            metric.setTimestamp(LocalDateTime.now());

            metricRepository.save(metric);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

