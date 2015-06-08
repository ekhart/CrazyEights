package pl.ekhart.crazyeights;

import java.util.List;

/**
 * Created by Ekh on 2015-06-09.
 */
public class ComputerPlayer {

    public int makePlay(List<Card> hand, int suit, int rank) {
        int play = 0;
        for (int i = 0; i < hand.size(); ++i) {
            Card card = hand.get(i);
            int tmpId = card.getId(),
                    tmpRank = card.getRank(),
                    tmpSuit = card.getSuit();

            if (rank == 8) {
                if (suit == tmpSuit) {
                    play = tmpId;
                }
            } else if (suit == tmpSuit
                    || rank == tmpRank
                    || tmpId == 108 || tmpId == 208
                    || tmpId == 308 || tmpId == 408) {
                play = tmpId;
            }
        }
        return play;
    }

    public int chooseSuit(List<Card> hand) {
        return 100;
    }
}
