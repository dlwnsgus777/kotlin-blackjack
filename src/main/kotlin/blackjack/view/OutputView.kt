package blackjack.view

import blackjack.constant.Messages
import blackjack.domain.OutputInterface
import blackjack.domain.card.Ace
import blackjack.domain.card.Card
import blackjack.domain.card.CardType
import blackjack.domain.card.Jack
import blackjack.domain.card.King
import blackjack.domain.card.Queen
import blackjack.domain.player.Dealer
import blackjack.domain.player.Player
import blackjack.domain.player.Users

/**
 * 출력을 담당하는 클래스
 * Created by Jaesungchi on 2022.06.07..
 */
object OutputView : OutputInterface {
    fun printHandOutMessage(users: Users) {
        println()
        println(Messages.HAND_OUT_CARD.format(users.names().joinToString()))
    }

    fun printUsersCard(users: Users, dealer: Dealer) {
        drawDealerOpenCard(dealer)
        users.users.forEach {
            drawUserCard(it)
        }
        println()
    }

    private fun drawDealerOpenCard(dealer: Dealer) {
        println(
            Messages.PRINT_HAVE_CARDS.format(dealer.name) + cardToString(dealer.cards.hands[0])
        )
    }

    override fun drawUserCard(user: Player) {
        println(
            Messages.PRINT_HAVE_CARDS.format(user.name) + user.cards.hands.joinToString {
                cardToString(it)
            }
        )
    }

    override fun drawMoreCard(user: Player) {
        println(Messages.WANT_MORE_CARD.format(user.name))
    }

    override fun drawDealerHitMessage() {
        println()
        println(Messages.PRINT_DEALER_HIT_MESSAGE)
    }

    fun printResult(users: Users, dealer: Dealer) {
        println()
        printCardAndScore(dealer)
        users.users.forEach {
            printCardAndScore(it)
        }
    }

    fun printWinAndLose(users: Users, dealer: Dealer) {
        println()
        println(Messages.FINAL_WIN_AND_LOSE)
        println(Messages.USER_COLON.format(dealer.name) + -users.getSumOfUsersIncome().value)
        users.users.forEach {
            println(Messages.USER_COLON.format(it.name) + it.income.value)
        }
    }

    private fun cardToString(card: Card): String {
        val number = when (card) {
            is Ace -> "A"
            is Jack -> "J"
            is Queen -> "Q"
            is King -> "K"
            else -> card.score
        }
        val type = when (card.type) {
            CardType.HEART -> "하트"
            CardType.DIAMOND -> "다이아"
            CardType.SPADE -> "스페이드"
            CardType.CLUB -> "클로버"
        }
        return "$number$type"
    }

    private fun printCardAndScore(user: Player) {
        println(
            Messages.PRINT_CARDS_AND_SCORE.format(
                user.name, user.cards.hands.joinToString { cardToString(it) }, user.cards.getScore().value
                )
            )
        }
    }
    