package io.benstein.sts.hunted.services;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

import io.benstein.sts.hunted.interfaces.ShuffleListener;

public final class ShuffleService {
    private List<ShuffleListener> _shuffleListeners = new ArrayList<ShuffleListener>();
    private static final ShuffleService INSTANCE = new ShuffleService();

    // YOU MAY NOT
    private ShuffleService() { }
    
    public static ShuffleService getInstance() {
        return INSTANCE;
    }

    public void notifyShuffle(CardGroupType groupType) {
        for (ShuffleListener listener : _shuffleListeners) {
            listener.onShuffle(groupType);
        }
    }

    public void registerShuffleListener(ShuffleListener listener) {
        this._shuffleListeners.add(listener);
    }

    public void unregisterShuffleListener(ShuffleListener listener) {
        this._shuffleListeners.remove(listener);
    }

    @Override
    protected void finalize() {
        this._shuffleListeners.clear();
    }
}