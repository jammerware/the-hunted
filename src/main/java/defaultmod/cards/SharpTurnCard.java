package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import defaultmod.TheHuntedMod;
import defaultmod.patches.AbstractCardEnum;

/* 
    https://trello.com/c/83b6o3uT/8-skid
    Sharp Turn
    Common Skill | 0

    (Draw a card.)
    Put a card from your hand on top of your draw pile.
    Put a card from your discard pile into your hand.

*/
public class SharpTurnCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID("SharpTurn");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 0;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public SharpTurnCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // draw if upgraded
        if (this.upgraded) {
            AbstractDungeon
                .actionManager
                .addToBottom(new DrawCardAction(player, 1));
        }

        // Put a card from your hand on top of your draw pile
        AbstractDungeon
            .actionManager
            .addToBottom(new PutOnDeckAction(player, player, 1, false));
        
        // Put a card from your discard pile into your hand
        if (!AbstractDungeon.player.discardPile.isEmpty()) {
            AbstractDungeon
                .actionManager
                .addToBottom(new DiscardPileToHandAction(1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
