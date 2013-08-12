package com.simplecards;

import java.util.*;

/**
 *
 * Represents a poker hand (set of cards.)
 *
 */
public class PokerHand extends Hand {

    //
    // Payoffs
    //
    // Royal Flush     250 * bet
    // Straight Flush  50  * bet
    // Four of a kind  25  * bet
    // Full House      9   * bet
    // Flush           6   * bet
    // Straight        4   * bet
    // Three of a kind 3   * bet
    // Two Pair        2   * bet
    // Jacks or Better 1   * bet
    //
    // User loses a credit when it is entered in the machine to start game.
    // User is returned (if they have a winning hand) x*bet.
    //
    // Notes from:
    // http://www.worktheodds.com/university/videopoker.php
    //
    // It is important to note that all Video Poker payoffs are on a “for” 
    // and not a “to” basis. For example, a pair of Jacks pays 1 for 1 on 
    // a Jacks or Better Video Poker machine. 
    // This means that your original bet is returned, not doubled — 
    // in other words, you break even.
    //

    //
    // Used for checking for jack value,
    // Is basically the index in the BaseEngine's FACES array.
    //
    private static final int JACK_VALUE = 10;

    private static final int WIN_ROYAL          = 250;
    private static final int WIN_STRFLUSH       = 50;
    private static final int WIN_FOUROFKIND     = 25;
    private static final int WIN_FULLHOUSE      = 9;
    private static final int WIN_FLUSH          = 6;
    private static final int WIN_STRAIGHT       = 4;
    private static final int WIN_THREEODKIND    = 3;
    private static final int WIN_TWOPAIR        = 2;
    private static final int WIN_PAIR           = 1;

    public static final Map<Integer, String> PRETTY_NAME_MAP = 
        new HashMap<Integer, String>();
    static {
        PRETTY_NAME_MAP.put(new Integer(WIN_ROYAL), "ROYAL FLUSH");
        PRETTY_NAME_MAP.put(new Integer(WIN_STRFLUSH), "STRAIGHT FLUSH");
        PRETTY_NAME_MAP.put(new Integer(WIN_FOUROFKIND), "FOUR OF A KIND");
        PRETTY_NAME_MAP.put(new Integer(WIN_FULLHOUSE), "FULL HOUSE");
        PRETTY_NAME_MAP.put(new Integer(WIN_FLUSH), "FLUSH");
        PRETTY_NAME_MAP.put(new Integer(WIN_STRAIGHT), "STRAIGHT");
        PRETTY_NAME_MAP.put(new Integer(WIN_THREEODKIND), "THREE OF A KIND");
        PRETTY_NAME_MAP.put(new Integer(WIN_TWOPAIR), "TWO PAIR");
        PRETTY_NAME_MAP.put(new Integer(WIN_PAIR), "PAIR, JACKS OR BETTER");

    }

    public static final int[] WINNING_HANDS = new int[] {
        WIN_ROYAL,          WIN_STRFLUSH,   WIN_FOUROFKIND,
        WIN_FULLHOUSE,      WIN_FLUSH,      WIN_STRAIGHT,
        WIN_THREEODKIND,    WIN_TWOPAIR,    WIN_PAIR };


    public PokerHand() {
        super();
    }

    public PokerHand(Card[] cards) {
        super(cards);
    }

    public String toString() {
        String ret = super.toString();

        //
        // Determine winning hand
        //

        // System.out.println("Checking for winning hand...");

        if(isRoyal()) {
            ret += "(ROYAL FLUSH)";
        } else if(isStraightFlush()) {
            ret += "(STRAIGHT FLUSH)";
        } else if(isFourOfKind()) {
            ret += "(FOUR OF A KIND)";
        } else if(isFullHouse()) {
            ret += "(FULL HOUSE)";
        } else if(isFlush()) {
            ret += "(FLUSH)";
        } else if(isStraight()) {
            ret += "(STRAIGHT)";
        } else if(isThreeOfKind()) {
            ret += "(THREE OF A KIND)";
        } else if(isTwoPair()) {
            ret += "(TWO PAIR)";
        } else if(isPair()) {
            ret += "(PAIR, JACKS OR BETTER)";
        }

        return ret;
    }

    /**
     *
     * True if this hand is a winner. False otherwise.
     */
    public boolean isWinner() {
        return (getWinType() != 0);
    }

    /**
     *
     * The payoff multiplier (and WIN_TYPE) for this winning hand, or 0
     * if this is not a winning hand.
     *
     */
    public int getWinType() {
        if(isRoyal()) {
            return WIN_ROYAL;
        } 
        if(isStraightFlush()) {
            return WIN_STRFLUSH;
        } 
        if(isFourOfKind()) {
            return WIN_FOUROFKIND;
        } 
        if(isFullHouse()) {
            return WIN_FULLHOUSE;
        } 
        if(isFlush()) {
            return WIN_FLUSH;
        } 
        if(isStraight()) {
            return WIN_STRAIGHT;
        } 
        if(isThreeOfKind()) {
            return WIN_THREEODKIND;
        } 
        if(isTwoPair()) {
            return WIN_TWOPAIR;
        } 
        if(isPair()) {
            return WIN_PAIR;
        }
        return 0;
    }

    public boolean isRoyal() {

        if(isStraight() && isFlush()) {
            List<PokerCard> sorted = new ArrayList<PokerCard>();
            for(Card card: _cards) {
                sorted.add((PokerCard)card);
            }

            Collections.sort(sorted, new CardComparator());

            // Debug
            // for(PokerCard card: sorted) {
            //    // System.out.println("Sorted " + card.getFace());
            // }

            //
            // By default, ace is sorted low so check for
            // ace at beginning and king at end.
            //
            if( sorted.get(0).getFace().equals(BaseEngine.ACE) &&
                sorted.get(4).getFace().equals(BaseEngine.KING) ) {

                return true;
            }

            //
            // Alternatively, the ace object might already be marked as high
            //
            if( sorted.get(3).getFace().equals(BaseEngine.KING) &&
                sorted.get(4).getFace().equals(BaseEngine.ACE) ) {

                return true;
            }


        }

        // Debug
        // System.out.println("isRoyal() not a straight flush.");

        return false;
    }

    public boolean isStraightFlush() {
        return (isStraight() && isFlush());
    }

    public boolean isFlush() {
        return  _cards[0].getSuit().equals(_cards[1].getSuit()) &&
                _cards[0].getSuit().equals(_cards[2].getSuit()) &&
                _cards[0].getSuit().equals(_cards[3].getSuit()) &&
                _cards[0].getSuit().equals(_cards[4].getSuit());
    }

    public boolean isStraight() {

        List<PokerCard> sorted = new ArrayList<PokerCard>();
        
        for(Card card: _cards) {
            sorted.add((PokerCard)card);
        }

        Collections.sort(sorted, new CardComparator());

        PokerCard[] cards = (PokerCard[])sorted.toArray(new PokerCard[0]);

        // 
        // System.out.println("Checking straight with ace default");

        if(isStraightWalker(cards)) {
            return true;
        }

        //
        // System.out.println("Checking straight with ace high");

        //
        // Check for ace as high
        //
        if( cards[0].getFace().equals(BaseEngine.ACE) ) {
            cards[0].setValue(BaseEngine.FACES.length);

            Collections.sort(sorted, new CardComparator());
            cards = (PokerCard[])sorted.toArray(new PokerCard[0]);

            if(isStraightWalker(cards)) {
                return true;
            }
        }

        return false;
    }

    private boolean isStraightWalker(PokerCard[] cards) {
        int prev = -1;
        for(int i = 0; i < cards.length; i++) {
            if(i == 0) {
                // System.out.println("First card is " + 
                //        cards[i].getFace() + " " + 
                //        cards[i].getValue() );
                prev = cards[i].getValue();
            } else {
                int current = cards[i].getValue();
                if(current - prev != 1) {
                    // System.out.println(
                    //    cards[i].getFace() + " " +
                    //     cards[i].getValue() + " " +
                    //    " diff is " + (current - prev) );
                    return false;
                }
                prev = current;
            }
        }
        return true;
    }



    private boolean isXxxOfKind(int num) {
        return isXxxOfKind(num, -1); 
    }

    private boolean isXxxOfKind(int num, int minValue) {

        Map<String, Integer> countMap = getCountMap();

        for(String face: countMap.keySet()) {
            Integer count = countMap.get(face);
            if(count.intValue() == num) {
                if(minValue != -1) {
                    // check for minimum here (e.g. jacks)
                    int cardVal = PokerCard.getValue(face);

                    // Special case for ace, use high value. 
                    if(cardVal == 0) {
                        cardVal = BaseEngine.FACES.length;
                    }

                    if(cardVal >= minValue) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    private Map<String, Integer> getCountMap() {

        Map<String, Integer> countMap = new HashMap<String, Integer>();

        for(Card card: _cards) {
            String face = card.getFace();
            Integer faceCount = countMap.get(face);
            if(faceCount == null) {
                countMap.put(face, 1);
                continue;
            } else {
                int count = faceCount.intValue();
                count++;
                countMap.put(face, count);
            }
        }

        return countMap;
    }

    public boolean isFourOfKind() {
        return isXxxOfKind(4);
    }

    public boolean isFullHouse() {
        Map<String, Integer> countMap = getCountMap();
    
        int countPairs = 0;
        int countThrees = 0;
        for(Integer count: countMap.values()) {
            if(count.intValue() == 2) {
                countPairs++;
            }
            if(count.intValue() == 3) {
                countThrees++;
            }
        }

        return countPairs == 1 && countThrees == 1;
    }

    public boolean isThreeOfKind() {
        return isXxxOfKind(3);
    }

    public boolean isTwoPair() {
        Map<String, Integer> countMap = getCountMap();
    
        int countPairs = 0;
        for(Integer count: countMap.values()) {
            if(count.intValue() == 2) {
                countPairs++;
            }
        }

        return countPairs == 2;
    }

    public boolean isPair() {
        return isXxxOfKind(2, JACK_VALUE);
    }

    private class CardComparator implements Comparator<PokerCard> {

        public int compare(PokerCard o1, PokerCard o2) {
            
            // debug
            // System.out.println("Comparing " + o1.getValue() + " " 
            //                    + o2.getValue() );

            if(o1.getValue() < o2.getValue()) {
                return -1;
            }
            if(o1.getValue() > o2.getValue()) {
                return 1;
            }
            return 0;
        }

    } 

}

