package org.main.recommendation.service;

import org.main.recommendation.entity.Embedding;
import org.main.recommendation.repository.EmbeddingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmbeddingService {
    @Autowired
    private EmbeddingRepository embeddingRepository;

    public List<Embedding> getAllEmbeddings() {
        return embeddingRepository.findAll();
    }

    public Embedding saveEmbedding(Embedding embedding) {
        return embeddingRepository.save(embedding);
    }

    public List<Object[]> findMostSimilarEmbeddings(float[] embedding, int limit) {
        return embeddingRepository.findMostSimilarEmbeddings(convertToPostgresVector(embedding), limit);
    }

    public static String convertToPostgresVector(float[] embedding) {
        return IntStream.range(0, embedding.length)
                .mapToObj(i -> Float.toString(embedding[i]))
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
