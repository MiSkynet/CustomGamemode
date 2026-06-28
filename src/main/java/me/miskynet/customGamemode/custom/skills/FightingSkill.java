package me.miskynet.customGamemode.custom.skills;

import me.miskynet.customGamemode.utils.Utils;

import java.util.List;

public class FightingSkill extends Skill {

    public FightingSkill() {
        super(SkillType.FIGHTING, 30, 150, Utils.component(false, "&dFighting"), List.of(
                Utils.component(false, "The Fighting skill can be leveled with your XP bar level"),
                Utils.component(false, "or by killing hostile mobs"),
                Utils.component(false, "Each level increases your damage on mobs and"),
                Utils.component(false, "increases your resistance. Additionally, you'll"),
                Utils.component(false, "receive money everytime you level up and some xp")
        ));
    }
}
