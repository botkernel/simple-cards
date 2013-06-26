package com.simplecards;

import java.util.*;

public abstract class BaseEngine {

    protected static Random random = 
                            new Random(System.currentTimeMillis());    

    //
    // Values for suits
    //
    public static final String SPADE   = "♠";
    public static final String CLUB    = "♣";
    public static final String HEART   = "♥"; 
    public static final String DIAMOND = "♦";

    //
    // Set of all suits
    //
    public static final String[] SUITS = { SPADE, CLUB, HEART, DIAMOND };

    //
    // Values for faces
    //
    public static final String ACE      = "A";
    public static final String TWO      = "2";
    public static final String THREE    = "3";
    public static final String FOUR     = "4";
    public static final String FIVE     = "5";
    public static final String SIX      = "6";
    public static final String SEVEN    = "7";
    public static final String EIGHT    = "8";
    public static final String NINE     = "9";
    public static final String TEN      = "10";
    public static final String JACK     = "J";
    public static final String QUEEN    = "Q";
    public static final String KING     = "K";

    //
    // Set of all faces
    //
    public static final String[] FACES = { 
            ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, 
            EIGHT, NINE, TEN, JACK, QUEEN, KING };



    /**
     *
     * Deal a new hand.
     *
     * @return A newly dealt hand for a card game.
     *
     */
    public abstract Hand dealHand();

    /**
     *
     * Deal a single card.
     *
     * @return A single card from the shoe.
     *
     */
    public Card dealCard() {
        return new Card(    
                            FACES[random.nextInt(FACES.length)],
                            SUITS[random.nextInt(SUITS.length)]     );
    }



}

