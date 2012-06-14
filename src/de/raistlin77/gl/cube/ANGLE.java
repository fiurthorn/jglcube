/*
 * 
 */
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

    /** The ang. */
    private int ang = 0;

    /**
     * Norm.
     */
    private void norm() {
        while ( ang < (-179) )
            ang += 360;
        while ( ang > 180 )
            ang -= 360;
    }

    /**
     * Instantiates a new aNGLE.
     */
    public ANGLE() {
    }

    /**
     * Instantiates a new aNGLE.
     * 
     * @param ang
     *            the ang
     */
    public ANGLE(int ang) {
        this.ang = ang;
        norm();
    }

    /**
     * Adds the.
     * 
     * @param a
     *            the a
     * @return the aNGLE
     */
    public ANGLE add(ANGLE a) {
        this.ang += a.ang;
        norm();
        return this;
    }

    /**
     * Adds the.
     * 
     * @param ang
     *            the ang
     * @return the aNGLE
     */
    public ANGLE add(int ang) {
        this.ang += ang;
        norm();
        return this;
    }

    /**
     * Sub.
     * 
     * @param a
     *            the a
     * @return the aNGLE
     */
    public ANGLE sub(ANGLE a) {
        this.ang -= a.ang;
        norm();
        return this;
    }

    /**
     * Sub.
     * 
     * @param ang
     *            the ang
     * @return the aNGLE
     */
    public ANGLE sub(int ang) {
        this.ang -= ang;
        norm();
        return this;
    }

    /**
     * Sets the.
     * 
     * @param ang
     *            the ang
     * @return the aNGLE
     */
    public ANGLE set(int ang) {
        this.ang = ang;
        norm();
        return this;
    }

    /**
     * Sets the.
     * 
     * @param a
     *            the a
     * @return the aNGLE
     */
    public ANGLE set(ANGLE a) {
        this.ang = a.ang;
        norm();
        return this;
    }

    /**
     * To int.
     * 
     * @return the int
     */
    public int toInt() {
        return ang;
    }

    /**
     * To float.
     * 
     * @return the float
     */
    public float toFloat() {
        return ang;
    }

}
