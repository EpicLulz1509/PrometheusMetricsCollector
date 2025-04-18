package com.example.prometheuscollector.controller;

import com.example.prometheuscollector.model.Metric;
import com.example.prometheuscollector.repository.MetricRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class HomeController {

    private final MetricRepository metricRepository;

    public HomeController(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("metrics", metricRepository.findAll());
        model.addAttribute("metric", new Metric());
        return "index";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Metric metric) {
        metric.setTimestamp(java.time.LocalDateTime.now());
        metricRepository.save(metric);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteMetric(@PathVariable Long id) {
        metricRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Metric> metricOpt = metricRepository.findById(id);
        if (metricOpt.isPresent()) {
            model.addAttribute("metric", metricOpt.get());
            model.addAttribute("metrics", metricRepository.findAll());
            return "index";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateMetric(@ModelAttribute Metric metric) {
        metric.setTimestamp(java.time.LocalDateTime.now());
        metricRepository.save(metric);
        return "redirect:/";
    }
}
