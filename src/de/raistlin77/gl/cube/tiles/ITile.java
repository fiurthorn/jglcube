package de.raistlin77.gl.cube.tiles;

public interface ITile {
	
	public enum Color { 
		BLACK,
		RED,
		GREEN,
		BLUE,
		WHITE,
		ORANGE,
		YELLOW
	}
	
	public enum Order {
		ABOVE,
		BELOW,
		LEFT,
		RIGHT,
		AHEAD,
		BEHIND
	}

	public boolean TRi( Color[]ca );
	public Color FaRT(Order o);
	
}
