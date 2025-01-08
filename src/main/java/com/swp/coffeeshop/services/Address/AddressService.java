package com.swp.coffeeshop.services.Address;

import com.swp.coffeeshop.models.UserAddress;
import com.swp.coffeeshop.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {

    AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public UserAddress getAddressById(Integer id) {
        return addressRepository.findById(id).get();
    }

    @Override
    public List<UserAddress> getAllAddressByUserId(Integer userId) {
        return addressRepository.findAll().stream()
                .filter(a -> a.getUser() != null && a.getUser().getId().equals(userId)).toList();
    }

    @Override
    public List<UserAddress> getAllAddressByTrackingId(String trackingId) {
        return addressRepository.findAll().stream()
                .filter(a -> a.getGuest() != null && a.getGuest().getTrackingId().equals(trackingId)).toList();
    }

    @Override
    public void removeAddress(Integer id) {
        addressRepository.deleteById(id);
    }

}
