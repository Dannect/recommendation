package org.main.recommendation.repository;

import org.main.recommendation.entity.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbeddingRepository extends JpaRepository<Embedding, Long> {

    @Query(value = "SELECT id, name, embedding <=> CAST(:embedding AS vector) AS similarity " +
            "FROM embedding " +
            "ORDER BY embedding <=> CAST(:embedding AS vector) " +
            "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostSimilarEmbeddings(@Param("embedding") String embedding, @Param("limit") int limit);
}