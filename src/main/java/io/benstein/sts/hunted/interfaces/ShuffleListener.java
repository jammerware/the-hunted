package io.benstein.sts.hunted.interfaces;

import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

public interface ShuffleListener {
    void onShuffle(CardGroupType groupType);
}