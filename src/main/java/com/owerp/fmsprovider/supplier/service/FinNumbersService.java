package com.owerp.fmsprovider.supplier.service;

import com.owerp.fmsprovider.supplier.model.data.FinNumbers;
import com.owerp.fmsprovider.supplier.model.enums.NumTypes;
import com.owerp.fmsprovider.supplier.repository.FinNumbersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FinNumbersService {

    private static final Logger LOG = LoggerFactory.getLogger(FinNumbersService.class);
    FinNumbersRepository finNumbersRepository;
    public Long getNextNumber(NumTypes numTypes, Boolean isDisplayOnly){
        long nextNumber = 0L;
        try{
            FinNumbers finNumbers = finNumbersRepository.getFinNumbersByNumType(numTypes);
            if(finNumbers == null){//First time
                nextNumber = 1L;

                finNumbers = new FinNumbers();
                finNumbers.setNumType(numTypes);

            }else{
                nextNumber = finNumbers.getNumber() + 1;
            }

            // If object is saving Next number is saved to DB
            if(!isDisplayOnly){
                finNumbers.setNumber(nextNumber);
                finNumbersRepository.save(finNumbers);
            }
        }catch(Exception e){
            LOG.error("[saveCustomer] " + e);
        }
        return nextNumber;
    }
}
