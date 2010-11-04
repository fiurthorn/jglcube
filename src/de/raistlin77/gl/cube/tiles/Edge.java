package de.raistlin77.gl.cube.tiles;

final public class Edge extends Tile {

	public Edge( Order[]o, Color[]c ){
		super( o, c, 2 );
	}

	public Edge dup() {
		return new Edge( o.clone(), c.clone() );
	}

}
