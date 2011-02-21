/*
 * 
 */
package de.raistlin77.gl.cube.tiles;

// TODO: Auto-generated Javadoc
/**
 * The Class Tile.
 */
public abstract class Tile implements ITile {

	/** The count. */
	protected int count = 0;

	/** The o. */
	protected Order[] o;
	
	/** The c. */
	protected Color[] c;

	/**
	 * Instantiates a new tile.
	 *
	 * @param o the o
	 * @param c the c
	 * @param count the count
	 */
	protected Tile( Order[]o, Color[]c, int count ) {
		this.count = count;
		if( count!=o.length || count!=c.length ) {
			System.err.println( String.format(" count:%d o:%d c:%s", count, o.length, c.length) );
			throw new RuntimeException( "Falsche Anzahl Farben bzw. Richtungen!" );
		}
		this.o = o;
		this.c = c;
	}

	/**
	 * T ri.
	 *
	 * @param c the c
	 * @param ca the ca
	 * @return true, if successful
	 */
	private boolean TRi(Color c, Color[] ca) {
		for ( int i = 0; i < ca.length; i++ ) {
			if ( ca[i] == c ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Hat Die Kante die Farben? Prüfte nach ob diese Teile die übergebene
	 * Farben bestize die ausrichtung ist völlig egal.
	 *
	 * @param ca the ca
	 * @return true, if successful
	 */
	public boolean TRi(Color[] ca) {
		if ( ca.length != count )
			throw new RuntimeException( "falsche Anzahl von Farben." );
		else {
			for ( int i = 0; i < count; i++ ) {
				if ( !TRi( c[i], ca ) ) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Liefert bei übergebenenem Teil und Ausrichtungung die Farbe zurück
	 * Funktion ist überladen für Flaechen , Kanten und Ecken und inline
	 * definiert.
	 *
	 * @param o the o
	 * @return the color
	 */
	public Color FaRT(Order o) {
		for ( int i = 0; i < count; i++ ) {
			if ( this.o[i] == o ) {
				return this.c[i];
			}
		}
		return null;
	}

	/**
	 * Liefert bei übergebenenem Teil und Ausrichtungung die Farbe zurück
	 * Funktion ist überladen für Flaechen , Kanten und Ecken und inline
	 * definiert.
	 *
	 * @param o the o
	 * @return the char
	 */
	public static char colorChar(Color o) {
		switch( o ){
		case BLACK:
			return '-';
		case RED:
			return 'R';
		case GREEN:
			return 'G';
		case BLUE:
			return 'L';
		case WHITE:
			return 'B';
		case ORANGE:
			return 'g';
		case YELLOW:
			return 'C';
		}
		return '*';
	}
	
	/**
	 * die funktion liefert in abhängikteit der drehrichtung die Richtung zurück
	 * von der die Farbe stammen muß. z.B. Drehrichtung 1 die vorderen Flächen
	 * stammt die neue Farbe von unten
	 *
	 * @param move the move
	 * @param AR the aR
	 * @return the order
	 */
	public Order WR( byte move, Order AR) {
		  // wertet die Drehrichtung aus und liefert die zukopierent richtung
		  // zurück
		  switch ( move ) {
		  case 1:
		  case 2:
		  case 3:
		    if ( AR==Order.ABOVE ) return Order.AHEAD;
		    else if ( AR==Order.AHEAD ) return Order.BELOW;
		    else if ( AR==Order.BELOW ) return Order.BEHIND;
		    else if ( AR==Order.BEHIND ) return Order.ABOVE;
		    else return AR;
//		    break;
		  case 4:
		  case 5:
		  case 6:
		    if ( AR==Order.ABOVE ) return Order.BEHIND;
		    else if ( AR==Order.BEHIND ) return Order.BELOW;
		    else if ( AR==Order.BELOW ) return Order.AHEAD;
		    else if ( AR==Order.AHEAD ) return Order.ABOVE;
		    else return AR;
//		    break;
		  case 7:
		  case 8:
		  case 9:
		    if ( AR==Order.AHEAD ) return Order.LEFT;
		    else if ( AR==Order.LEFT ) return Order.BEHIND;
		    else if ( AR==Order.BEHIND ) return Order.RIGHT;
		    else if ( AR==Order.RIGHT ) return Order.AHEAD;
		    else return AR;
//		    break;
		  case 10:
		  case 11:
		  case 12:
		    if ( AR==Order.AHEAD ) return Order.RIGHT;
		    else if ( AR==Order.RIGHT ) return Order.BEHIND;
		    else if ( AR==Order.BEHIND ) return Order.LEFT;
		    else if ( AR==Order.LEFT ) return Order.AHEAD;
		    else return AR;
//		    break;
		  case 13:
		  case 14:
		  case 15:
		    if ( AR==Order.ABOVE ) return Order.RIGHT;
		    else if ( AR==Order.RIGHT ) return Order.BELOW;
		    else if ( AR==Order.BELOW ) return Order.LEFT;
		    else if ( AR==Order.LEFT ) return Order.ABOVE;
		    else return AR;
//		    break;
		  case 16:
		  case 17:
		  case 18:
		    if ( AR==Order.ABOVE ) return Order.LEFT;
		    else if ( AR==Order.LEFT ) return Order.BELOW;
		    else if ( AR==Order.BELOW ) return Order.RIGHT;
		    else if ( AR==Order.RIGHT ) return Order.ABOVE;
		    else return AR;
//		    break;
		  }
		  return AR;
	}
	
	/**
	 * Swap.
	 *
	 * @param t the t
	 * @param move the move
	 */
	public void swap( Tile t, byte move ){
		for(int i=0; i<count; i++){
			newColor( o[i], t.FaRT( WR( move, o[i] ) ) );			
		}
	}
	
	/**
	 * New color.
	 *
	 * @param o the o
	 * @param c the c
	 */
	private void newColor( Order o, Color c ){
		for(int i=0; i<count; i++){
			if( o==this.o[i] ){
				this.c[i]=c;
				return;
			}
		}
	}

}
