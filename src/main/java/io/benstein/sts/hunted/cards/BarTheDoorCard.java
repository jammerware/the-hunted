package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.actions.WardenGainLoseGroundAction;
import io.benstein.sts.hunted.patches.AbstractCardEnum;
import io.benstein.sts.hunted.powers.BarTheDoorPower;

/* 
    https://trello.com/c/OVILqpVb/9-bar-the-door
    Bar the Door
    Rare Power | 0

    At the end of combat, if the Warden is very far behind you, heal 10 (15).
    Lose 2 ground.

*/
public class BarTheDoorCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID(BarTheDoorCard.class.getSimpleName());

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;

    private static final int COST = 0;
    private static final int BASE_WARDEN_GROUND = 2;
    private static final int BASE_HEAL = 10;
    private static final int UPGRADE_HEAL = 5;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Power.png";

    public BarTheDoorCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = BASE_HEAL;
        baseWardenGainLoseAmount = wardenGainLoseAmount = BASE_WARDEN_GROUND;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // apply the business
        AbstractDungeon
            .actionManager
            .addToBottom(new ApplyPowerAction(player, player, new BarTheDoorPower(player, this.magicNumber)));

        // lose ground
        AbstractDungeon
            .actionManager
            .addToBottom(new WardenGainLoseGroundAction(player, this.wardenGainLoseAmount));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_HEAL);
            initializeDescription();
        }
    }
}
