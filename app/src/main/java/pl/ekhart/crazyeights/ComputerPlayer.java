package pl.ekhart.crazyeights;

import java.util.List;

/**
 * Created by Ekh on 2015-06-09.
 */
public class ComputerPlayer {

    public int makePlay(List<Card> hand, int suit, int rank) {
        int play = 0;
        for (Card card : hand) {
            int id = card.getId(),
                tmpRank = card.getRank(),
                tmpSuit = card.getSuit();

            if (tmpRank != 8) {
                if (rank == 8) {
                    if (suit == tmpSuit)
                        play = id;
                } else if (suit == tmpSuit || rank == tmpRank)
                    play = id;
            }
        }

        if (play == 0)
            play = getPlayIfEight(hand);

        return play;
    }

    private int getPlayIfEight(List<Card> hand) {
        int play = 0;
        for (Card card : hand) {
            int id = card.getId();
            if (isEight(id))
                play = id;
        }
        return play;
    }

    public int chooseSuit(List<Card> hand) {
        int suit = 100,
            diamonds = 0,
            clubs = 0,
            hearts = 0,
            spades = 0;

        for (Card card : hand) {
            if (card.getRank() == 8) {
                continue;
            }

            switch (card.getSuit()) {
                case 100: diamonds++; break;
                case 200: clubs++; break;
                case 300: hearts++; break;
                case 400: spades++; break;
            }
        }

        if (clubs > diamonds && clubs > hearts && clubs > spades) {
            suit = 200;
        } else if (hearts > diamonds && hearts > clubs && hearts > spades) {
            suit = 300;
        } else if (spades > diamonds && spades > clubs && spades > hearts) {
            suit = 400;
        }

        return suit;
    }

    private boolean isEight(int id) {
        return id == 108 || id == 208 || id == 308 || id == 408;
    }
}
