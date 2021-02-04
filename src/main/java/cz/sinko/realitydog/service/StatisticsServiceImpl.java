package cz.sinko.realitydog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.sinko.realitydog.entity.Location;
import cz.sinko.realitydog.entity.Property;
import cz.sinko.realitydog.entity.PropertyType;
import cz.sinko.realitydog.repository.PropertyRepository;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private PropertyRepository propertyRepository;

	@Override
	public Double calculateAverageByPropertyType(PropertyType propertyType) {
		List<Property> properties = propertyRepository.findAllByPropertyTypeAndBlacklistedIsFalse(propertyType);
		if (!properties.isEmpty()) {
			return properties.stream().mapToDouble(Property::getPriceForMeter).average().getAsDouble();
		}
		return Double.valueOf(0);
	}

	@Override
	public Double calculateAverageByLocation(Location location) {
		List<Property> properties = propertyRepository.findAllByLocationAndBlacklistedIsFalse(location);
		if (!properties.isEmpty()) {
			return properties.stream().mapToDouble(Property::getPriceForMeter).average().getAsDouble();
		}
		return Double.valueOf(0);
	}

	@Override
	public Double calculateAverageByLocationAndPropertyType(Location location, PropertyType propertyType) {
		List<Property> properties = propertyRepository.findAllByLocationAndPropertyTypeAndBlacklistedIsFalse(location, propertyType);
		if (!properties.isEmpty()) {
			return properties.stream().mapToDouble(Property::getPriceForMeter).average().getAsDouble();
		}
		return Double.valueOf(0);
	}

}
