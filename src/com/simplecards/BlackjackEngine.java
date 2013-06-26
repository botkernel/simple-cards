package com.simplecards;

import java.util.*;

public class BlackjackEngine extends BaseEngine {

    //
    // Mapping of face values which cannot be parsed as integers.
    //
    public static final Map<String, Integer> VALUE_MAP = 
                                                new HashMap<String, Integer>();
    //                                             
    // Map out face values back to integer values.
    // If we can't find the String value in here, assume we need to
    // parse with something like Integer.parseInt();
    //
    static {
        VALUE_MAP.put(ACE,      new Integer(11));
        VALUE_MAP.put(JACK,     new Integer(10));
        VALUE_MAP.put(QUEEN,    new Integer(10));
        VALUE_MAP.put(KING,     new Integer(10));
    }

    /**
     *
     * Parse a String representation of cards.
     *
     */
    public Hand parseCards(String[] strCards) {
        BlackjackCard[] cards = new BlackjackCard[strCards.length];
        for(int i = 0; i < strCards.length; i++) {
            //
            // Parse cards out of Strings, representing the current hand.
            //
            String face = strCards[i].substring(0, strCards[i].length() - 1);
            String suit = strCards[i].substring(strCards[i].length() - 1);
            cards[i] = new BlackjackCard(face, suit);
        }
        return new BlackjackHand(cards);
    }


    /**
     *
     * Deal card onto an existing hand.
     *
     * @param hand  Existing hand of cards
     *
     * @return A new hand consisting of the existing cards, plus
     *          a newly dealt card.
     *
     */
    public Hand dealCard(Hand hand) {
        BlackjackCard[] inputCards = (BlackjackCard[])hand.getCards();
        BlackjackCard[] cards = new BlackjackCard[inputCards.length + 1];
        for(int i = 0; i < inputCards.length; i++) {
            cards[i] = inputCards[i]; 
        }
        cards[inputCards.length] = (BlackjackCard)dealCard();
        return new BlackjackHand( cards );
    }

    /**
     *
     * Deal a new hand.
     *
     * @return A newly dealt blackjack hand.
     *
     */
    public Hand dealHand() {
        return new BlackjackHand( 
                        new BlackjackCard[] { 
                                                dealCard(), 
                                                dealCard() } );
    }

    /**
     *
     * Deal a single card.
     *
     * @return A single card from the shoe.
     *
     */
    public BlackjackCard dealCard() {
        return new BlackjackCard(    
                            FACES[random.nextInt(FACES.length)],
                            SUITS[random.nextInt(SUITS.length)]     );
    }

    /**
     *
     * A main() method for command line testing and examples.
     *
     * Usage:
     * BlackjackEngine <operation> [<card> ...]
     *
     * E.g.
     * BlackjackEngine deal 8♣ A♥
     *
     */
    public static void main(String[] args) {
   
        String op = args[0];
        String[] strCards = null;

        if(args.length > 1) {
            // Create an array with the remaining command line options
            strCards = new String[args.length - 1];
            for(int i = 0; i < strCards.length; i++) {
                strCards[i] = args[i+1]; 
            }
        } 

        BlackjackEngine engine = new BlackjackEngine();

        if(op.equals("parseCards")) {
            Hand hand = engine.parseCards(strCards);
            System.out.println("Hand: " + hand);
            return;
        }

        if(op.equals("dealCard")) {
            BlackjackCard card = engine.dealCard();
            Hand hand = new BlackjackHand( new BlackjackCard[] { card } );
            System.out.println("Hand: " + hand);
            return;
        }

        if(op.equals("deal")) {
            Hand hand = null;

            if(args.length > 1) {
                Hand existingHand = engine.parseCards(strCards);
                hand = engine.dealCard(existingHand);
            } else {
                hand = engine.dealHand();
            }
            System.out.println("Hand: " + hand);
            return;
        }

    }

}

