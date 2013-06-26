package com.simplecards;

/**
 *
 */
public class BlackjackCard extends Card {

    public BlackjackCard(String face, String suit) {
        super(face, suit);
    }

    public Integer getValue() { 
        Integer value = BlackjackEngine.VALUE_MAP.get(_face);
        if(value == null) {
            value = Integer.parseInt(_face);
        }
        return value;
    }

}

