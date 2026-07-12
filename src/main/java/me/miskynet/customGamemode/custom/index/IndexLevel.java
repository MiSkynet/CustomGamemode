package me.miskynet.customGamemode.custom.index;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class IndexLevel {

    private final int level;
    private final int requiredLevel;
    private final ArrayList<Reward> rewards = new ArrayList<>();

    public IndexLevel(int level, int requiredLevel) {
        this.level = level;
        this.requiredLevel = requiredLevel;
    }

    public int getLevel() {
        return this.level;
    }

    public int getRequiredLevel() {
        return this.requiredLevel;
    }

    public void addReward(Reward reward) {
        this.rewards.add(reward);
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

}
