package net.mrscauthd.boss_tools.entity;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.village.GossipManager;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.procedures.AlienOnEntityTickUpdateProcedure;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroup;
import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.IPacket;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.mrscauthd.boss_tools.TradeGoal;
import net.minecraft.entity.monster.ZombieEntity;

import java.util.*;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

import javax.annotation.Nullable;

public class AlienEntity extends AnimalEntity implements IMerchant, INPC {
//public class AlienEntity extends BossToolsModElements.ModElement implements IMerchant, INPC {

	@Nullable
	private PlayerEntity customer;
	private Set<UUID> tradedCustomers = new HashSet<>();
	@Nullable
	private MerchantOffers offers;
	private final GossipManager gossip = new GossipManager();

	private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.EMERALD);

	public AlienEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 20)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED,0.25D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		//this.eatGrassGoal = new EatGrassGoal(this);
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(0, new TradeGoal(this));
		//this.goalSelector.addGoal(0, new TradeAlienWithPlayerGoal(this));
		//this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

    /*@Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        System.out.println("x");
        return super.applyPlayerInteraction(player, vec, hand);
    }*/

	protected void resetCustomer() {
		this.setCustomer((PlayerEntity)null);
	}
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		this.resetCustomer();
	}

	@Override
	protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_VILLAGER_AMBIENT; }

	@Override
	protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_VILLAGER_DEATH; }

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_VILLAGER_HURT;
	}

	@Override
	public void setCustomer(@Nullable PlayerEntity player) {
		this.customer = player;
	}

	@Nullable
	@Override
	public PlayerEntity getCustomer() {
		return this.customer;
	}

	@Override
	public MerchantOffers getOffers() {
		if (this.offers == null) {
			this.offers = new MerchantOffers();

			int max = 18;
			int min = 2;

			for (int i = 0; i < new Random().nextInt((max+1)-min)+min; i++){
				this.populateTradeData(i);
			}
		}

		return this.offers;
	}

	@Override
	public void setClientSideOffers(@Nullable MerchantOffers offers) {

	}

	@Override
	public void onTrade(MerchantOffer offer) {
		offer.increaseUses();
		if(this.customer != null)
		{
			this.tradedCustomers.add(this.customer.getUniqueID());
		}
	}

	@Override
	public void verifySellingItem(ItemStack stack) {

	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public int getXp() {
		return 0;
	}

	@Override
	public void setXP(int xpIn) {

	}

	@Override
	public boolean hasXPBar() {
		return false;
	}

	public boolean isPreviousCustomer(PlayerEntity player)
	{
		return this.tradedCustomers.contains(player.getUniqueID());
	}

	@Override
	public SoundEvent getYesSound() {
		return null;
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Offers", 10)) {
			this.offers = new MerchantOffers(compound.getCompound("Offers"));
		}

		//this.villagerInventory.read(compound.getList("Inventory", 10));
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		MerchantOffers merchantoffers = this.getOffers();
		//if (!merchantoffers.isEmpty()) {
		compound.put("Offers", merchantoffers.write());
		//}

		//compound.put("Inventory", this.villagerInventory.write());
	}

	protected void addTrades(MerchantOffers givenMerchantOffers, VillagerTrades.ITrade[] newTrades, int maxNumbers) {
		Set<Integer> set = Sets.newHashSet();
		if (newTrades.length > maxNumbers) {
			while(set.size() < maxNumbers) {
				set.add(this.rand.nextInt(newTrades.length));
			}
		} else {
			for(int i = 0; i < newTrades.length; ++i) {
				set.add(i);
			}
		}

		for(Integer integer : set) {
			VillagerTrades.ITrade villagertrades$itrade = newTrades[integer];
			MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.rand);
			if (merchantoffer != null) {
				givenMerchantOffers.add(merchantoffer);
			}
		}

	}

	protected void populateTradeData(int i)
	{
		//VillagerData villagerdata = this.getVillagerData();
		Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = AlienTrade.TRADES.get(VillagerProfession.WEAPONSMITH);
		if (int2objectmap != null && !int2objectmap.isEmpty()) {
			VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(i);
			if (avillagertrades$itrade != null) {
				MerchantOffers merchantoffers = this.getOffers();

				int max = 18;
				int min = 1;

				this.addTrades(getOffers(), avillagertrades$itrade, new Random().nextInt((max+1)-min)+min);
			}
		}
	}

	public boolean hasCustomer() {
		return this.customer != null;
	}

	@Override
	public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {

		//populateTradeData();
		ItemStack itemstack = sourceentity.getHeldItem(hand);
		if (hand == Hand.MAIN_HAND) {
			if (!this.world.isRemote) {
				//this.shakeHead();
			}

			sourceentity.addStat(Stats.TALKED_TO_VILLAGER);
		}

		//if (flag) {
		//    return ActionResultType.func_233537_a_(this.world.isRemote);
		//} else {
		if (!this.world.isRemote) {
			this.displayMerchantGui(sourceentity);
		}

		return ActionResultType.func_233537_a_(this.world.isRemote);
		//}
	}

	private void displayMerchantGui(PlayerEntity player) {
		this.recalculateSpecialPricesFor(player);
		this.setCustomer(player);
		this.openMerchantContainer(player, this.getDisplayName(), 1);
	}

	private void recalculateSpecialPricesFor(PlayerEntity playerIn) {
		int i = this.getPlayerReputation(playerIn);
		if (i != 0) {
			for(MerchantOffer merchantoffer : this.getOffers()) {
				merchantoffer.increaseSpecialPrice(-MathHelper.floor((float)i * merchantoffer.getPriceMultiplier()));
			}
		}

		if (playerIn.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
			EffectInstance effectinstance = playerIn.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE);
			int k = effectinstance.getAmplifier();

			for(MerchantOffer merchantoffer1 : this.getOffers()) {
				double d0 = 0.3D + 0.0625D * (double)k;
				int j = (int)Math.floor(d0 * (double)merchantoffer1.getBuyingStackFirst().getCount());
				merchantoffer1.increaseSpecialPrice(-Math.max(j, 1));
			}
		}

	}

	public int getPlayerReputation(PlayerEntity player) {
		return this.gossip.getReputation(player.getUniqueID(), (gossipType) -> {
			return true;
		});
	}

	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}
}
