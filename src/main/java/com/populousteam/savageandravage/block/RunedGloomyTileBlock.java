package com.populousteam.savageandravage.block;

import com.populousteam.savageandravage.init.SavageBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class RunedGloomyTileBlock extends Block {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public RunedGloomyTileBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TRIGGERED, Boolean.valueOf(false)));
    }

    private static void activate(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!state.get(TRIGGERED)) {
            if (entity.getEntity().getType().getTags() != EntityTypeTags.RAIDERS) {
                world.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(true)), 3);

                if (entity instanceof PlayerEntity) {
                    world.playSound((PlayerEntity) entity, pos, SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.HOSTILE, 1.0F, 1.0F);
                    world.playSound(null, pos, SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.HOSTILE, 1.0F, 1.0F);
                } else {
                    world.playSound(null, pos, SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.HOSTILE, 1.0F, 1.0F);
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 1));
                }
                EvokerFangsEntity evokerFangs = EntityType.EVOKER_FANGS.create(world);
                evokerFangs.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0F, 0.0F);
                world.addEntity(evokerFangs);

            }
        }

    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
        //TODO: Make the rune effect a separate entity to solve this problem - also make it do tick
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        activate(worldIn.getBlockState(pos), worldIn, pos, entityIn);
    }

    //TODO: make this work
    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (state.get(TRIGGERED)) {
            worldIn.getServer().enqueue(new TickDelayedTask(20, new Runnable() {
                @Override
                public void run() {
                    worldIn.setBlockState(pos, SavageBlocks.GLOOMY_TILES.getDefaultState());
                }
            }));
        }

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }
}
