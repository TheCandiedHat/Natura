package mods.natura.blocks.overrides;

import mods.natura.client.LeverRender;
import net.minecraft.block.BlockLever;

public class NetherLever extends BlockLever
{
    public NetherLever(int par1)
    {
        super(par1);
    }
    
    public int getRenderType ()
    {
        return LeverRender.model;
    }
}
