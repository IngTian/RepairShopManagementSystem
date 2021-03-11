package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;

@Service
public class SystemService {

    @Autowired
    private RepairShopManagementSystemRepository systemRepository;

    @Transactional
    public RepairShopManagementSystem createSystem(String businessName, String phoneNo, String address) {
        RepairShopManagementSystem system = new RepairShopManagementSystem();
        system.setBusinessName(businessName);
        system.setBusinessPhoneNumber(phoneNo);
        system.setBusinessAddress(address);
        systemRepository.save(system);
        return system;
    }

    @Transactional
    public List<RepairShopManagementSystem> getAllSystems() {
        return toList(systemRepository.findAll());
    }

    @Transactional
    public RepairShopManagementSystem getSystem(String businessName, String phoneNo, String address) {
        return systemRepository.findRepairShopManagementSystemByBusinessNameAndBusinessAddressAndBusinessPhoneNumber(businessName, address, phoneNo);
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}
