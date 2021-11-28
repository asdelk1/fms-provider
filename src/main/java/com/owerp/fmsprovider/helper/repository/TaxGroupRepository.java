package com.owerp.fmsprovider.helper.repository;

import com.owerp.fmsprovider.helper.model.data.TaxGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxGroupRepository extends JpaRepository<TaxGroup, Long> {

    List<TaxGroup> findAllByStatusIsTrue();
}
