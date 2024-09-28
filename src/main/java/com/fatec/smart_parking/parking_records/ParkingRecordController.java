package com.fatec.smart_parking.parking_records;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/parking-records")
public class ParkingRecordController {
    @Autowired
    private ParkingRecordService parkingRecordService;

    @PostMapping("/entry")
    public ResponseEntity<ParkingRecordDTO> create(@RequestBody PlateDTO plateDTO) {
        this.parkingRecordService.create(plateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping
    public ResponseEntity<List<ParkingRecordDTO>> findAllCurrentRecords() {
       return ResponseEntity.ok(this.parkingRecordService.findByCurrentRecords());
    }

    @GetMapping("/history")
    public ResponseEntity<List<ParkingHistoryDTO>> parkingHistory() {
        return ResponseEntity.ok(this.parkingRecordService.parkingHistory());
    }


}
