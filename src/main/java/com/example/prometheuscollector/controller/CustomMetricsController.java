// CustomMetricsController.java
package com.example.prometheuscollector.controller;

import com.example.prometheuscollector.repository.MetricRepository;
import com.example.prometheuscollector.model.Metric;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@RestController
public class CustomMetricsController {

    private final MetricRepository metricRepository;

    public CustomMetricsController(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    @GetMapping(value = "/metrics/custom", produces = "text/plain")
    public String customMetrics() {
        List<Metric> metrics = metricRepository.findAll();

        long totalCount = metrics.size();
        DoubleSummaryStatistics stats = metrics.stream()
                .mapToDouble(Metric::getMetricValue)
                .summaryStatistics();

        return """
                # HELP app_custom_metric_count Total number of stored metrics
                # TYPE app_custom_metric_count gauge
                app_custom_metric_count %d
                                
                # HELP app_custom_metric_avg Average metric value
                # TYPE app_custom_metric_avg gauge
                app_custom_metric_avg %.2f
                                
                # HELP app_custom_metric_min Minimum metric value
                # TYPE app_custom_metric_min gauge
                app_custom_metric_min %.2f
                                
                # HELP app_custom_metric_max Maximum metric value
                # TYPE app_custom_metric_max gauge
                app_custom_metric_max %.2f
                """.formatted(
                totalCount,
                stats.getAverage(),
                stats.getMin(),
                stats.getMax()
        );
    }
}
