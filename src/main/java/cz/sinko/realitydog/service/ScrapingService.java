package cz.sinko.realitydog.service;

import java.util.List;

import org.openqa.selenium.WebDriver;

import cz.sinko.realitydog.entity.Property;
import cz.sinko.realitydog.entity.Search;

public interface ScrapingService {

	void search(WebDriver webDriver, Search search) throws InterruptedException;

	List<String> getListOfPropertiesFromSearchSite(WebDriver webDriver, Search search) throws InterruptedException;

	Property scrapeProperty(WebDriver webDriver, String url) throws InterruptedException;
}
