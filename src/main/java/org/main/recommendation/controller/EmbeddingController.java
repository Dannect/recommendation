package org.main.recommendation.controller;

import org.main.recommendation.entity.Embedding;
import org.main.recommendation.service.EmbeddingService;
import org.main.recommendation.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class EmbeddingController {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private GeminiService geminiService;

    @RequestMapping(value = "/similar", method = RequestMethod.GET)
    public List<Object[]> findMostSimilarEmbeddings(@RequestParam("searchValue") String searchValue) {
        return embeddingService.findMostSimilarEmbeddings(geminiService.getEmbeddingForString(searchValue), 10);
    }

    @RequestMapping(value = "/embeddings", method = RequestMethod.GET)
    public List<Embedding> findEmbeddings() {
        return embeddingService.getAllEmbeddings();
    }

    @RequestMapping(value = "/embedding", method = RequestMethod.POST)
    public void addEmbedding(@RequestBody HashMap<String, Object> request) {
        String searchValue = (String) request.get("searchValue");
        Embedding embedding = new Embedding();
        embedding.setName(searchValue);
        embedding.setEmbedding(geminiService.getEmbeddingForString(searchValue));
        embeddingService.saveEmbedding(embedding);
    }
}