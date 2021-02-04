package cz.sinko.realitydog.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "property")
@Data
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String propertyId;
	private String name;
	private String address;
	@Enumerated(EnumType.STRING)
	private Location location;
	private Float Latitude;
	private Float Longitude;
	private Double price;
	@Enumerated(EnumType.STRING)
	private PropertyType propertyType;
	@Enumerated(EnumType.STRING)
	private State state;
	private Integer size;
	private Double priceForMeter;
	@Column(columnDefinition = "TEXT")
	private String description;
	private LocalDate inserted;
	private LocalDate invalidated;
	private String link;
	private Boolean blacklisted;
	private Boolean suitable;
	private Boolean valid;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Property property = (Property) o;
		return Objects.equals(address, property.address) &&
				location == property.location &&
				Objects.equals(price, property.price) &&
				propertyType == property.propertyType &&
				state == property.state &&
				Objects.equals(size, property.size) &&
				Objects.equals(priceForMeter, property.priceForMeter);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, location, price, propertyType, state, size, priceForMeter);
	}
}
