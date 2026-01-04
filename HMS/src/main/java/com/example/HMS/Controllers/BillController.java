package com.example.HMS.Controllers;

import com.example.HMS.Service.BillService;
import com.example.HMS.models.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public void createBill(@RequestBody Bill bill){
        System.out.println("Create bill");
        billService.createBill(bill);
    }

    @GetMapping
    public List<Bill> getAllBill(){
        System.out.println("get All bill");
        return billService.GetAllBill();
    }

    @GetMapping("/id")
    public Bill getBillById(@PathVariable Long id){
        System.out.println("get bill by id");
        return billService.GetBillById(id);
    }

    @PutMapping("/id")
    public void updateBillById(@PathVariable Long id){
        System.out.println("update bill by id");
        billService.updateBillById(id);
    }

    @DeleteMapping("/id")
    public void deleteBillById(@PathVariable Long id){
        System.out.println("delete bill by id");
        billService.deleteBillById(id);
    }

}













