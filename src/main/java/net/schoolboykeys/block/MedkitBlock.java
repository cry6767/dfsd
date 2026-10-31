package net.schoolboykeys.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.schoolboykeys.init.ModItems;
import net.schoolboykeys.init.ModSounds;
import net.schoolboykeys.init.ModTags;

/**
 * Аптечка. Ставится запертой. Ключ от аптечки (ПКМ) открывает и закрывает её.
 * В открытую аптечку можно засыпать таблетки (ПКМ таблетками): звук и сообщение, как у кружки в оригинальном моде.
 */
public class MedkitBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    private static final VoxelShape SHAPE_NS = Block.box(2.0D, 0.0D, 4.0D, 14.0D, 8.0D, 12.0D);
    private static final VoxelShape SHAPE_EW = Block.box(4.0D, 0.0D, 2.0D, 12.0D, 8.0D, 14.0D);

    public MedkitBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(1.0F)
                .sound(SoundType.METAL)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(OPEN, false);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction.Axis axis = state.getValue(FACING).getAxis();
        return axis == Direction.Axis.X ? SHAPE_EW : SHAPE_NS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        ItemStack held = player.getItemInHand(hand);
        boolean open = state.getValue(OPEN);

        // Ключ от аптечки: открыть / закрыть
        if (held.is(ModItems.MEDKIT_KEY.get())) {
            level.setBlock(pos, state.setValue(OPEN, !open), 3);
            level.playSound(null, pos, ModSounds.DOOR_UNLOCK.get(), SoundSource.BLOCKS, 1.0F, open ? 0.6F : 1.0F);
            player.displayClientMessage(Component.translatable(
                    open ? "message.schoolboykeys.medkit_closed" : "message.schoolboykeys.medkit_opened"), true);
            return InteractionResult.CONSUME;
        }

        // Заперта
        if (!open) {
            level.playSound(null, pos, ModSounds.DOOR_LOCKED.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable("message.schoolboykeys.medkit_locked"), true);
            return InteractionResult.CONSUME;
        }

        // Открыта: засыпать таблетки (как с кружкой — таблетка не тратится)
        if (held.is(ModTags.PILLS)) {
            level.playSound(null, pos, ModSounds.PILLS_POUR.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable(
                    held.is(ModItems.SLEEPING_PILLS.get())
                            ? "message.schoolboykeys.pills_sleeping"
                            : "message.schoolboykeys.pills_laxative"), true);
            return InteractionResult.CONSUME;
        }

        player.displayClientMessage(Component.translatable("message.schoolboykeys.medkit_idle"), true);
        return InteractionResult.CONSUME;
    }
}
