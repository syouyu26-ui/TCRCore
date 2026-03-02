package com.p1nero.tcrcore.datagen;

import com.p1nero.tcrcore.TCRCoreMod;
import com.yesman.epicskills.common.data.SkillTreeProvider;
import com.yesman.epicskills.skilltree.SkillTree;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.merlin204.efsiss.skill.EFSISSSkills;
import yesman.epicfight.skill.Skill;

import java.util.function.Consumer;

public class TCRSkillTreeProvider extends SkillTreeProvider {

    public static ResourceKey<SkillTree> MAGIC = ResourceKey.create(SkillTree.SKILL_TREE_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "magic"));

    public TCRSkillTreeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildSkillTreePages(Consumer<SkillTreePageBuilder> writer) {
        int centerX = 120;
        int centerY = 150;
        int radius = 100;
        int skillCount = 8;
        double angleStep = 2 * Math.PI / skillCount;

        Skill[] skills = {
                EFSISSSkills.BLOOD_INTO_MANA,
                EFSISSSkills.AUTO_HEAL,
                EFSISSSkills.RAPID_CHANT,
                EFSISSSkills.BREATHE_AGAIN,
                EFSISSSkills.AGAINST_MAGIC,
                EFSISSSkills.RESERVE_MANA,
                EFSISSSkills.HASTY_CASTING,
                EFSISSSkills.MANA_SHIELD
        };

        SkillTreePageBuilder pageBuilder = newPage(TCRCoreMod.MOD_ID, "magic")
                .menuBarColor(37, 27, 18)
                .priority(999)
                .hiddenWhenLocked(true)
                .setLocked(null);

        for (int i = 0; i < skills.length; i++) {
            double angle = i * angleStep;
            int x = (int) Math.round(centerX + radius * Math.cos(angle));
            int y = (int) Math.round(centerY + radius * Math.sin(angle));

            pageBuilder.newNode(skills[i])
                    .position(x, y)
                    .abilityPointsRequirement(5)
                    .done();
        }

        writer.accept(pageBuilder);
    }
}
