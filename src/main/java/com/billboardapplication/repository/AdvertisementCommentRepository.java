package com.billboardapplication.repository;

import com.billboardapplication.model.entity.AdvertisementComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementCommentRepository extends JpaRepository<AdvertisementComment, Integer> {

    AdvertisementComment findTopByOrderById();

}
