package com.proj.Matjalal.domain.subway.repository;

import com.proj.Matjalal.domain.subway.entitiy.SubwayArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayRepository extends JpaRepository<SubwayArticle, Long> {
}
