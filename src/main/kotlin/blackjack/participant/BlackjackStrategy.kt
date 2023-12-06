package blackjack.participant

import blackjack.ScoreCalculator
import blackjack.card.BlackJackCard
import blackjack.participant.status.Blackjack
import blackjack.participant.status.Hit
import blackjack.participant.status.Stand
import blackjack.participant.status.Status

class BlackjackStrategy(
    private val scoreCalculator: ScoreCalculator,
) {
    var cards: List<BlackJackCard> = emptyList()
    var status: Status = Hit()

    val isBust get() = resultScore() > BLACKJACK

    fun drawCard(cards: List<BlackJackCard>) {
        this.cards += cards
        if (cards.size == NORMAL_DRAW && !isBust2()) {
            status = Stand()
        }

        if (isFirstTurn(cards) && isBlackjack()) {
            status = Blackjack()
        }
    }

    private fun isFirstTurn(cards: List<BlackJackCard>): Boolean = cards.size == FIRST_TURN_DRAW

    fun resultScore(): Int {
        return scoreCalculator.calculateGameScore(cards)
    }

    private fun isBlackjack(): Boolean {
        return resultScore() == BLACKJACK
    }

    private fun isBust2(): Boolean {
        return resultScore() > BLACKJACK
    }

    companion object {
        private const val BLACKJACK: Int = 21
        private const val FIRST_TURN_DRAW: Int = 2
        private const val NORMAL_DRAW: Int = 1
    }
}