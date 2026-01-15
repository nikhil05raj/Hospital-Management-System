package com.example.HMS.Service;

import com.example.HMS.models.Bill;
import com.example.HMS.repository.BillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    @Autowired
    private BillRepository billRepository;

    public Page<Bill> GetAllBill(int page, int size){
        try {
            System.out.println("Service layer : Created Bill");
            Pageable pageable = PageRequest.of(page, size);
            return billRepository.findAll(pageable);
        }
        catch (Exception e) {
            logger.error("Error occurred while fetching all the Bills : {}",e.getMessage());
            return null;
        }
    }

    public Bill createBill(Bill bill){
        try {
            System.out.println("Service layer : Created Bill");
            return bill;
        }
        catch (Exception e) {
            logger.error("Error occurred while creating the bill : {}",e.getMessage());
            return null;
        }
    }

    public Bill GetBillById(Long id){
        try {
            System.out.println("Service layer : Created Bill");
            return null;
        }
        catch (Exception e) {
            logger.error("Error occurred while fetch the Bill by id {}: {}",id,e.getMessage());
            return null;
        }
    }

    public void updateBillById(Long id){
        try {
            System.out.println("Service layer : Updated the Bill by id");
        }
        catch (Exception e) {
            logger.error("Error occurred while updating the bill by id {} : {}",id,e.getMessage());
        }
    }

    public void deleteBillById(Long id){
        try {
            System.out.println("Service layer : Deleting Bill");
        }
        catch (Exception e) {
            logger.error("Error occurred while deleting the bill by id {} : {}",id,e.getMessage());
        }
    }
}
