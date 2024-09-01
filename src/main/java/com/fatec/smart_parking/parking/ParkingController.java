package com.fatec.smart_parking.parking;

import com.fatec.smart_parking.client.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/parkings")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping
    public ResponseEntity<List<ParkingDTO>> findAll(){
        List<ParkingDTO> list = parkingService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable Long id){
        ParkingDTO parking = parkingService.findById(id);
        return ResponseEntity.ok().body(parking);
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody Parking parking){
        ParkingDTO parkingDTO = parkingService.create(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingDTO);
    }
}
