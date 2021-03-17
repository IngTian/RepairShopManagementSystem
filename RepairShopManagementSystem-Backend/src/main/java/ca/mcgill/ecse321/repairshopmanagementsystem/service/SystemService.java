package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import java.util.*;

@Service
public class SystemService {

	@Autowired
	private RepairShopManagementSystemRepository systemRepository;

	/**
	 * create a system
	 * 
	 * @param businessName
	 * @param phoneNo
	 * @param address
	 * @return
	 */
	@Transactional
	public RepairShopManagementSystem createSystem(String businessName, String phoneNo, String address) {
		RepairShopManagementSystem system = new RepairShopManagementSystem();

		String error = "";

		if (businessName == null || businessName.trim().length() == 0) {
			error = error + "Business name cannot be empty! ";
		} else if (!Util.isBusinessNameCorrect(businessName)) {
			error = error + "Business name illegal! ";
		}

		if (phoneNo == null || phoneNo.trim().length() == 0) {
			error = error + "Business phone number cannot be empty! ";
		} else if (!Util.isPhoneNoCorrect(phoneNo)) {
			error = error + "Business phone number illegal! ";
		}

		if (address == null || address.trim().length() == 0) {
			error = error + "Business address cannot be empty! ";
		} else if (!Util.isAddressCorrect(address)) {
			error = error + "Business address illegal! ";
		}

		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		system.setBusinessName(businessName);
		system.setBusinessPhoneNumber(phoneNo);
		system.setBusinessAddress(address);
		system.setSpace(new HashSet<>());
		system.setSchedule(new HashSet<>());
		system.setUser(new HashSet<>());
		systemRepository.save(system);
		return system;
	}

	@Transactional
	public RepairShopManagementSystem getMostRecentSystem() {
		return systemRepository.findFirstByOrderByIdDesc();
	}

	@Transactional
	public List<RepairShopManagementSystem> getAllSystems() {
		return toList(systemRepository.findAll());
	}

	@Transactional
	public RepairShopManagementSystem getSystem(String businessName, String phoneNo, String address) {
		return systemRepository.findRepairShopManagementSystemByBusinessNameAndBusinessAddressAndBusinessPhoneNumber(
				businessName, address, phoneNo);
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
