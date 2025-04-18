package com.example.prometheuscollector.repository;

import com.example.prometheuscollector.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    List<Metric> findByMetricName(String metricName);
}
