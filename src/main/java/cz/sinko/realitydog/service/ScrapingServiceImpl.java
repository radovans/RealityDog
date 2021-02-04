package cz.sinko.realitydog.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import cz.sinko.realitydog.entity.Location;
import cz.sinko.realitydog.entity.Property;
import cz.sinko.realitydog.entity.PropertyType;
import cz.sinko.realitydog.entity.Search;
import cz.sinko.realitydog.entity.State;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ScrapingServiceImpl implements ScrapingService {

	@Override
	public void search(WebDriver webDriver, Search search) throws InterruptedException {
		StringBuilder stringBuilder = new StringBuilder().append("https://www.nehnutelnosti.sk/predaj/?");
		if (search.getPriceFrom() != 0) {
			stringBuilder.append("&p[param1][from]=" + search.getPriceFrom());
		}
		if (search.getPriceTo() != 0) {
			stringBuilder.append("&p[param1][to]=" + search.getPriceTo());
		}
		if (search.getSizeFrom() != 0) {
			stringBuilder.append("&p[param11][from]=" + search.getSizeFrom());
		}
		if (search.getSizeTo() != 0) {
			stringBuilder.append("&p[param11][to]=" + search.getSizeTo());
		}
		if (search.getLocations() != null) {
			stringBuilder.append("&p[location]=");
			for (Location location : search.getLocations()) {
				stringBuilder.append(location.id + ".");
			}
		}
		if (search.getPropertyTypes() != null) {
			stringBuilder.append("&p[categories][ids]=");
			for (PropertyType propertyType : search.getPropertyTypes()) {
				stringBuilder.append(propertyType.id + ".");
			}
		}
		if (search.getPage() == 0) {
			stringBuilder.append("&p[page]=1"); // first page
		} else {
			stringBuilder.append("&p[page]=" + search.getPage());
		}

		stringBuilder.append("&p[order]=1"); // order from newest
		WebDriverUtils.openWebsite(webDriver, stringBuilder.toString(), 10, 1);
	}

	@Override
	public List<String> getListOfPropertiesFromSearchSite(WebDriver webDriver, Search search)
			throws InterruptedException {
		search(webDriver, search);
		List<String> properties = new ArrayList<>();
		int numberOfProperties = extractIntegerFromText(webDriver, "/html/body/div[2]/div[8]/div/div/div[1]/div[11]/div[2]/div[1]");
		int numberOfPages = (numberOfProperties / 30) + 1;
		for (int i = 1; i <= numberOfPages; i++) {
			search.setPage(i);
			search(webDriver, search);
			for (int j = 1; j < 36; j++) {
				try {
					webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
					String href = extractAttribute(webDriver, "/html/body/div[1]/div[8]/div/div/div[1]/div[13]/div[" + j
							+ "]/div/div[2]/div/div[1]/h2/a", "href");
					if (href != null) {
						properties.add(href);
					}
					log.info("Added property: " + href);
				} catch (NoSuchElementException exception) {
					// ad, or no more properties
				}
			}
		}
		log.info("Ready for scraping '" + properties.size() + "' properties");
		return properties;
	}

	@Override
	public Property scrapeProperty(WebDriver webDriver, String url) throws InterruptedException {
		WebDriverUtils.openWebsite(webDriver, url, 10, 1);
		Property property = new Property();
		property.setName(extractText(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[1]/h1"));
		property.setAddress(extractText(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[2]/span"));
		property.setLocation(Location.valueOfLabel(extractText(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[2]/span/a")));
		property.setLatitude(extractGps(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[1]/div[1]", "data-map-lat"));
		property.setLongitude(extractGps(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[1]/div[1]", "data-map-long"));
		property.setPrice(extractPrice(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[3]/div/div/div/div/div/span"));
		property.setPropertyType(PropertyType.valueOfLabel(extractText(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[5]/ul/li[1]/div[2]/strong")));
		property.setState(State.valueOfLabel(extractText(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[5]/ul/li[1]/div[3]/strong")));
		property.setSize(extractSize(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[5]/ul/li[2]/div/strong"));
		property.setPriceForMeter(extractPriceForMeter(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[5]/ul/li[3]/div[1]/strong"));
		property.setPropertyId(extractText(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[5]/ul/li[3]/div[2]/strong"));
		property.setDescription(extractText(webDriver, "/html/body/div[6]/div[1]/section/div[1]/div[2]/div/div[1]/div[2]/div[6]/div"));
		property.setInserted(LocalDate.now());
		property.setLink(url);
		property.setBlacklisted(false);
		property.setValid(true);
		return property;
	}

	private String extractText(WebDriver webDriver, String xpath) {
		try {
			return webDriver.findElement(By.xpath(xpath)).getText();
		} catch (NoSuchElementException exception) {
		}
		return null;
	}

	private String extractAttribute(WebDriver webDriver, String xpath, String attribute) {
		try {
			return webDriver.findElement(By.xpath(xpath)).getAttribute(attribute);
		} catch (NoSuchElementException exception) {
		}
		return null;
	}

	private Integer extractIntegerFromText(WebDriver webDriver, String xpath) {
		try {
			String text = webDriver.findElement(By.xpath(xpath)).getText();
			return Integer.parseInt(text.replaceAll("[\\D]", ""));
		} catch (NoSuchElementException | NumberFormatException exception) {
		}
		return null;
	}

	private Float extractGps(WebDriver webDriver, String xpath, String attribute) {
		try {

			String gps = webDriver.findElement(By.xpath(xpath)).getAttribute(attribute);
			return Float.parseFloat(gps);
		} catch (NoSuchElementException | NumberFormatException exception) {
		}
		return null;
	}

	private Double extractPrice(WebDriver webDriver, String xpath) {
		try {

			String price = webDriver.findElement(By.xpath(xpath)).getText();
			return Double.parseDouble(price.replaceAll("[\\D]", ""));
		} catch (NoSuchElementException | NumberFormatException exception) {
		}
		return null;
	}

	private Double extractPriceForMeter(WebDriver webDriver, String xpath) {
		try {

			String price = webDriver.findElement(By.xpath(xpath)).getText();
			return Double.parseDouble(price.replace(" €/m²", "").replace(" ", "").replace(",", "."));
		} catch (NoSuchElementException | NumberFormatException exception) {
		}
		return null;
	}

	private Integer extractSize(WebDriver webDriver, String xpath) {
		try {

			String size = webDriver.findElement(By.xpath(xpath)).getText();
			return Integer.parseInt(size.replace(" m2", ""));
		} catch (NoSuchElementException | NumberFormatException exception) {
		}
		return null;
	}
}
