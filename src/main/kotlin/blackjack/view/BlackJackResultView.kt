package blackjack.view

import blackjack.domain.card.Card
import blackjack.domain.card.CardDenomination
import blackjack.domain.card.CardShape
import blackjack.domain.card.Cards
import blackjack.domain.game.CardDistributionResult
import blackjack.domain.game.DealerTurnExecuteResult
import blackjack.domain.game.MatchResult
import blackjack.domain.gamer.DealerCard
import blackjack.domain.gamer.PlayerCards
import blackjack.domain.score.Score
import kotlin.math.roundToInt

class BlackJackResultView {

    fun display(result: CardDistributionResult) {
        val message = buildString {
            appendLine()
            appendLine(result.makeTitleMessage())
            appendLine(result.makeDealerCardsMessage())
            appendLine(result.makeAllPlayerCardsMessage())
        }
        println(message)
    }

    fun display(playerCards: PlayerCards) {
        println(playerCards.makeDisplayMessage())
    }

    fun display(dealerTurnExecuteResult: DealerTurnExecuteResult) {
        val message = buildString {
            appendLine()
            appendLine(dealerTurnExecuteResult.makeDealerTurnResultMessage())
        }
        println(message)
    }

    fun display(matchResult: MatchResult) {
        val message = buildString {
            appendLine(matchResult.makeDealerCardsDisplayMessage())
            appendLine(matchResult.makeAllPlayerCardsDisplayMessage())
            appendLine()
            appendLine("## 최종 수익")
            appendLine(matchResult.makeDealerMatchResultMessage())
            appendLine(matchResult.makeAllPlayersMatchResultMessage())
        }
        println(message)
    }

    private fun CardDistributionResult.makeTitleMessage(): String {
        val names = playerNames.joinToString()
        return "딜러와 ${names}에게 ${distributionCardSize}장씩 나누었습니다."
    }

    private fun CardDistributionResult.makeDealerCardsMessage(): String {
        val openCards = dealerCards.filterIsInstance<DealerCard.Open>()
        val cardsMessage = openCards.joinToString { openCard -> openCard.card.makeDisplayMessage() }
        return "딜러: $cardsMessage"
    }

    private fun CardDistributionResult.makeAllPlayerCardsMessage(): String {
        return playerCards.joinToString("\n") { playerCard -> playerCard.makeDisplayMessage() }
    }

    private fun DealerTurnExecuteResult.makeDealerTurnResultMessage(): String {
        return if (isDistributedOneMoreCard) {
            "딜러는 16이하라 한장의 카드를 더 받았습니다."
        } else {
            "딜러는 17이상이라 카드를 더 받지 않았습니다."
        }
    }

    private fun MatchResult.makeDealerCardsDisplayMessage(): String {
        return gamerCards.run {
            "딜러 카드: ${dealerCards.makeDisplayMessage()} - ${dealerCards.score.makeDisplayMessage()}"
        }
    }

    private fun MatchResult.makeAllPlayerCardsDisplayMessage(): String {
        return gamerCards.allPlayerCards.joinToString("\n") {
            val name = it.playerName
            val cards = it.cards
            val score = it.cards.score
            "$name 카드: ${cards.makeDisplayMessage()} - ${score.makeDisplayMessage()}"
        }
    }

    private fun MatchResult.makeDealerMatchResultMessage(): String {
        return "딜러: ${gamerProfitResults.dealerProfitResult.profit.roundToInt()}"
    }

    private fun MatchResult.makeAllPlayersMatchResultMessage(): String {
        return gamerProfitResults.playerProfitResults.joinToString("\n") {
            "${it.playerName}: ${it.profit.roundToInt()}"
        }
    }

    private fun PlayerCards.makeDisplayMessage(): String {
        val cardNames = cards.makeDisplayMessage()
        return "${playerName}카드: $cardNames"
    }

    private fun Score.makeDisplayMessage(): String {
        return "결과: $value"
    }

    private fun Cards.makeDisplayMessage(): String {
        return value.joinToString { card -> card.makeDisplayMessage() }
    }

    private fun Card.makeDisplayMessage(): String {
        val shapeName = when (shape) {
            CardShape.SPADE -> "스페이드"
            CardShape.HEART -> "하트"
            CardShape.DIAMOND -> "다이아몬드"
            CardShape.CLOVER -> "클로버"
        }
        val denominationName = when (denomination) {
            CardDenomination.ACE -> "A"
            CardDenomination.TWO -> "2"
            CardDenomination.THREE -> "3"
            CardDenomination.FOUR -> "4"
            CardDenomination.FIVE -> "5"
            CardDenomination.SIX -> "6"
            CardDenomination.SEVEN -> "7"
            CardDenomination.EIGHT -> "8"
            CardDenomination.NINE -> "9"
            CardDenomination.TEN -> "10"
            CardDenomination.JACK -> "J"
            CardDenomination.QUEEN -> "Q"
            CardDenomination.KING -> "K"
        }
        return "$denominationName$shapeName"
    }
}