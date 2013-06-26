package com.simplecards;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * Represents a hand (set of cards.)
 *
 */
public class BlackjackHand extends Hand {

    public BlackjackHand(BlackjackCard[] cards) {
        super(cards);
    }
        
    public String toString() {
        String ret = super.toString();

        //
        // Now add the blackjack specific modifier to the card
        //

        ret += "(";
        if(isBlackjack()) {
            ret += "BLACKJACK";
        } else {
            Integer[] values = getValues();
            if(values.length == 0) {
                ret += "BUSTED";
            } else {
                for(int i = 0; i < values.length; i++) {
                    if(i > 0) {
                        ret += ", or ";
                    }
                    ret += values[i].intValue();
                }
            }
        }
        ret += ")";

        return ret;
    }

    /**
     * Return true if the hand is busted.
     */
    public boolean isBusted() {
        return (getValues().length == 0);
    }

    /**
     * A hand is blackjack if it has only two cards, and
     * has a value of 21.
     */
    public boolean isBlackjack() {

        if(_cards.length != 2) {
            return false;
        }

        Integer[] values = getValues();
        for(Integer value: values) {
            if(value.intValue() == 21) {
                return true;
            }
        }

        return false;
    }

    /**
     * A hand can have more than one value, if it has
     * one or more aces.
     *
     * If no valid values are returned, then the hand is busted.
     *
     */
    public Integer[] getValues() {
        List<Integer> ret = new ArrayList<Integer>();

        int total = 0;
        for(int i = 0; i < _cards.length; i++) {
            Integer cardVal = ((BlackjackCard)_cards[i]).getValue();
            total += cardVal.intValue(); 
        }

        if(total < 22) {
            ret.add(new Integer(total));
        }

        // Now substitue lower ace values.
        for(int i = 0; i < _cards.length; i++) {
            if(_cards[i].getFace().equals(BlackjackEngine.ACE)) {
                total -= 10;
                if(total < 22) {
                    ret.add(new Integer(total));
                }
            }
        } 

        return ret.toArray(new Integer[0]);
    }

}

