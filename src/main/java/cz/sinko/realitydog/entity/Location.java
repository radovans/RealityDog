package cz.sinko.realitydog.entity;

import java.util.HashMap;
import java.util.Map;

public enum Location {
	BRATISLAVA_PETRZALKA("Bratislava V - Petržalka, okres Bratislava", "t11"),
	BRATISLAVA_JAROVCE("Bratislava V - Jarovce, okres Bratislava", "t217"),
	BRATISLAVA_RUSOVCE("Bratislava V - Rusovce, okres Bratislava", "t218"),
	BRATISLAVA_CUNOVO("Bratislava V - Čunovo, okres Bratislava","t215"),
	BRATISLAVA_ZAHORSKA_BYSTRICA("Bratislava IV - Záhorská Bystrica, okres Bratislava","t219"),
	BRATISLAVA_DEVINSKA_NOVA_VES("Bratislava IV - Devínska Nová Ves, okres Bratislava","t6"),
	BRATISLAVA_DEVIN("Bratislava IV - Devín, okres Bratislava","t216"),
	BRATISLAVA_LAMAC("Bratislava IV - Lamač, okres Bratislava","t9"),
	BRATISLAVA_DUBRAVKA("Bratislava IV - Dúbravka, okres Bratislava","t7"),
	BRATISLAVA_KAROVA_VES("Bratislava IV - Karlova Ves, okres Bratislava","t8"),
	BRATISLAVA_VAJNORY("Bratislava III - Vajnory, okres Bratislava","t16"),
	BRATISLAVA_RACA("Bratislava III - Rača, okres Bratislava","t13"),
	BRATISLAVA_NOVE_MESTO("Bratislava III - Nové Mesto, okres Bratislava","t10"),
	BRATISLAVA_PODUNAJSKE_BISKUPICE("Bratislava II - Podunajské Biskupice, okres Bratislava","t3027"),
	BRATISLAVA_VRAKUNA("Bratislava II - Vrakuňa, okres Bratislava","t17"),
	BRATISLAVA_RUZINOV("Bratislava II - Ružinov, okres Bratislava","t14"),
	BRATISLAVA_STARE_MESTO("Bratislava I - Staré Mesto, okres Bratislava","p161"),
	BRATISLAVA_OKRES("Bratislava - okres","d14");

	private static final Map<String, Location> BY_LABEL = new HashMap<>();
	private static final Map<String, Location> BY_ID = new HashMap<>();

	static {
		for (Location e : values()) {
			BY_LABEL.put(e.label, e);
			BY_ID.put(e.id, e);
		}
	}

	public final String label;
	public final String id;

	private Location(String label, String id) {
		this.label = label;
		this.id = id;
	}

	public static Location valueOfLabel(String label) {
		return BY_LABEL.get(label);
	}

	public static Location valueOfId(String id) {
		return BY_ID.get(id);
	}

}
