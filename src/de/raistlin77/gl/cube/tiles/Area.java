/*
 * 
 */
package de.raistlin77.gl.cube.tiles;

// TODO: Auto-generated Javadoc
/**
 * The Class Area.
 */
final public class Area extends Tile {
	
	/**
	 * Instantiates a new area.
	 *
	 * @param o the o
	 * @param c the c
	 */
	public Area( Order[]o, Color[]c ){
		super( o, c, 1 );
	}
	
	/**
	 * Dup.
	 *
	 * @return the area
	 */
	public Area dup() {
		return new Area( o.clone(), c.clone() );
	}

}
