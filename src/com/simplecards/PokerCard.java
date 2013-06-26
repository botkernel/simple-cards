package com.simplecards;

/**
 *
 */
public class PokerCard extends Card {

    private int _value = -1;

    public PokerCard(String str) { super(str); }

    public PokerCard(String face, String suit) { super(face, suit); }

    /**
     *
     * Used to calculate straights.  
     * Returns the "order" of the card's face if you were to
     * sort starting at ace and ending at king
     *
     * Also for two of a kind, check for minimum jack value
     * Jack value is 10 (BaseEngine.FACES[10])
     *
     */
    public int getValue() {
       
        //
        // Special case for handling ace as high
        //
        if(_value != -1) {
            return _value;
        }
    
        return getValue(_face);

    }

    public static int getValue(String face) {

        for(int i = 0; i < BaseEngine.FACES.length; i++) {
            if(face.equals(BaseEngine.FACES[i])) {
                return i;
            }
        }

        //
        // Should be an error.
        // 
        return -1;
    }

    public void setValue(int value) {
        //
        // allow us to handle aces high
        //
        _value = value;
    }

}

