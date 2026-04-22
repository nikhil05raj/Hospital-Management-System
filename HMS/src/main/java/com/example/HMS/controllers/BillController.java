package com.example.HMS.controllers;

import com.example.HMS.dto.BillDto;
import com.example.HMS.service.BillService;
import com.example.HMS.service.webhookService;
import com.example.HMS.enums.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/bill")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final webhookService webhookService;

    @PostMapping
    public ResponseEntity<BillDto> createBill(@RequestBody BillDto billRequestDto)
    {
        BillDto billDto = billService.createBill(billRequestDto);

        Map<String,Object> payload = new HashMap<>();
        payload.put("BillId",billDto.getBillId());
        payload.put("patientId",billDto.getPatientId());
        payload.put("Amount",billDto.getAmount());
        payload.put("Status",billDto.getStatus());

        String webhookUrl = "http://localhost:8081/webhook";    // Send the webhook
        webhookService.sendWebhook(webhookUrl, EventType.Bill_Generated, payload);

        return ResponseEntity.ok(billDto);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillDto> getBillById(@PathVariable Long billId)
    {
        BillDto billDto = billService.getBillById(billId);

        return ResponseEntity.ok(billDto);
    }

    @GetMapping
    public ResponseEntity<Page<BillDto>> getAllBill(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "2") int size){

        Page<BillDto> dtos = billService.getAllBill(page,size);

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/update/{billId}")
    public ResponseEntity<BillDto> updateBillById(@PathVariable Long billId,
                                                  @RequestBody BillDto dto)
    {
        BillDto billDto = billService.updateBillById(billId, dto);

        return ResponseEntity.ok(billDto);
    }

    @DeleteMapping("/delete/{billId}")
    public ResponseEntity<String> deleteBillById(@PathVariable Long billId)
    {
        billService.deleteBillById(billId);
        return ResponseEntity.ok(String.format("the bill with id %d has been deleted successfully.",billId));
    }

    // Get all bills for a patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BillDto>> getBillsByPatient(@PathVariable Long patientId)
    {
        List<BillDto> dtos = billService.getBillByPatient(patientId);
        return ResponseEntity.ok(dtos);
    }

    // Get bill for a specific appointment
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<BillDto> getBillByAppointment(@PathVariable Long appointmentId)
    {
        BillDto dto = billService.getBillByAppointment(appointmentId);
        return ResponseEntity.ok(dto);
    }

}













