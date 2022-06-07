package blackjack.model.player

import blackjack.model.CardDistributor
import blackjack.model.card.Card
import blackjack.model.card.Cards
import blackjack.model.card.State
import blackjack.model.card.State.BlackJack
import blackjack.model.card.State.Running

class Player(val name: String, private val hitDecisionMaker: HitDecisionMaker) :
    CardRecipient {

    private val cardList = mutableListOf<Card>()

    val state: State
        get() = State.of(this.cardList)

    val canHit: Boolean
        get() = (this.state is Running) && hitDecisionMaker.doYouWantToHit(this)

    val cards: Cards
        get() = Cards(this.cardList.toList())

    val cardCount: Int
        get() = this.cardList.size

    override fun addCard(card: Card) {
        this.cardList.add(card)
    }

    fun clearCard() {
        this.cardList.clear()
    }

    fun hitWhileWants(cardDistributor: CardDistributor, progress: ((Player) -> Unit)? = null) {
        while (this.canHit) {
            cardDistributor.giveCardsTo(this) // hit
            progress?.invoke(this)
        }
    }
}

class Players(playerList: List<Player>) : List<Player> by playerList {

    val blackJackPlayer: Player?
        get() = this.find { it.state is BlackJack }

    fun clearCard() {
        this.forEach { it.clearCard() }
    }

    companion object {
        fun List<Player>.toPlayers() = Players(this)
    }
}