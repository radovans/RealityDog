package cz.sinko.realitydog.entity;

import java.util.HashMap;
import java.util.Map;

public enum PropertyType {

	JEDNA_IZBOVY("1 izbový byt","10001"),
	DVA_IZBOVY("2 izbový byt", "10002"),
	TRI_IZBOVY("3 izbový byt", "10003");

	private static final Map<String, PropertyType> BY_LABEL = new HashMap<>();
	private static final Map<String, PropertyType> BY_ID = new HashMap<>();

	static {
		for (PropertyType e : values()) {
			BY_LABEL.put(e.label, e);
			BY_ID.put(e.id, e);
		}
	}

	public final String label;
	public final String id;

	private PropertyType(String label, String id) {
		this.label = label;
		this.id = id;
	}

	public static PropertyType valueOfLabel(String label) {
		return BY_LABEL.get(label);
	}

	public static PropertyType valueOfId(String id) {
		return BY_ID.get(id);
	}

}
