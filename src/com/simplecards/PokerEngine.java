package com.simplecards;

import java.util.*;

public class PokerEngine extends BaseEngine {

    /**
     *
     * Parse a String representation of a hand of cards.
     *
     */
    public Hand parseCards(String[] strCards) {
        Card[] cards = new Card[strCards.length];
        for(int i = 0; i < strCards.length; i++) {
            //
            // Parse cards out of Strings, representing the current hand.
            //
            String face = strCards[i].substring(0, strCards[i].length() - 1);
            String suit = strCards[i].substring(strCards[i].length() - 1);
            cards[i] = new Card(face, suit);
        }
        return new Hand(cards);
    }


    /**
     *
     * Deal a new hand.
     *
     * @return A newly dealt poker hand.
     *
     */
    public Hand dealHand() {
        Hand hand = new Hand( new Card[0] );
        for(int i = 0; i < 5; i++) {
            hand.add( dealCard(hand) );
        }
        return hand;
    }

    /**
     *
     * Deal a single card.
     *
     * @return A single card from the deck.
     *
     */
    public Card dealCard(Hand exclude) {
        
        List<Card> deck = new ArrayList<Card>();

        //
        // Video Poker will use a 52 card deck.
        //
        // Build a deck. 
        //
        for(String face: FACES) {
            for(String suit: SUITS) {
                Card card = new Card(face, suit);
                if(!exclude.contains(card)) {
                    deck.add(card);
                }
            }
        }

        Collections.shuffle(deck);

        return deck.get(random.nextInt(deck.size()));

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

        PokerEngine engine = new PokerEngine();

        if(op.equals("parseCards")) {
            Hand hand = engine.parseCards(strCards);
            System.out.println("Hand: " + hand);
            return;
        }

        if(op.equals("dealCard")) {
            Card card = engine.dealCard();
            Hand hand = new Hand( new Card[] { card } );
            System.out.println("Hand: " + hand);
            return;
        }

        if(op.equals("deal")) {
            Hand hand = null;

            if(args.length > 1) {
                Hand excludeCards = engine.parseCards(strCards);
                hand = engine.dealCard(excludeCards);
            } else {
                hand = engine.dealHand();
            }
            System.out.println("Hand: " + hand);
            return;
        }

    }

}

