package blackjack.domain.gamer

import blackjack.domain.card.CardFixture
import blackjack.domain.card.cards
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DealerTest : StringSpec({

    "카드 목록 초기화를 하지 않았다면 hit가 불가능하다" {
        val dealer = Dealer()
        dealer.canHit() shouldBe false
    }

    "카드 목록을 초기화하면 hit가 가능하다" {
        val dealer = Dealer()
        dealer.init(cards(CardFixture.heartFour, CardFixture.heartTwo))
        dealer.canHit() shouldBe true
    }

    "초기화 한 카드 목록의 점수가 16을 초과하면 hit가 불가능하다" {
        val dealer = Dealer()
        dealer.init(cards(CardFixture.heartJack, CardFixture.heartQueen)) // j(10) + q(10) = 20
        dealer.canHit() shouldBe false
    }
})