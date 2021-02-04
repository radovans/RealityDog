package cz.sinko.realitydog;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.sinko.realitydog.entity.Location;
import cz.sinko.realitydog.entity.Property;
import cz.sinko.realitydog.entity.Search;
import cz.sinko.realitydog.entity.PropertyType;
import cz.sinko.realitydog.repository.PropertyRepository;
import cz.sinko.realitydog.service.ChromeService;
import cz.sinko.realitydog.service.ScrapingService;
import cz.sinko.realitydog.service.StatisticsService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class RealitydogApplication {

	@Autowired
	private ScrapingService scrapingService;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private StatisticsService statisticsService;

	public static void main(String[] args) {
		SpringApplication.run(RealitydogApplication.class, args);
	}

	@PostConstruct
	void afterStart() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@PostConstruct
	private void postConstruct() throws InterruptedException {
		WebDriver webDriver = ChromeService.openChrome();
		Search search = new Search();
		List<Location> locations = new ArrayList<>();
		locations.add(Location.BRATISLAVA_OKRES);
		List<PropertyType> propertyTypes = new ArrayList<>();
		propertyTypes.add(PropertyType.JEDNA_IZBOVY);
		propertyTypes.add(PropertyType.DVA_IZBOVY);
		propertyTypes.add(PropertyType.TRI_IZBOVY);
		search.setLocations(locations);
		search.setPropertyTypes(propertyTypes);
//		search.setPriceTo(80000);
		List<String> properties = scrapingService.getListOfPropertiesFromSearchSite(webDriver, search);
		for (String property : properties){
			Property property1 = scrapingService.scrapeProperty(webDriver, property);
			if (propertyRepository.findByAddressAndLocationAndPriceAndPriceForMeterAndSizeAndPropertyTypeAndState(property1.getAddress(), property1.getLocation(), property1.getPrice(), property1.getPriceForMeter(), property1.getSize(), property1.getPropertyType(), property1.getState()).isEmpty() && property1.getPrice() != null) {
				if (property1.getPriceForMeter() != null && property1.getPrice() > 1000 && property1.getPrice() < 999999) {
					propertyRepository.save(property1);
				}
			}
		}
		webDriver.quit();
	}

	@PostConstruct
	private void postCounstruct() {
		log.info("1 izbovy priemer: " + statisticsService.calculateAverageByPropertyType(PropertyType.JEDNA_IZBOVY));
		log.info("2 izbovy priemer: " + statisticsService.calculateAverageByPropertyType(PropertyType.DVA_IZBOVY));
		log.info("3 izbovy priemer: " + statisticsService.calculateAverageByPropertyType(PropertyType.TRI_IZBOVY));
		for (Location location : Location.values()) {
			log.info(location + ": " + statisticsService.calculateAverageByLocation(location));
		}
		for (Location location : Location.values()) {
			for (PropertyType propertyType : PropertyType.values()) {
				log.info(location + " " + propertyType + " "+  ": " + statisticsService.calculateAverageByLocationAndPropertyType(location, propertyType));
			}
		}
	}

}
