package com.fatec.smart_parking.make_car;

import com.fatec.smart_parking.core.exception.MakeCarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MakeCarService {

    @Autowired
    private MakeCarRepository makeCarRepository;

    public MakeCar findByName(String name){
        MakeCar makeCar = this.makeCarRepository.findByName(name)
                .orElseThrow(MakeCarNotFoundException::new);
        return makeCar;
    }
}
