package de.raistlin77.gl.cube.tiles;

final public class Corner extends Tile {
	
	public Corner( Order[]o, Color[]c ){
		super( o, c, 3 );
	}

	public Corner dup() {
		return new Corner( o.clone(), c.clone() );
	}

}
