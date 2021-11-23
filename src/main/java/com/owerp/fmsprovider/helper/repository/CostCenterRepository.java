package com.owerp.fmsprovider.helper.repository;

import com.owerp.fmsprovider.helper.model.data.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {

    List<CostCenter> findAllByStatusIsTrue();
}
