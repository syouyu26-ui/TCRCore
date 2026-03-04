package com.p1nero.tcrcore.mixin.ba_bt;

import com.brass_amber.ba_bt.util.BTTags;
import com.brass_amber.ba_bt.worldGen.structures.CoreTower;
import com.brass_amber.ba_bt.worldGen.structures.TowerStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.datafixers.util.Pair;

import java.util.function.Predicate;

@Mixin(CoreTower.class)
public abstract class CoreTowerMixin extends Structure implements TowerStructure {

    protected CoreTowerMixin(StructureSettings p_226558_) {
        super(p_226558_);
    }

    @Inject(method = "getTowerType", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$getTowerType(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }

    /**
     * 企图简化搜索逻辑，但是好像加了locate就卡死了= =还是注释掉吧
     */
//    @Inject(method = "isSpawnableChunk", at = @At("HEAD"), cancellable = true, remap = false)
//    private void tcr$isSpawnableChunk(Structure.GenerationContext generationContext, CallbackInfoReturnable<Pair<Boolean, Integer>> cir) {
//        ChunkPos chunkPos = generationContext.chunkPos();
//        ChunkGenerator chunkGen = generationContext.chunkGenerator();
//
//
//        int middleHieght = chunkGen.getFirstOccupiedHeight(
//                chunkPos.getMiddleBlockX(), chunkPos.getMiddleBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, generationContext.heightAccessor(), generationContext.randomState()
//        );
//
//        BlockPos blockpos = chunkPos.getMiddleBlockPosition(middleHieght);
//
//        Holder<Biome> biome = generationContext.biomeSource().getNoiseBiome(
//                QuartPos.fromBlock(chunkPos.getMiddleBlockX()), QuartPos.fromBlock(middleHieght), QuartPos.fromBlock(chunkPos.getMiddleBlockZ()), generationContext.randomState().sampler()
//        );
//
//
//        if (generationContext.validBiome().test(biome)) {
//            HolderSet<Biome> holderset = generationContext.registryAccess().registryOrThrow(Registries.BIOME).getTag(BiomeTags.IS_OCEAN).orElseThrow();
//            Predicate<Holder<Biome>> predicate = holderset::contains;
//            Pair<BlockPos, Holder<Biome>> oceanBiomeNearby = generationContext.chunkGenerator().getBiomeSource().findBiomeHorizontal(
//                    blockpos.getX(), generationContext.chunkGenerator().getSeaLevel(), blockpos.getZ(), 77,
//                    predicate, generationContext.random(), generationContext.randomState().sampler()
//            );
//
//            if(oceanBiomeNearby != null) {
//                cir.setReturnValue(Pair.of(false, 0));
//            }
//
//            holderset = generationContext.registryAccess().registryOrThrow(Registries.BIOME).getTag(BiomeTags.HAS_ANCIENT_CITY).orElseThrow();
//            predicate = holderset::contains;
//            Pair<BlockPos, Holder<Biome>> sculkBiomeNearby = generationContext.chunkGenerator().getBiomeSource().findBiomeHorizontal(
//                    blockpos.getX(), -60, blockpos.getZ(), 48,
//                    predicate, generationContext.random(), generationContext.randomState().sampler()
//            );
//
//            if(sculkBiomeNearby != null) {
//                cir.setReturnValue(Pair.of(false, 0));
//            }
//
//            checkVariant(generationContext, blockpos);
//            cir.setReturnValue(Pair.of(true, middleHieght));
//        }
//
//        cir.setReturnValue(Pair.of(false, 0));
//    }

}
