package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.patches.AbstractCardEnum;
import io.benstein.sts.hunted.powers.RecklessWeaknessPower;

public class AnemicSlapCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID("AnemicSlap");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;

    private static final int COST = 0;
    private static final int BASE_DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 2;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Attack.png";

    public AnemicSlapCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = damage = BASE_DAMAGE;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // deal damage
        AbstractDungeon
            .actionManager
            .addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn)));
        
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

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            initializeDescription();
        }
    }
}
