package io.benstein.sts.hunted.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import org.apache.logging.log4j.Logger;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.actions.WardenGainLoseGroundAction;
import io.benstein.sts.hunted.patches.AbstractCardEnum;
import io.benstein.sts.hunted.powers.HereGoesNothingPower;
import io.benstein.sts.hunted.services.LoggerService;

/*
    Here Goes Nothing
    https://trello.com/c/uLFPof5K/40-here-goes-nothing
    Rare Skill | 3

    Gain 5 ground.
    Play and exhaust the next 8 cards in your draw pile. If the Warden
    catches you this turn, you die.
*/
public class HereGoesNothingCard extends AbstractWardenGroundCard {
    public static final String ID = TheHuntedMod.makeID("HereGoesNothing");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;

    private static final int WARDEN_GROUND = -5;
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;
    private static final int CARDS_PLAYED = 8;

    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.HUNTED_ORANGE;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "defaultModResources/images/cards/Skill.png";

    public HereGoesNothingCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = CARDS_PLAYED;
        baseWardenGainLoseAmount = wardenGainLoseAmount = WARDEN_GROUND;
    }

    public void use(AbstractPlayer player, AbstractMonster monster) {
        // gain ground
        AbstractDungeon
            .actionManager
            .addToBottom(new WardenGainLoseGroundAction(player, this.wardenGainLoseAmount));

        // apply the fatal debuff
        AbstractDungeon
            .actionManager
            .addToBottom(new ApplyPowerAction(player, player, new HereGoesNothingPower(player)));
        
        // play and exhaust n cards from draw pile
        Logger logger = LoggerService.getLogger(HereGoesNothingCard.class);
        for (int i = 0; i < this.magicNumber; i++) {
            logger.debug("Playing card " + i);
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            logger.debug("Target is " + target.name);

            AbstractDungeon
                .actionManager
                .addToBottom(
                    new PlayTopCardAction(
                        target,
                        true
                    )
                );
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}