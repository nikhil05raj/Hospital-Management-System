package com.example.HMS.Service;

import com.example.HMS.models.Bill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    public void createBill(Bill bill){
        try {
            System.out.println("Service layer : Created Bill");
        }
        catch (Exception e) {
            System.out.println("Error message :"+e.getMessage() );
        }
    }

    public List<Bill> GetAllBill(){
        try {
            System.out.println("Service layer : Created Bill");
            return null;
        }
        catch (Exception e) {
            System.out.println("Error message :"+e.getMessage() );
            return null;
        }
    }

    public Bill GetBillById(Long id){
        try {
            System.out.println("Service layer : Created Bill");
            return null;
        }
        catch (Exception e) {
            System.out.println("Error message :"+e.getMessage() );
            return null;
        }
    }

    public void updateBillById(Long id){
        try {
            System.out.println("Service layer : Updated the Bill by id");
        }
        catch (Exception e) {
            System.out.println("Error message :"+e.getMessage() );
        }
    }

    public void deleteBillById(Long id){
        try {
            System.out.println("Service layer : Deleting Bill");
        }
        catch (Exception e) {
            System.out.println("Error message :"+e.getMessage() );
        }
    }




}
