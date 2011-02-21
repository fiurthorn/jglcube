/*
 * 
 */
package de.raistlin77.gl.cube.tiles;

// TODO: Auto-generated Javadoc
/**
 * The Class Edge.
 */
final public class Edge extends Tile {

	/**
	 * Instantiates a new edge.
	 *
	 * @param o the o
	 * @param c the c
	 */
	public Edge( Order[]o, Color[]c ){
		super( o, c, 2 );
	}

	/**
	 * Dup.
	 *
	 * @return the edge
	 */
	public Edge dup() {
		return new Edge( o.clone(), c.clone() );
	}

}
