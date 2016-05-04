package de.raistlin77.gl.cube;

class Counter {
    private int count = 0;
    private String format;

    /**
     * 
     */
    public Counter() {
        this( 0 );
    }

    /**
     * @param start
     *            start value
     */
    public Counter(int start) {
        this( start, "%08d" );
    }

    /**
     * @param format
     *            format
     */
    public Counter(String format) {
        this( 0, format );
    }

    /**
     * @param start
     *            start value
     * @param format
     *            format
     */
    public Counter(int start, String format) {
        this.format = format;
        this.count = start;
    }

    /**
     * @return String
     */
    public String format() {
        return String.format( format, count );
    }

    /**
     * @return String
     */
    synchronized public String formatInc() {
        String s = String.format( format, count );
        inc();
        return s;
    }

    /**
     * @param count
     *            count
     * @return String
     */
    public String format(int count) {
        return String.format( format, count );
    }

    /**
     * 
     */
    public void reset() {
        count = 0;
    }

    /**
     * 
     */
    public void inc() {
        count++;
    }

    /**
     * @param l
     *            void
     */
    public void inc(int l) {
        count += l;
    }

    /**
     * 
     */
    public void dec() {
        count--;
    }

    @Override
    public String toString() {
        return format();
    }

    /**
     * @return count
     */
    public int value() {
        return count;
    }

    /**
     * @return isNull
     */
    public boolean isNull() {
        return count == 0;
    }
}
