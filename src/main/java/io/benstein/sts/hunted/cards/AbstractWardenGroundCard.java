package io.benstein.sts.hunted.cards;

import basemod.abstracts.CustomCard;

public abstract class AbstractWardenGroundCard extends CustomCard {
    public int baseWardenGainLoseAmount;
    public int wardenGainLoseAmount;
    public boolean upgradedWardenGainLoseAmount;
    public boolean isWardenGainLoseAmountModified;

    public AbstractWardenGroundCard(final String id, final String name, final String img, final int cost,
            final String rawDescription, final CardType type, final CardColor color, final CardRarity rarity,
            final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void displayUpgrades() {
        if (upgradedWardenGainLoseAmount) {
            // Show how the number changes, as out of combat, the base number of a card is shown.
            baseWardenGainLoseAmount = wardenGainLoseAmount;
            // Modified = true, color it green to highlight that the number is being changed.
            isWardenGainLoseAmountModified = true;
        }

    }

    public void upgradeWardenGainLoseAmount(int amount) {
        // I'm not sure if you HAVE to set the base value to the upgraded value or not; the example does
        this.baseWardenGainLoseAmount = this.wardenGainLoseAmount + amount;
        this.wardenGainLoseAmount = this.baseWardenGainLoseAmount;
        this.upgradedWardenGainLoseAmount = true;
    }
}