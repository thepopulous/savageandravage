package illager.guardillagers.event;

import com.google.common.collect.Lists;
import illager.guardillagers.GuardIllagers;
import illager.guardillagers.entity.GuardIllagerEntity;
import illager.guardillagers.init.IllagerEntityRegistry;
import it.unimi.dsi.fastutil.longs.LongIterator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IStructureReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.Structures;
import net.minecraft.world.gen.feature.structure.WoodlandMansionPieces;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = GuardIllagers.MODID)
public class GuardSpawnHandler {
	private static final Logger LOGGER = LogManager.getLogger(GuardSpawnHandler.class);

	/*private static final ImmutableMultimap<String, SpawnEntry> SPAWN_ENTRIES = ImmutableMultimap.of(
		"entrance", new SpawnEntry(GuardIllagerEntity.class, 2, 3),
		"1x2_c_stairs", new SpawnEntry(GuardIllagerEntity::new, 2, 2)
			.withFloorPredicate(state -> state.getBlock() instanceof BlockStairs),
		"1x2_d_stairs", new SpawnEntry(GuardIllagerEntity::new, 2, 2)
			.withFloorPredicate(state -> state.getBlock() instanceof BlockStairs)
	);*/

	private static final float SPAWN_SEARCH_ATTEMPT_FACTOR = 0.1F;

	private static final Field TEMPLATE_NAME_FIELD = ObfuscationReflectionHelper.findField(WoodlandMansionPieces.MansionTemplate.class, "field_191082_d");

	@Nullable
	private static Structure getMansionGenerator(ServerWorld world) {
		ServerChunkProvider chunkProvider = world.getChunkProvider();
		if (chunkProvider.generator instanceof OverworldChunkGenerator) {

			return (Structure) Structures.MANSION;

		}
		return null;
	}

	@Nullable
	private static String getTemplateName(StructurePiece component) {
		if (component instanceof WoodlandMansionPieces.MansionTemplate) {
			try {
				return (String) TEMPLATE_NAME_FIELD.get(component);
			} catch (ReflectiveOperationException e) {
				LOGGER.error("Failed to get template name for {}", component, e);
			}
		}
		return null;
	}

	public static List<StructureStart> getStarts(IWorld worldIn, int x, int z) {
		List<StructureStart> list = Lists.newArrayList();
		IChunk ichunk = worldIn.getChunk(x, z, ChunkStatus.STRUCTURE_REFERENCES);
		LongIterator longiterator = ichunk.getStructureReferences(Structures.MANSION.getStructureName()).iterator();

		while (longiterator.hasNext()) {
			long i = longiterator.nextLong();
			IStructureReader istructurereader = worldIn.getChunk(ChunkPos.getX(i), ChunkPos.getZ(i), ChunkStatus.STRUCTURE_STARTS);
			StructureStart structurestart = istructurereader.getStructureStart(Structures.MANSION.getStructureName());
			if (structurestart != null) {
				list.add(structurestart);
				return list;
			}
		}

		return list;
	}

	@SubscribeEvent
	public static void onPostPopulate(ChunkEvent.Load event) {
		if (!(event.getWorld() instanceof ServerWorld)) {
			return;
		}

		ServerWorld world = (ServerWorld) event.getWorld();

		Structure mansionGenerator = getMansionGenerator(world);
		if (mansionGenerator == null) {
			return;
		}

		List<StructureStart> structureMap = getStarts(world, event.getChunk().getPos().x, event.getChunk().getPos().z);
		if (structureMap == null) {
			return;
		}

		//TODO
		ChunkPos chunkPos = new ChunkPos(event.getChunk().getPos().x, event.getChunk().getPos().z);
		MutableBoundingBox chunkBounds = new MutableBoundingBox(chunkPos.getXStart(), chunkPos.getZStart(), chunkPos.getXEnd(), chunkPos.getZEnd());

		structureMap.forEach(structure -> {
			Stream<StructurePiece> intersectingComponents = structure.getComponents().stream()
				.filter(component -> component.getBoundingBox().intersectsWith(chunkBounds))
				.filter(component -> isComponentFullyGenerated(world, component));

			MutableBoundingBox structureBounds = structure.getBoundingBox();
			intersectingComponents.forEach(component -> populateComponent(world, structureBounds, component));
		});
	}

	private static boolean isComponentFullyGenerated(World world, StructurePiece component) {
		MutableBoundingBox boundingBox = component.getBoundingBox();
		ChunkPos minChunkPos = chunkFromBlock(boundingBox.minX, boundingBox.minZ);
		ChunkPos maxChunkPos = chunkFromBlock(boundingBox.maxX, boundingBox.maxZ);
		for (int chunkZ = minChunkPos.z; chunkZ <= maxChunkPos.z; chunkZ++) {
			for (int chunkX = minChunkPos.x; chunkX <= maxChunkPos.x; chunkX++) {
				if (isChunkPopulated(world, chunkX, chunkZ)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isChunkPopulated(World world, int chunkX, int chunkZ) {
		BlockPos pos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
		if (!world.isBlockLoaded(pos)) {
			return false;
		}

		Chunk chunk = world.getChunk(chunkX, chunkZ);
		//TODO
		return world.getChunkProvider().chunkExists(chunkX, chunkZ);
	}

	private static void populateComponent(World world, MutableBoundingBox structureBounds, StructurePiece component) {
		String templateName = getTemplateName(component);
		if (templateName == null) {
			return;
		}


		long seed = world.getSeed() ^ hash(structureBounds);
		Random random = new Random(seed);

		if (templateName.equals("entrance") || templateName.equals("1x2_c_stairs") || templateName.equals("1x2_d_stairs")) {

			spawnGroup(world, random, structureBounds, component);
		}

	}

	private static void spawnGroup(World world, Random random, MutableBoundingBox structureBounds, StructurePiece component) {
		BlockPos structureCenter = new BlockPos(
			(structureBounds.maxX + structureBounds.minX) / 2,
			(structureBounds.maxY + structureBounds.minY) / 2,
			(structureBounds.maxZ + structureBounds.minZ) / 2
		);
		int structureRadius = Math.max(
			Math.max(structureBounds.getXSize(), structureBounds.getYSize()),
			structureBounds.getZSize()
		) / 2;

		int groupSize = random.nextInt(3) + 1;

		int floorArea = component.getBoundingBox().getXSize() * component.getBoundingBox().getZSize();

		int attempts = MathHelper.ceil(floorArea * SPAWN_SEARCH_ATTEMPT_FACTOR);

		BlockPos pos = randomPositionIn(component.getBoundingBox(), random);

		List<Entity> list = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos.add(-5, 0, -5), pos.add(5, 5, 5)), EntityPredicates.IS_ALIVE);


		if (list.isEmpty()) {
			for (int i = 0; i < groupSize; i++) {
				GuardIllagerEntity entity = IllagerEntityRegistry.GUARD_ILLAGER.create(world);
				BlockPos spawnLocation = tryFindSpawnLocationIn(world, random, entity, component.getBoundingBox());
				if (spawnLocation == null) {
					return;
				}

				float yaw = random.nextFloat() * 360.0F;
				entity.setPositionAndRotation(spawnLocation.getX() + 0.5, spawnLocation.getY(), spawnLocation.getZ() + 0.5, yaw, 0.0F);
				world.addEntity(entity);

				if (entity instanceof CreatureEntity) {
					((CreatureEntity) entity).setHomePosAndDistance(structureCenter, structureRadius);
					((CreatureEntity) entity).onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(entity)), null, null, null);
					((CreatureEntity) entity).enablePersistence();
				}
			}

		}
	}

	@Nullable
	private static BlockPos tryFindSpawnLocationIn(World world, Random random, Entity entity, MutableBoundingBox bounds) {
		int floorArea = bounds.getXSize() * bounds.getZSize();

		int attempts = MathHelper.ceil(floorArea * SPAWN_SEARCH_ATTEMPT_FACTOR);

		for (int i = 0; i < attempts; i++) {

			BlockPos pos = randomPositionIn(bounds, random);

			if (world.isAirBlock(pos)) {
				BlockPos floor = findFloor(world, pos, bounds.minY);

				if (floor == null) {

					continue;

				}
				BlockState state = world.getBlockState(floor);

				if (entity instanceof CreatureEntity) {
					if (((CreatureEntity) entity).canSpawn(world, SpawnReason.STRUCTURE)) {
						BlockPos spawnPos = floor.up();

						AxisAlignedBB boundsInPlace = getEntityBoundsAt(entity, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
						if (!world.checkBlockCollision(boundsInPlace)) {

							continue;

						}
						return spawnPos;
					}
				}
			}
		}
		return null;
	}

	private static AxisAlignedBB getEntityBoundsAt(Entity entity, double x, double y, double z) {
		float width = entity.getWidth() / 2.0F;
		float height = entity.getHeight();

		return new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
	}

	@Nullable
	private static BlockPos findFloor(World world, BlockPos pos, int minY) {
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
		while (mutablePos.getY() > minY) {
			mutablePos.move(Direction.DOWN);
			if (world.getBlockState(mutablePos).getCollisionShape(world, mutablePos) != null) {
				return mutablePos.toImmutable();
			}
		}

		return null;
	}

	private static BlockPos randomPositionIn(MutableBoundingBox bounds, Random random) {
		return new BlockPos(
			bounds.minX + random.nextInt(bounds.getXSize()),
			bounds.minY + random.nextInt(bounds.getYSize()),
			bounds.minZ + random.nextInt(bounds.getZSize())
		);
	}

	private static ChunkPos chunkFromBlock(int x, int z) {
		return new ChunkPos(x >> 4, z >> 4);
	}

	private static long hash(MutableBoundingBox bounds) {
		long min = (bounds.minY + bounds.minZ * 31) * 31 + bounds.minX;
		long max = (bounds.maxY + bounds.maxZ * 31) * 31 + bounds.maxX;
		return min * 31 + max;
	}
}