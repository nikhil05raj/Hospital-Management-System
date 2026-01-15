package com.example.HMS.Controllers;

import com.example.HMS.Service.BillService;
import com.example.HMS.Service.webhookService;
import com.example.HMS.models.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired   // ← VERY IMPORTANT: Inject the webhookService instance!
    private webhookService webhookService;

    @GetMapping
    public Page<Bill> getAllBill(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "2") int size){
        System.out.println("get All bill");
        return billService.GetAllBill(page,size);
    }

    @PostMapping
    public Bill createBill(@RequestBody Bill billRequest){
        System.out.println("Create bill");
        Bill bill = billService.createBill(billRequest);

        Map<String,Object> payload = new HashMap<>();
        payload.put("BillId",bill.getId());
        payload.put("patientId",bill.getPatientId());
        payload.put("Amount",bill.getAmount());
        payload.put("Status",bill.getStatus());

        // Send the webhook
        String webhookUrl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookUrl, payload);

        return bill;
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













