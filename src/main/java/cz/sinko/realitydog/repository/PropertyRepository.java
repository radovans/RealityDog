package cz.sinko.realitydog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.realitydog.entity.Location;
import cz.sinko.realitydog.entity.Property;
import cz.sinko.realitydog.entity.PropertyType;
import cz.sinko.realitydog.entity.State;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

	Optional<Property> findByAddressAndLocationAndPriceAndPriceForMeterAndSizeAndPropertyTypeAndState(String address, Location location, Double price, Double priceForMeter, Integer Size, PropertyType propertyType, State state);

	List<Property> findAllByPropertyTypeAndBlacklistedIsFalse(PropertyType propertyType);

	List<Property> findAllByLocationAndBlacklistedIsFalse(Location location);

	List<Property> findAllByLocationAndPropertyTypeAndBlacklistedIsFalse(Location location, PropertyType propertyType);

}
