package com.fatec.smart_parking.parking_records;

import com.fatec.smart_parking.core.authentication.AuthenticationService;


import com.fatec.smart_parking.parking.Parking;
import com.fatec.smart_parking.parking.ParkingService;

import com.fatec.smart_parking.payment.Payment;
import com.fatec.smart_parking.payment.PaymentService;
import com.fatec.smart_parking.user.User;

import com.fatec.smart_parking.vehicle.Vehicle;
import com.fatec.smart_parking.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingRecordService {
    @Autowired
    private ParkingRecordRepository parkingRecordRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private PaymentService paymentService;
    public ParkingRecordDTO create(PlateDTO plateDTO){
        Vehicle vehicle = this.vehicleService.findByPlate(plateDTO.plate());
        Parking parking = this.parkingService.findFirstParking();
        ParkingRecord parkingRecord = new ParkingRecord(null,vehicle,parking,LocalDateTime.now(),null);
        ParkingRecordDTO parkingRecordDTO = new ParkingRecordDTO(
                vehicle.getClient().getName(), vehicle.getModel(),
                vehicle.getColor().getName(),vehicle.getPlate(),
                parking.getName(), LocalDateTime.now(),null);
        this.parkingRecordRepository.save(parkingRecord);
        return parkingRecordDTO;
    }

    public List<ParkingRecordDTO> findByCurrentRecords(Long id){
        List<ParkingRecord> parkingRecordList = this.parkingRecordRepository.findByUserId(id);
        return  parkingRecordList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ParkingRecord findCurrentByPlate(String plate){
        Vehicle vehicle = this.vehicleService.findByPlate(plate);
        List<ParkingRecord> currentRecords = this.parkingRecordRepository.findByUserId(vehicle.getClient().getId());
        return currentRecords.stream()
                .filter(record -> record.getVehicle().getPlate().equals(plate))
                .findFirst()
                .orElse(null);

    }


    public List<ParkingHistoryDTO> parkingHistory(){
        User user = this.authenticationService.getCurrentUser();
        List<Payment> paymentList = this.paymentService.findAllByUser(user.getId());


        return  paymentList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public ParkingRecordDTO convertToDTO(ParkingRecord parkingRecord) {
        return new ParkingRecordDTO(
                parkingRecord.getVehicle().getClient().getName(),
                parkingRecord.getVehicle().getModel(),
                parkingRecord.getVehicle().getColor().getName(),
                parkingRecord.getVehicle().getPlate(),
                parkingRecord.getParking().getName(),
                parkingRecord.getEntryTime(),
                parkingRecord.getExitTime());
    }

    public ParkingHistoryDTO convertToDTO(Payment payment){
        return new ParkingHistoryDTO(
            "Num Tem :(",
            payment.getParkingRecord().getParking().getName(),
            "Num Tem :(",
            payment.getParkingRecord().getVehicle().getModel());
    }



}
