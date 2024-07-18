package bloomfilter.controller;

import bloomfilter.BloomFilter;
import bloomfilter.redis.RedisBloomFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bloomfilter")
public class BloomFilterController {
    private final BloomFilter bloomFilter;
    private String bloomFilterName = "bloom-filter";
    private int DEFAULT_EXPECTED_ELEMENTS = 10_000;
    private float DEFAULT_FP_PROBABILITY = 0.01f;

    public BloomFilterController() {
        // Initialize Bloom filter with size 1024 and 10 hash functions
        this.bloomFilter = new RedisBloomFilter(bloomFilterName, DEFAULT_EXPECTED_ELEMENTS, DEFAULT_FP_PROBABILITY);
    }

    @PostMapping
    public String initialiseBloomFilter(@RequestParam String name, @RequestParam int expectedElements, @RequestParam float fpProbability) {

        return "Initialised the bloom filter.";
    }

    @PostMapping("/add")
    public String addElement(@RequestParam String element) {
        bloomFilter.add(element);
        return "Element added.";
    }

    @GetMapping("/contains")
    public boolean containsElement(@RequestParam String element) {
        return bloomFilter.contains(element);
    }
}
