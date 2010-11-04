package de.raistlin77.gl.cube.tiles;

final public class Area extends Tile {
	
	public Area( Order[]o, Color[]c ){
		super( o, c, 1 );
	}
	
	public Area dup() {
		return new Area( o.clone(), c.clone() );
	}

}
