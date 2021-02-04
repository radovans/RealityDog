package cz.sinko.realitydog.entity;

public enum State {

	NOVOSTAVBA("Novostavba"),
	CIASTOCNA_REKONSTRUKCIA("Čiastočná rekonštrukcia"),
	KOMPLETNA_REKONSTRUKCIA("Kompletná rekonštrukcia"),
	POVODNY_STAV("Pôvodný stav"),
	VO_VYSTAVBE("Vo výstavbe"),
	DEVELOPERSKY_PROJEKT("Developerský projekt");

	public final String label;

	private State(String label) {
		this.label = label;
	}

	public static State valueOfLabel(String label) {
		for (State e : values()) {
			if (e.label.equals(label)) {
				return e;
			}
		}
		return null;
	}

}
