package com.simplecards;

import java.util.*;

public class PokerEngine extends BaseEngine {

    /**
     *
     * Deal a new hand.
     *
     * @return A newly dealt poker hand.
     *
     */
    public Hand dealHand() {
        Hand hand = new PokerHand( new Card[0] );
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

        // Debug
        // System.out.println("Debug...");

        //
        // Video Poker will use a 52 card deck.
        //
        // Build a deck. 
        //
        for(String face: FACES) {
            for(String suit: SUITS) {
                Card card = new PokerCard(face, suit);
                if(!exclude.contains(card)) {
                    deck.add(card);
                } else {
                    // Debug
                    // System.out.println("Excluding " + card);
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

        PokerEngine engine = new PokerEngine();

        if(op.equals("deal")) {
            Hand hand = engine.dealHand();
            System.out.println("Hand: " + hand);
            return;
        }

        //
        // Otherwise, assume cards are passed to us
        //

        // List<Card> exclude = new ArrayList<Card>();
        Hand exclude = new PokerHand();

        Card[] cards = new PokerCard[5];
        for(int i = 0; i < 5; i++) {
            cards[i] = new PokerCard(args[i]);
            exclude.add(cards[i]);
        }

        System.out.println("Hand: " + exclude);

        String command = args[5];

        for(int i = 0; i < 5; i++) {
            if(command.getBytes()[i] == 'x') {
                // Debug
                // System.out.println("Keeping " + cards[i]);
            } else {
                cards[i] = null;
            }
        }

        for(int i = 0; i < cards.length; i++) {
            if(cards[i] == null) {
                cards[i] = engine.dealCard(exclude);
                exclude.add(cards[i]);
            }
        }

        Hand hand = new PokerHand( cards );
        System.out.println("Hand: " + hand);

    }

}

