package com.p1nero.tcrcore.worldgen.structure;

import com.brass_amber.ba_bt.block.block.BTChestBlock;
import com.brass_amber.ba_bt.entity.block.BTMonolith;
import com.brass_amber.ba_bt.entity.hostile.golem.NetherGolem;
import com.brass_amber.ba_bt.init.BTEntityType;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.worldgen.TCRStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.content.mobs.entity.Forgotten;
import org.violetmoon.quark.content.mobs.module.ForgottenModule;

public class NetherGateOfDisaster extends Structure {
    public static final ResourceLocation GATE_OF_DISASTER_P1 = ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "gate_of_disaster_part1");
    public static final ResourceLocation GATE_OF_DISASTER_P2 = ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "gate_of_disaster_part2");
    public static final ResourceLocation GATE_OF_DISASTER_P3 = ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "gate_of_disaster_part3");
    public static final Codec<NetherGateOfDisaster> CODEC = simpleCodec(NetherGateOfDisaster::new);
    private static final Map<ResourceLocation, BlockPos> OFFSET = new HashMap<>();

    protected NetherGateOfDisaster(Structure.StructureSettings pSettings) {
        super(pSettings);
    }

    protected @NotNull Optional<Structure.GenerationStub> findGenerationPoint(@NotNull Structure.@NotNull GenerationContext context) {
        Rotation rotation = Rotation.getRandom(context.random());
        return onSealevelOfChunkCenter(context, (builder) -> this.generatePieces(builder, context, rotation));
    }

    protected static Optional<Structure.GenerationStub> onSealevelOfChunkCenter(Structure.GenerationContext context, Consumer<StructurePiecesBuilder> generationStub) {
        ChunkPos chunkpos = context.chunkPos();
        int i = chunkpos.getMiddleBlockX();
        int j = chunkpos.getMiddleBlockZ();
        int k = context.chunkGenerator().getSeaLevel();
        return Optional.of(new Structure.GenerationStub(new BlockPos(i, k, j), generationStub));
    }

    public @NotNull StructureType<?> type() {
        return TCRStructures.GATE_OF_DISASTER.get();
    }

    public static void start(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieceList, RandomSource random) {
        int x = pos.getX();
        int z = pos.getZ();
        BlockPos rotationOffSet = (new BlockPos(-48, 0, 0)).rotate(rotation);
        BlockPos blockpos = rotationOffSet.offset(x, pos.getY(), z);
        pieceList.addPiece(new Piece(templateManager, GATE_OF_DISASTER_P1, blockpos, rotation));
        rotationOffSet = (new BlockPos(0, 0, 0)).rotate(rotation);
        blockpos = rotationOffSet.offset(x, pos.getY(), z);
        pieceList.addPiece(new Piece(templateManager, GATE_OF_DISASTER_P2, blockpos, rotation));
        rotationOffSet = (new BlockPos(48, 0, 0)).rotate(rotation);
        blockpos = rotationOffSet.offset(x, pos.getY(), z);
        pieceList.addPiece(new Piece(templateManager, GATE_OF_DISASTER_P3, blockpos, rotation));
    }

    public void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context, Rotation rotation) {
        StructureTemplateManager templateManager = context.structureTemplateManager();
        int centerX = context.chunkPos().getMinBlockX();
        int centerZ = context.chunkPos().getMinBlockZ();
        int height = context.chunkGenerator().getSeaLevel();
        BlockPos spawnPos = new BlockPos(centerX, height, centerZ);
        start(templateManager, spawnPos, rotation, builder, context.random());
    }

    static {
        OFFSET.put(GATE_OF_DISASTER_P1, new BlockPos(0, 0, 0));
        OFFSET.put(GATE_OF_DISASTER_P2, new BlockPos(0, 0, 0));
        OFFSET.put(GATE_OF_DISASTER_P3, new BlockPos(0, 0, 0));
    }

    public static class Piece extends TemplateStructurePiece {

        public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotation) {
            super(TCRStructures.GATE_OF_DISASTER_PIECE.get(), 0, templateManagerIn, resourceLocationIn, resourceLocationIn.toString(), makeSettings(rotation), makePosition(resourceLocationIn, pos));
        }

        public Piece(StructureTemplateManager templateManagerIn, CompoundTag tagCompound) {
            super(TCRStructures.GATE_OF_DISASTER_PIECE.get(), tagCompound, templateManagerIn, (resourceLocation) -> makeSettings(Rotation.valueOf(tagCompound.getString("Rot"))));
        }

        public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(context.structureTemplateManager(), tag);
        }

        private static StructurePlaceSettings makeSettings(Rotation rotation) {
            return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).addProcessor(new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE));
        }

        private static BlockPos makePosition(ResourceLocation location, BlockPos pos) {
            return pos.offset(NetherGateOfDisaster.OFFSET.get(location));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag tagCompound) {
            super.addAdditionalSaveData(pContext, tagCompound);
            tagCompound.putString("Rot", this.placeSettings.getRotation().name());
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            super.postProcess(level, manager, generator, random, box, chunkPos, pos);
            BoundingBox pieceBox = this.getBoundingBox();
            int minX = pieceBox.minX();
            int maxX = pieceBox.maxX();
            int minZ = pieceBox.minZ();
            int maxZ = pieceBox.maxZ();
            int bottomY = pieceBox.minY();

            for(int x = minX; x <= maxX; ++x) {
                for(int z = minZ; z <= maxZ; ++z) {
                    BlockPos.MutableBlockPos supportPos = new BlockPos.MutableBlockPos(x, bottomY - 1, z);
                    if (box.isInside(supportPos)) {
                        while(this.canReplace(level, supportPos) && supportPos.getY() > level.getMinBuildHeight()) {
                            level.setBlock(supportPos, Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), 2);
                            supportPos.move(0, -1, 0);
                        }
                    }
                }
            }

        }

        public boolean canReplace(WorldGenLevel level, BlockPos blockPos) {
            BlockState blockState = level.getBlockState(blockPos);
            return blockState.getCollisionShape(level, blockPos).isEmpty();
        }

        /**
         * 改成生成下界傀儡
         */
        @Override
        protected void handleDataMarker(@NotNull String pName, @NotNull BlockPos pPos, @NotNull ServerLevelAccessor pLevel, @NotNull RandomSource pRandom, @NotNull BoundingBox pBox) {
            if (pName.equals("boss_spawn")) {
                BlockPos above2 = pPos.above(2);
                BTMonolith bossSpawner = BTEntityType.NETHER_MONOLITH.get().spawn(pLevel.getLevel(), above2, MobSpawnType.SPAWNER);
                if (bossSpawner != null) {
                    bossSpawner.moveTo((double)pPos.getX() + (double)0.5F, pPos.getY() + 2, (double)pPos.getZ() + (double)0.5F, 0.0F, 0.0F);
                    pLevel.addFreshEntity(bossSpawner);
                    bossSpawner.setKeyCountInEntity(1);
                    destroyNearby(bossSpawner, 5);
                }
                BlockPos pos1 = pPos.offset(pRandom.nextInt(5), 2, pRandom.nextInt(5));
                Forgotten forgotten = ForgottenModule.forgottenType.spawn(pLevel.getLevel(), pos1, MobSpawnType.SPAWNER);
                if(forgotten != null) {
                    forgotten.addTag("tcr_drop_nether_golem_key");
                    forgotten.setGlowingTag(true);
                    destroyNearby(forgotten, 5);
                }

                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 2);
            }

        }

        private void destroyNearby(Entity living, float scale) {
            if(living == null) {
                return;
            }
            Vec3 offset = living.getLookAngle().normalize().scale(scale);
            int ox = Mth.floor(living.getX() + offset.x);
            int oy = Mth.floor(living.getY() + (double)0.25F);
            int oz = Mth.floor(living.getZ() + offset.z);
            int width = Mth.ceil(living.getBbWidth() / 2.0F);
            int height = Mth.ceil(living.getBbHeight());
            boolean playEffectFlag = false;

            for(int ix = ox - width; ix <= ox + width; ++ix) {
                for(int iy = oy; iy <= oy + height; ++iy) {
                    for(int iz = oz - width; iz <= oz + width; ++iz) {
                        BlockPos pos = new BlockPos(ix, iy, iz);
                        BlockState state = living.level().getBlockState(pos);
                        boolean isChest = state.getBlock() instanceof BTChestBlock;
                        if (!isChest) {
                            playEffectFlag |= living.level().destroyBlock(pos, true, living);
                            if (state.getBlock() instanceof FireBlock) {
                                living.level().destroyBlock(pos, false, living);
                            }
                        }
                    }
                }
            }

            if (playEffectFlag) {
                living.level().gameEvent(living, GameEvent.BLOCK_DESTROY, living.blockPosition());
            }
        }
        
    }
}
