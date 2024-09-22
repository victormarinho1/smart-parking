package com.fatec.smart_parking.vehicle;

import com.fatec.smart_parking.core.exception.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleDTO findByPlate(String plate) {
        Optional<Vehicle>  optVehicle = this.vehicleRepository.findByPlate(plate);
        if(optVehicle.isPresent()){
            VehicleDTO vehicleDTO = convertToDTO(optVehicle.get());
            return vehicleDTO;
        }
        throw new VehicleNotFoundException();
    }


    public VehicleDTO convertToDTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getClient().getName(),vehicle.getModel());
    }



}
