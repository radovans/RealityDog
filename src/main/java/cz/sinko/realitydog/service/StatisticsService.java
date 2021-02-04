package cz.sinko.realitydog.service;

import cz.sinko.realitydog.entity.Location;
import cz.sinko.realitydog.entity.PropertyType;

public interface StatisticsService {

	Double calculateAverageByPropertyType(PropertyType propertyType);

	Double calculateAverageByLocation(Location location);

	Double calculateAverageByLocationAndPropertyType(Location location, PropertyType propertyType);

}
