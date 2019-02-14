package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.actions.WardenGainLoseGroundAction;
import io.benstein.sts.hunted.patches.AbstractCardEnum;
import io.benstein.sts.hunted.powers.RecklessWeaknessPower;

public class EvadeCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID("Evade");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int COST = 0;
    private static final int BASE_BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;
    private static final int BASE_WEAK = 1;
    private static final int WARDEN_LOSE_GROUND = -1;

    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public EvadeCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        
        baseBlock = block = BASE_BLOCK;
        baseMagicNumber = magicNumber = BASE_WEAK;
        baseWardenGainLoseAmount = wardenGainLoseAmount = WARDEN_LOSE_GROUND;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m) {
        // apply block
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));

        // gain ground
        AbstractDungeon.actionManager.addToBottom(new WardenGainLoseGroundAction(player, -1));

        // apply weak to yourself at start of next turn
        AbstractDungeon
            .actionManager
            .addToBottom(
                new ApplyPowerAction(
                    player,
                    player,
                    new RecklessWeaknessPower(player, this.magicNumber)
                )
            );
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
