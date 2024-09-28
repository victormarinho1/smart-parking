package com.fatec.smart_parking.color;

import com.fatec.smart_parking.core.exception.ColorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    public Color findByName(String name) {
        Color color = this.colorRepository.findByName(name)
                .orElseThrow(ColorNotFoundException::new);
        return color;

    }
}
