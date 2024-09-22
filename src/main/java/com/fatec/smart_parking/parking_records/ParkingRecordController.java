package com.fatec.smart_parking.parking_records;

import com.fatec.smart_parking.core.authentication.LoginDTO;
import com.fatec.smart_parking.core.authentication.LoginResponseDTO;
import com.fatec.smart_parking.vehicle.VehicleDTO;
import lombok.Getter;
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
    public ResponseEntity<ParkingRecordDTO> create(@RequestBody VehicleDTO vehicleDTO) {
        this.parkingRecordService.create(vehicleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping
    public ResponseEntity<List<ParkingRecordDTO>> findAllCurrentRecords() {
       return ResponseEntity.ok(this.parkingRecordService.findByCurrentRecords());
    }

    @PostMapping("/history")
    public ResponseEntity<List<ParkingHistoryDTO>> parkingHistory() {
        return ResponseEntity.ok(this.parkingRecordService.parkingHistory());
    }


}
