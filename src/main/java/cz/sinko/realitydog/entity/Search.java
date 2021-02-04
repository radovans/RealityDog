package cz.sinko.realitydog.entity;

import java.util.List;

import lombok.Data;

@Data
public class Search {

	private List<Location> locations;
	private List<PropertyType> propertyTypes;
	private State state;
	private int priceFrom;
	private int priceTo;
	private int sizeFrom;
	private int sizeTo;
	private List<String> keywords;
	private int page;

}
