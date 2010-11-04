package de.raistlin77.gl.cube;

/**
 * The Class represent a angle Class.
 * The Angle only can be form -179deg till 180deg.
 * If the Angles is bigger or smaller the
 * angle will correct to the real scope.
 *
 * There are a few of operations to handle the class.
 *
 */
public class ANGLE {
	
	private int ang=0;
	
	private void norm() {
		while ( ang<( -179 ) )
			ang+=360;
		while ( ang>180 )
			ang-=360;
	}

	public ANGLE(){
	}

	public ANGLE( int ang ){
		this.ang = ang;
		norm();
	}
	
	public ANGLE add( ANGLE a ){
		this.ang += a.ang;
		norm();
		return this;
	}
	
	public ANGLE add( int ang ){
		this.ang += ang;
		norm();
		return this;
	}
	
	public ANGLE sub( ANGLE a ){
		this.ang -= a.ang;
		norm();
		return this;
	}

	public ANGLE sub( int ang ){
		this.ang -= ang;
		norm();
		return this;
	}
	
	public ANGLE set( int ang ){
		this.ang = ang;
		norm();
		return this;
	}
	
	public ANGLE set( ANGLE a ){
		this.ang = a.ang;
		norm();
		return this;
	}
	
	public int toInt(){
		return ang;
	}
	
	public float toFloat(){
		return ang;
	}
	
}
