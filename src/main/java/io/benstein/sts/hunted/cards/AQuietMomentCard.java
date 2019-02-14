package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.actions.WardenGainLoseGroundAction;
import io.benstein.sts.hunted.patches.AbstractCardEnum;

/* 
    https://trello.com/c/uSNYbkQT/28-concentrate
    A Quiet Moment
    Uncommon Skill | 1

    Draw 4 cards.
    Lose 3 (1) ground.

*/
public class AQuietMomentCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID("AQuietMoment");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 1;
    private static final int BASE_DRAW = 4;
    private static final int BASE_WARDEN_GROUND = 3;
    private static final int UPGRADE_WARDEN_GROUND = -1;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public AQuietMomentCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = BASE_DRAW;
        baseWardenGainLoseAmount = wardenGainLoseAmount = BASE_WARDEN_GROUND;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // draw
        AbstractDungeon
            .actionManager
            .addToBottom(new DrawCardAction(player, magicNumber));
        
        // lose ground
        AbstractDungeon
            .actionManager
            .addToBottom(new WardenGainLoseGroundAction(player, wardenGainLoseAmount));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeWardenGainLoseAmount(UPGRADE_WARDEN_GROUND);
            initializeDescription();
        }
    }
}
