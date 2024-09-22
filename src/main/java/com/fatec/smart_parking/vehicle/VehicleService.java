package com.fatec.smart_parking.vehicle;

import com.fatec.smart_parking.core.exception.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle findByPlate(String plate) {
        Optional<Vehicle>  optVehicle = this.vehicleRepository.findByPlate(plate);
        if(optVehicle.isPresent()){
            Vehicle vehicle = optVehicle.get();
            return vehicle;
        }
        throw new VehicleNotFoundException();
    }



    public VehicleDTO convertToDTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getPlate());
    }



}
