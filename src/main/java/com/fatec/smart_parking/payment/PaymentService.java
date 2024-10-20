package com.fatec.smart_parking.payment;

import com.fatec.smart_parking.parking.ParkingRepository;
import com.fatec.smart_parking.parking_price.ParkingPriceService;
import com.fatec.smart_parking.parking_records.ParkingRecord;
import com.fatec.smart_parking.parking_records.PlateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ParkingPriceService parkingPriceService;

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<Payment> findAllByUser(Long userId) {
       return this.paymentRepository.findPaymentsByUserId(userId);

    }

    public QrCodeResponseDTO gerarQrCode(String valor, Long id) {
        String url = "http://localhost:5000/gerar_qrcode";
        QrCodeDTO qrCodeDTO = new QrCodeDTO(valor,id.toString());
        QrCodeResponseDTO  qrCodeResponseDTO = restTemplate.postForObject(url, qrCodeDTO, QrCodeResponseDTO.class);
        return qrCodeResponseDTO;
    }

    public Payment create(ParkingRecord parkingRecord) {
        QrCodeResponseDTO qrCodeResponseDTO = gerarQrCode("0.01", 1L);
        Payment payment = new Payment(null, parkingRecord,this.parkingPriceService.findCurrent(), LocalDateTime.now(), qrCodeResponseDTO.payload(), qrCodeResponseDTO.url_qrcode());
        return this.paymentRepository.save(payment);
    }
}
