package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.patches.AbstractCardEnum;

/* 
    Ransack
    https://trello.com/c/OcwgmV3g/7-ransack
    Common Skill | 1

    Draw 2 (3) cards.
    Discard 2 cards.
*/
public class RansackCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID("Ransack");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 1;
    private static final int BASE_DRAW = 2;
    private static final int UPGRADE_DRAW = 1;
    private static final int DISCARD = 2;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public RansackCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = 
        baseDamage = damage = BASE_DRAW;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m) {
        // draw cards
        AbstractDungeon
            .actionManager
            .addToBottom(new DrawCardAction(player, this.magicNumber));

        // discard a card
        AbstractDungeon
            .actionManager
            .addToBottom(new DiscardAction(player, player, DISCARD, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
            initializeDescription();
        }
    }
}
