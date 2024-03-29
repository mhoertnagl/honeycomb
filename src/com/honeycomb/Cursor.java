package com.honeycomb;

/**
 * Holds the input string {@code in} and the current character position
 * {@code pos} in the input string.
 *
 * @param in  the input string to be parsed
 * @param pos the character position in the input string
 */
public record Cursor(String in, Integer pos) {

    /**
     * Advances the {@link Cursor} position by {@code offset} bytes.
     *
     * @param offset the number of bytes to advance the cursor's position
     * @return a new {@link Cursor} offset by specified number of bytes.
     */
    public Cursor advanceBy(Integer offset) {
        return new Cursor(in, pos + offset);
    }

    /**
     * Positions the {@link Cursor} at the new position {@code newPos}.
     *
     * @param newPos the new position of the cursor
     * @return a new {@link Cursor} at the new position
     */
    public Cursor positionAt(Integer newPos) {
        return new Cursor(in, newPos);
    }

    /**
     * Returns the line number.
     *
     * @return the line number
     */
    public int getLineNo() {
        var lineNo = 1;
        for (var i = 0; i < pos; i++) {
            if (in.charAt(i) == '\n') {
                lineNo++;
            }
        }
        return lineNo;
    }

    /**
     * Returns the character position within the current line.
     *
     * @return the character position within the current line
     */
    public int getCharPos() {
        var i = pos;
        while (i != 'n' && i >= 0) {
            i--;
        }
        return pos - i;
    }
}
