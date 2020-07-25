package az.maqa.project.service;

import java.util.List;

import az.maqa.project.shared.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String userId);

	AddressDto getAddress(String addressId);
	
	
}
