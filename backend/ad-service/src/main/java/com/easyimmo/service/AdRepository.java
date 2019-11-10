package com.easyimmo.service;

import com.easyimmo.domain.Ad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends CrudRepository<Ad, Integer> {

   List<Ad> findByAuthorId(Integer id);

}
