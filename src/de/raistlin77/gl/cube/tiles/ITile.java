/*
 * 
 */
package de.raistlin77.gl.cube.tiles;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITile.
 */
public interface ITile {
	
	/**
	 * The Enum Color.
	 */
	public enum Color { 
		
		/** The BLACK. */
		BLACK,
		
		/** The RED. */
		RED,
		
		/** The GREEN. */
		GREEN,
		
		/** The BLUE. */
		BLUE,
		
		/** The WHITE. */
		WHITE,
		
		/** The ORANGE. */
		ORANGE,
		
		/** The YELLOW. */
		YELLOW
	}
	
	/**
	 * The Enum Order.
	 */
	public enum Order {
		
		/** The ABOVE. */
		ABOVE,
		
		/** The BELOW. */
		BELOW,
		
		/** The LEFT. */
		LEFT,
		
		/** The RIGHT. */
		RIGHT,
		
		/** The AHEAD. */
		AHEAD,
		
		/** The BEHIND. */
		BEHIND
	}

	/**
	 * T ri.
	 *
	 * @param ca the ca
	 * @return true, if successful
	 */
	public boolean TRi( Color[]ca );
	
	/**
	 * Fa rt.
	 *
	 * @param o the o
	 * @return the color
	 */
	public Color FaRT(Order o);
	
}
