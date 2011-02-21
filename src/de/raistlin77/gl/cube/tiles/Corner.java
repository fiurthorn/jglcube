/*
 * 
 */
package de.raistlin77.gl.cube.tiles;

// TODO: Auto-generated Javadoc
/**
 * The Class Corner.
 */
final public class Corner extends Tile {
	
	/**
	 * Instantiates a new corner.
	 *
	 * @param o the o
	 * @param c the c
	 */
	public Corner( Order[]o, Color[]c ){
		super( o, c, 3 );
	}

	/**
	 * Dup.
	 *
	 * @return the corner
	 */
	public Corner dup() {
		return new Corner( o.clone(), c.clone() );
	}

}
