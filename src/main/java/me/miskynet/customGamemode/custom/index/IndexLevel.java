package me.miskynet.customGamemode.custom.index;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class IndexLevel {

    private final int level;
    private final ArrayList<Reward> rewards = new ArrayList<>();

    public IndexLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void addReward(Reward reward) {
        this.rewards.add(reward);
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

}
