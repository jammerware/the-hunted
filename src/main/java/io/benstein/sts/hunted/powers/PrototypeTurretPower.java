package io.benstein.sts.hunted.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import io.benstein.sts.hunted.TheHuntedMod;
import io.benstein.sts.hunted.services.LoggerService;

public class PrototypeTurretPower extends AbstractPower {
    public static final String POWER_ID = TheHuntedMod.makeID("PrototypeTurretPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG = "defaultModResources/images/powers/prototype-turret.png";    

    public PrototypeTurretPower(final AbstractCreature player, int damage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = damage;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();

        boolean isTargetingPlayer = AbstractDungeon.miscRng.random() < 0.1;
        AbstractCreature target = isTargetingPlayer ? this.owner : AbstractDungeon.getCurrRoom().monsters.getRandomMonster();

        LoggerService.getLogger(PrototypeTurretPower.class).debug("Target for turret is " + target.name);
        AbstractDungeon
            .actionManager
            .addToBottom(new DamageAction(target, new DamageInfo(this.owner, this.amount, DamageType.NORMAL), AttackEffect.SMASH));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}