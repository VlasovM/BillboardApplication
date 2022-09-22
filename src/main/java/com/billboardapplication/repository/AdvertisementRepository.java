package com.billboardapplication.repository;

import com.billboardapplication.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

    List<Advertisement> findAllByOrderByNameAsc();

    List<Advertisement> findAllByOrderByIdAsc();

    List<Advertisement> findAllByOrderByContactsAsc();

    Advertisement findById(int id);

}
