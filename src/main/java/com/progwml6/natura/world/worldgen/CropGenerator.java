package com.progwml6.natura.world.worldgen;

import java.util.Random;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.crops.BlockNaturaBarley;
import com.progwml6.natura.overworld.block.crops.BlockNaturaCotton;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class CropGenerator implements IWorldGenerator
{
    public static CropGenerator INSTANCE = new CropGenerator();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
        world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world)
    {
        int xSpawn, ySpawn, zSpawn;

        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        BlockPos position;

        Biome biome = world.getChunkFromBlockCoords(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        // Barley
        if (Config.generateBarley && random.nextInt(5) == 0 && this.goodClimate(biome, 0.11f, 1.0f, 0.11f, 2f))
        {
            xSpawn = chunkX * 16 + random.nextInt(16);
            ySpawn = random.nextInt(128) + Config.seaLevel;
            zSpawn = chunkZ * 16 + random.nextInt(16);

            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.generateBarley(world, random, position);
            this.generateBarley(world, random, position);
        }

        // Cotton
        if (Config.generateCotton && random.nextInt(12) == 0 && this.goodClimate(biome, 0.11f, 1.0f, 0.11f, 2f))
        {
            xSpawn = chunkX * 16 + random.nextInt(16);
            ySpawn = random.nextInt(128) + Config.seaLevel;
            zSpawn = chunkZ * 16 + random.nextInt(16);

            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.generateCotton(world, random, position);
            this.generateCotton(world, random, position);
        }

        // Bluebells
        if (Config.generateBluebells && random.nextInt(12) == 0)
        {
            xSpawn = chunkX * 16 + random.nextInt(16);
            ySpawn = random.nextInt(128) + Config.seaLevel;
            zSpawn = chunkZ * 16 + random.nextInt(16);

            position = new BlockPos(xSpawn, ySpawn, zSpawn);

            this.generateBluebells(world, random, position);
        }
    }

    public boolean generateBarley(World world, Random random, BlockPos position)
    {
        IBlockState state = NaturaOverworld.barleyCrop.getDefaultState().withProperty(BlockNaturaBarley.AGE, 3);

        for (int tries = 0; tries < 64; tries++)
        {
            BlockPos blockpos = position.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

            if (world.isAirBlock(blockpos) && Blocks.YELLOW_FLOWER.canBlockStay(world, blockpos, state))
            {
                world.setBlockState(blockpos, state, 2);
            }
        }

        return true;
    }

    public boolean generateCotton(World world, Random random, BlockPos position)
    {
        IBlockState state = NaturaOverworld.cottonCrop.getDefaultState().withProperty(BlockNaturaCotton.AGE, 4);

        for (int tries = 0; tries < 64; tries++)
        {
            BlockPos blockpos = position.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

            if (world.isAirBlock(blockpos) && Blocks.YELLOW_FLOWER.canBlockStay(world, blockpos, state))
            {
                world.setBlockState(blockpos, state, 2);
            }
        }

        return true;
    }

    public boolean generateBluebells(World world, Random random, BlockPos position)
    {
        IBlockState state = NaturaOverworld.bluebellsFlower.getDefaultState();

        for (int tries = 0; tries < 40; tries++)
        {
            BlockPos blockpos = position.add(random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8));

            if (world.isAirBlock(blockpos) && Blocks.YELLOW_FLOWER.canBlockStay(world, blockpos, state))
            {
                world.setBlockState(blockpos, state, 2);
            }
        }

        return true;
    }

    public boolean goodClimate(Biome biome, float minTemp, float maxTemp, float minRain, float maxRain)
    {
        float temp = biome.getTemperature();
        float rain = biome.getRainfall();

        if (minTemp <= temp && temp <= maxTemp && minRain <= rain && rain <= maxRain)
        {
            return true;
        }

        return false;
    }

}
