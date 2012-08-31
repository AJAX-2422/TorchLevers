package net.minecraft.src;

import java.util.Random;

public class TLBlock extends Block {
    protected TLBlock(int par1, int par2)
    {
        super(par1, par2, Material.circuits);
        this.setTickRandomly(true);
    }
    @Override
    public int idDropped(int x, Random random, int y)
    {
    	return mod_TorchLevers.TLitem.shiftedIndex;
    }
//psuedocode start
//    public void update(int x, int y, int z, World world)
//    {
//    	metadata = world.getMetadata(x,y,z)
//    	if (metadata includes 1)
//    	{
//    		renderAsAttachedToTop
//    	}
//    	elseif (metadata includes 2)
//    	{
//    		renderAsAttachedToNorth
//    	}
//    	elseif (metadata includes 3)
//    	{
//    		renderAsAttachedToEast
//    	}
//    	elseif (metadata includes 4)
//    	{
//    		renderAsAttachedToSouth
//    	}
//    	elseif (metadata includes 5)
//    	{
//    		renderAsAttachedToWest
//    	}
//    	if (metadata includes 8)
//    	{
//    		redstoneOutput = true
//    	}
//    	else
//    	{
//    		redstoneOutput = false
//    	}
//    }
//    public void place(int x, int y, int z, World world)
//    {
//    	metadata = null
//    	if (isPlacedOnTop)
//    	{
//    		metadata = 1
//    	}
//    	elseif (isPlacedOnNorth)
//    	{
//    		metadata = 2
//    	}
//    	elseif (isPlacedOnEast)
//    	{
//    		metadata = 3
//    	}
//    	elseif (isPlacedOnSouth)
//    	{
//    		metadata = 4
//    	}
//    	elseif (isPlacedOnWest)
//    	{
//    		metadata = 5
//    	}
//    	world.setMetadata(x,y,z,metadata)
//    
//    }
//psuedocode end
//copypasta start
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 2;
    }
    /**
     * Gets if we can place a torch on a block.
     */
    private boolean canPlaceTorchOn(World par1World, int par2, int par3, int par4)
    {
        if (par1World.doesBlockHaveSolidTopSurface(par2, par3, par4))
        {
            return true;
        }
        else
        {
            int var5 = par1World.getBlockId(par2, par3, par4);
            return var5 == Block.fence.blockID || var5 == Block.netherFence.blockID || var5 == Block.glass.blockID;
        }
    }
    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) ? true : (par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) ? true : this.canPlaceTorchOn(par1World, par2, par3 - 1, par4))));
    }
    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
    {
        int var9 = par1World.getBlockMetadata(par2, par3, par4);

        if (par5 == 1 && this.canPlaceTorchOn(par1World, par2, par3 - 1, par4))
        {
            var9 = 5;
        }

        if (par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
        {
            var9 = 4;
        }

        if (par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
        {
            var9 = 3;
        }

        if (par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
        {
            var9 = 2;
        }

        if (par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
        {
            var9 = 1;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, var9);
    }
    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.getBlockMetadata(par2, par3, par4) == 0)
        {
            this.onBlockAdded(par1World, par2, par3, par4);
        }
    }
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1);
        }
        else if (par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }
        else if (par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }
        else if (par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
        }
        else if (this.canPlaceTorchOn(par1World, par2, par3 - 1, par4))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
        }

        this.dropTorchIfCantStay(par1World, par2, par3, par4);
    }
    /**
     * Tests if the block can remain at its current location and will drop as an item if it is unable to stay. Returns
     * True if it can stay and False if it drops. Args: world, x, y, z
     */
    private boolean dropTorchIfCantStay(World par1World, int par2, int par3, int par4)
    {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
        {
            if (par1World.getBlockId(par2, par3, par4) == this.blockID)
            {
                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }

            return false;
        }
        else
        {
            return true;
        }
    }
    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        int var7 = par1World.getBlockMetadata(par2, par3, par4) & 7;
        float var8 = 0.15F;

        if (var7 == 1)
        {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 2)
        {
            this.setBlockBounds(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 3)
        {
            this.setBlockBounds(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
        }
        else if (var7 == 4)
        {
            this.setBlockBounds(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
        }
        else
        {
            var8 = 0.1F;
            this.setBlockBounds(0.5F - var8, 0.0F, 0.5F - var8, 0.5F + var8, 0.6F, 0.5F + var8);
        }

        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }   
    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0;
    }
    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);

        if ((var6 & 8) == 0)
        {
            return false;
        }
        else
        {
            int var7 = var6 & 7;
            return var7 == 0 && par5 == 0 ? true : (var7 == 7 && par5 == 0 ? true : (var7 == 6 && par5 == 1 ? true : (var7 == 5 && par5 == 1 ? true : (var7 == 4 && par5 == 2 ? true : (var7 == 3 && par5 == 3 ? true : (var7 == 2 && par5 == 4 ? true : var7 == 1 && par5 == 5))))));
        }
    }
    //copypasta end
    /*
     * Returns true if the block at x,y,z is providing power, false otherwise.
     */
    public boolean amIPowering(World world, int x, int y, int z)
    {
    	return isIndirectlyPoweringTo(world, x, y, z, 1) || isIndirectlyPoweringTo(world, x, y, z, 4);
    }
    //copypasta start
    /*
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        double var7 = (double)((float)par2 + 0.5F);
        double var9 = (double)((float)par3 + 0.7F);
        double var11 = (double)((float)par4 + 0.5F);
        double var13 = 0.2199999988079071D;
        double var15 = 0.27000001072883606D;
        //copypasta end
        if (!amIPowering(par1World, par2, par3, par4))
        {
        	//copypasta start
        	if (var6 == 1)
        	{
            	par1World.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
            	par1World.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        	}
        	else if (var6 == 2)
        	{
            	par1World.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
            	par1World.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        	}
        	else if (var6 == 3)
        	{
            	par1World.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
            	par1World.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
        	}
        	else if (var6 == 4)
        	{
        		par1World.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
            	par1World.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
        	}
        	else
        	{
            	par1World.spawnParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
            	par1World.spawnParticle("flame", var7, var9, var11, 0.0D, 0.0D, 0.0D);
        	}
        //copypasta end
        }
        //copypasta start
    }
    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        this.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
    }
    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            int var10 = par1World.getBlockMetadata(par2, par3, par4);
            int var11 = var10 & 7;
            int var12 = 8 - (var10 & 8);
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 + var12);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, var12 > 0 ? 0.6F : 0.5F);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);

            if (var11 == 1)
            {
                par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            }
            else if (var11 == 2)
            {
                par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            }
            else if (var11 == 3)
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            }
            else if (var11 == 4)
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
            }
            else if (var11 != 5 && var11 != 6)
            {
                if (var11 == 0 || var11 == 7)
                {
                    par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
                }
            }
            else
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            }
            return true;
        }
    }
    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }
}
