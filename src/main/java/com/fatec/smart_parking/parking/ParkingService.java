package com.fatec.smart_parking.parking;


import com.fatec.smart_parking.client.Client;
import com.fatec.smart_parking.client.ClientDTO;
import com.fatec.smart_parking.core.ApplicationUserService;
import com.fatec.smart_parking.core.Role;
import com.fatec.smart_parking.core.exception.ClientNotFoundException;
import com.fatec.smart_parking.core.exception.ParkingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingService extends ApplicationUserService {
    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingValidator parkingValidator;

    public List<ParkingDTO> findAll(){
        List<Parking> parkingList = parkingRepository.findAll();
        List<ParkingDTO> parkingDTOList = new ArrayList();
        for (Parking parking : parkingList) {
            parkingDTOList.add(convertToDTO(parking));
        }
        return parkingDTOList;
    }

    public ParkingDTO findById(Long id){
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(ParkingNotFoundException::new);
        return convertToDTO(parking);
    }

    public ParkingDTO create(Parking parking) {
        parkingValidator.checkEmailExists(parking.getEmail());
        parkingValidator.checkEmailValidity(parking.getEmail());
        parking.setRole(Role.PARKING);
        parking.setPassword(new BCryptPasswordEncoder().encode(parking.getPassword()));
        return convertToDTO(parkingRepository.save(parking));
    }

    public ParkingDTO convertToDTO(Parking parking) {
        return new ParkingDTO(parking.getId(), parking.getName(),parking.getEmail(),parking.getRole(),parking.getEnabled());
    }
}
