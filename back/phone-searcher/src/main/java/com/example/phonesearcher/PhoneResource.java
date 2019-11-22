package com.example.phonesearcher;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/phones")
public class PhoneResource {
	
	PhoneService phones;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping()
	public List<PhoneDTO> findPhones(@RequestBody PhoneDTO phone ) {
		
		phones = new PhoneService();
		
		List<PhoneDTO> phonesList = phones.getData(phone);
		
		return phonesList;
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/more")
	public List<PhoneDTO> findMorePhones(@RequestBody PhoneDTO phone ) {
		
		phones = new PhoneService();
		
		List<PhoneDTO> phonesList = phones.getData(phone);
		
		return phonesList;
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/brands")
	public List<String> findBrands() {
		phones = new PhoneService();
		
		return phones.findField("brand");
	}
	
	@CrossOrigin(origins = "http://localhost:8080")	
	@GetMapping("/colors")
	public List<String> findColors() {
		phones = new PhoneService();
		
		return phones.findField("color");
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/intMemories")
	public List<String> findIntMemories() {
		phones = new PhoneService();
		
		return phones.findField("intMemory");
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/RAMs")
	public List<String> findRAMs() {
		phones = new PhoneService();
		
		return phones.findField("ram");
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/frontCamNums")
	public List<String> findFrontCamNums() {
		phones = new PhoneService();
		
		return phones.findField("frontCamNum");
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/backCamNums")
	public List<String> findBackCamNums() {
		phones = new PhoneService();
		
		return phones.findField("backCamNum");
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/frontCamRes")
	public List<String> findFrontCamRes() {
		phones = new PhoneService();
		
		return phones.findField("frontCamRes");
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/backCamRes")
	public List<String> findBackCamRes() {
		phones = new PhoneService();
		
		return phones.findField("backCamRes");
	}
	
	
}
