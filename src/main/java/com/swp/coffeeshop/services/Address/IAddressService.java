package com.swp.coffeeshop.services.Address;

import com.swp.coffeeshop.models.UserAddress;

import java.util.List;

public interface IAddressService {
    public UserAddress getAddressById(Integer id);

    public List<UserAddress> getAllAddressByUserId(Integer userId);

    public List<UserAddress> getAllAddressByTrackingId(String trackingId);

    public void removeAddress(Integer id);

}
