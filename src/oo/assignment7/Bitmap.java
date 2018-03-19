package oo.assignment7;

/**
 * Bitmap: A class for representing an N by N bitmap;
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Bitmap {
    private final boolean[][] raster;
    private final int size;
    
    /**
     * Creates an empty bitmap of size * size.
     * @param size The size of the bitmap (must be a power of 2).
     */
    public Bitmap(int size) {
        this.raster = new boolean[size][size];
        this.size = size;
    }

    /**
     * Gets a bit at the specified position
     * @param x: x coordinate
     * @param y: y coordinate
     */
    public boolean getBit( int x, int y ) {
        return raster[x][y];
    }
    
    /**
     * Sets a bit at the specified position
     * @param x: x coordinate
     * @param y: y coordinate
     * @param val: the bit value 
     */
    public void setBit( int x, int y, boolean val ){
        raster[x][y] = val;
    }
    
    /**
     * Converts a bitmap into a string
     * 1 is represented by '*'; 0 by 'O'
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
               sb.append( raster[x][y] ?  '*' : 'O' );
            }
            sb.append( '\n' );
        }

        return sb.toString();
    }
    
    /**
     * @return the size of the bitmap
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Fills a square area of a bitmap with value val
     * @param x: x coordinate of upper-left corner
     * @param y: y coordinate of upper-left corner
     * @param size: size of the square
     * @param val: the bit value 
      */
    public void fillArea( int x, int y, int size, boolean val ){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setBit(x+i, y+j, val);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Bitmap) {
            Bitmap other = (Bitmap)o;

            if(size != other.size) {
                return false;
            }

            for(int y = 0; y < size; ++y) {
                for(int x = 0; x < size; ++x) {
                    if(raster[x][y] != other.raster[x][y]) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
