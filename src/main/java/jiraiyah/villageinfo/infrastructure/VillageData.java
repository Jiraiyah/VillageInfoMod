package jiraiyah.villageinfo.infrastructure;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class VillageData
{
	public int radius;
	public BlockPos center;
	public List<BlockPos> doorPositions = new ArrayList<>();
	public int reputation;
	public int villagerCount;


	public VillageData(int radiusList, BlockPos centerList, List<BlockPos> doorPositions, int villagerCount, int reputation)
	{
		this.radius = radiusList;
		this.center = centerList;
		this.doorPositions = doorPositions;
		this.villagerCount = villagerCount;
		this.reputation = reputation;
	}
}
