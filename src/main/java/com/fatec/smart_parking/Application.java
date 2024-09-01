package com.fatec.smart_parking;

import com.fatec.smart_parking.client.Client;
import com.fatec.smart_parking.client.ClientRepository;
import com.fatec.smart_parking.core.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	@Autowired
	ClientRepository clientRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Client client = Client.builder()
//				.name("João")
//				.email("joa@gmail.com")
//				.role(Role.CLIENT)
//				.password("4984894dsafda")
//				.document("49854964")
//				.build();
//
//		clientRepository.save(client);
//
//	}

}
