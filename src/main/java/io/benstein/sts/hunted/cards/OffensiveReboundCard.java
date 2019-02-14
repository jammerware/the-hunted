package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.actions.WardenGainLoseGroundAction;
import io.benstein.sts.hunted.patches.AbstractCardEnum;
import io.benstein.sts.hunted.services.LoggerService;

public class OffensiveReboundCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID(OffensiveReboundCard.class.getSimpleName());

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;

    private static final int COST = 1;
    private static final int BASE_DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int BASE_WEAK = 1;
    private static final int BASE_GROUND_LOSE = 2;

    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Attack.png";

    private boolean isRebounding = false;

    public OffensiveReboundCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = damage = BASE_DAMAGE;
        baseMagicNumber = magicNumber = BASE_WEAK;
        baseWardenGainLoseAmount = wardenGainLoseAmount = BASE_GROUND_LOSE;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // check block state of the enemy, set the card to rebound if unblocked
        // PROBABLY going to later implement a bypass block debuff, so we might have to check that here
        if (monster.currentBlock > 0) {
            this.isRebounding = true;
        }
        
        // deal damage
        AbstractDungeon
            .actionManager
            .addToBottom(
                new DamageAction(monster,
                new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL)
            );

        // apply weak
        AbstractDungeon
            .actionManager
            .addToBottom(
                new ApplyPowerAction(
                    monster, 
                    player, 
                    new WeakPower(monster, this.magicNumber, false), 
                    this.magicNumber
                )
            );

        // lose ground
        AbstractDungeon.actionManager.addToBottom(new WardenGainLoseGroundAction(player, this.wardenGainLoseAmount));

        // rebound if the attack wasn't blocked
        this.isRebounding = true;
    }

    @Override
    public void onMoveToDiscard() {
        Logger logger = LoggerService.getLogger(OffensiveReboundCard.class);
        logger.info("moving to discard");
        super.onMoveToDiscard();
        logger.info("supered");

        if (this.isRebounding) {
            logger.info("REBOUND");
            CardGroup group = new CardGroup(CardGroupType.DISCARD_PILE);
            logger.info("got the discard pile");
            logger.info("get the top card");
            logger.info(group.size());
            AbstractDungeon.player.hand.addToHand(this);
            logger.info("add to hand");
        }
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
