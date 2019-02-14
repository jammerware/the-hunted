package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import basemod.abstracts.CustomCard;
import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.patches.AbstractCardEnum;

/*
    https://trello.com/c/iYTYu13x/26-capitalize
    Capitalize
    Uncommon Attack | 2

    Deal 9 (13) damage. If the enemy has a debuff, gain 1 (2) energy and draw a card.

*/
public class CapitalizeCard extends CustomCard {
    public static final String ID = TheHuntedMod.makeID("Capitalize");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;

    private static final int COST = 2;
    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int BASE_ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY_GAIN = 1;
    
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Attack.png";

    public CapitalizeCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = damage = BASE_DAMAGE;
        baseMagicNumber = magicNumber = BASE_ENERGY_GAIN;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // deal damage
        AbstractDungeon
            .actionManager
            .addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
        
        
        // check for debuff
        boolean isDebuffed = false;
        for(AbstractPower power : monster.powers) {
            if (power.type == PowerType.DEBUFF) {
                isDebuffed = true;
                break;
            }
        }

        // gain energy and draw if isDebuffed
        if (isDebuffed) {
            // gain nrg
            AbstractDungeon
                .actionManager
                .addToBottom(new GainEnergyAction(this.magicNumber));

            // draw
            AbstractDungeon
                .actionManager
                .addToBottom(new DrawCardAction(player, 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_ENERGY_GAIN);
            initializeDescription();
        }
    }
}
