package net.mrscauthd.boss_tools.entity.alien;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.village.GossipManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.*;
//import net.mrscauthd.boss_tools.procedures.AlienOnEntityTickUpdateProcedure;
import net.mrscauthd.boss_tools.events.Config;
//import net.mrscauthd.boss_tools.BossToolsModElements;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

import javax.annotation.Nullable;

public class AlienEntity extends AgeableEntity implements IMerchant, INPC {
//public class AlienEntity extends BossToolsModElements.ModElement implements IMerchant, INPC {

	@Nullable
	private PlayerEntity customer;
	private Set<UUID> tradedCustomers = new HashSet<>();
	@Nullable
	private MerchantOffers offers;
	private final GossipManager gossip = new GossipManager();
	public static final DataParameter<Integer> ALIEN_TYPE = EntityDataManager.createKey(AlienEntity.class, DataSerializers.VARINT);

	private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.EMERALD);
	public AlienJobs job;

	public AlienEntity(EntityType<? extends AgeableEntity> type, World worldIn) {
		super(type, worldIn);
	}
	public int getJobId(){
		return this.dataManager.get(ALIEN_TYPE);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 20)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED,0.25D);
	}

	public int getAlienType() {
		//System.out.println(this.dataManager.get(ALIEN_TYPE));
		return this.dataManager.get(ALIEN_TYPE);
	}

	public void setAlienType(int type) {
		this.dataManager.set(ALIEN_TYPE, type);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		//this.eatGrassGoal = new EatGrassGoal(this);
		this.goalSelector.addGoal(2, new SwimGoal(this));
		this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
		//this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		//this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(1, new TradeGoal(this));
		this.goalSelector.addGoal(1, new FollowGoal(this, 1.1D,false));
		//this.goalSelector.addGoal(1, new TraderunGoal(this, 1.1D, TEMPTATION_ITEMS, false));
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

	// Lead FIX
	@Override
	public boolean canBeLeashedTo(PlayerEntity player) {
		return false;
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
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

		if (worldIn.getRandom().nextFloat() < 0.2F) {
			this.setChild(true);
		}

		this.setAlienType(this.rand.nextInt(AlienJobs.values().length));
		this.getOffers();

		//System.out.println("spawn");

//		List<AlienJobs> x = new ArrayList<>();
//		x = Arrays.asList(AlienJobs.values());

//		int max = x.size()-1;
//		int min = 0;

//		this.job = x.get(new Random().nextInt((max+1)-min)+min);
		//System.out.println(job.id);

		//this.id = job.id;

		//this.id = 100;

		//System.out.println("Alien: "+(job != null));

	//	this.dataManager.set(ALIEN_TYPE, job.id);

		//this.getPersistentData().putDouble("texture", job.id);

		return spawnDataIn;
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ALIEN_TYPE, 0);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		//System.out.println("hit");
		for(MerchantOffer merchantoffer : this.getOffers()) {
			merchantoffer.resetUses();
		}

		//}

		//for(int j = 0; j < i; ++j) {
		if (this.isChild() == false) {
			for(MerchantOffer merchantoffer : this.getOffers()) {
				int max = 1;
				int min = 0;
				merchantoffer.increaseSpecialPrice((int) Math.floor((amount*new Random().nextInt((max+1)-min)+min)));
			}
		}
		return super.attackEntityFrom(source,amount);
	}


	@Override
	public MerchantOffers getOffers() {
		if (this.offers == null) {
			this.offers = new MerchantOffers();

			int max = 7;
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
	//remove despawning
	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
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

	@Override
	public void tick() {
		super.tick();
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setAlienType(compound.getInt("JobId"));
		if (compound.contains("Offers", 10)) {
			this.offers = new MerchantOffers(compound.getCompound("Offers"));
		}
	//	if (compound.contains("JobId")) {
	//		int x = (compound.getInt("JobId"));


			//	System.out.println("load job " + x);

			//this.id = x;

		//	List<AlienJobs> y = new ArrayList<>();
		//	y = Arrays.asList(AlienJobs.values());
		//	this.job = y.get(x);
		//	this.setAlienType(this.rand.nextInt(AlienJobs.values().length-1));

			//this.getPersistentData().putDouble("texture", job.id);

		//}
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		MerchantOffers merchantoffers = this.getOffers();
		compound.put("Offers", merchantoffers.write());
		compound.putInt("JobId", this.getAlienType());
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
		Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = AlienTrade.TRADES.get(AlienJobs.values()[this.getAlienType()]);
		//VillagerData villagerdata = this.getVillagerData();
		//Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = AlienTrade.TRADES.get(VillagerProfession.WEAPONSMITH);
		if (int2objectmap != null && !int2objectmap.isEmpty()) {
			VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(i);
			if (avillagertrades$itrade != null) {
				MerchantOffers merchantoffers = this.getOffers();

				int max = 18;
				int min = 3;

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
		if (!this.hasCustomer() && (sourceentity.getHeldItemMainhand().getItem() != ModInnet.ALIEN_SPAWN_EGG.get()) && this.isChild() == false) {
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
		}
		return ActionResultType.func_233537_a_(this.world.isRemote);
	}
	//@OnlyIn(Dist.CLIENT)
	private void displayMerchantGui(PlayerEntity player) {
		this.recalculateSpecialPricesFor(player);
		this.setCustomer(player);
		//this.openMerchantContainer(player, ITextComponent.getTextComponentOrEmpty(this.getDisplayName().getString()+" - "+this.job.getJobDisplayname().getString()), 1);
		//this.openMerchantContainer(player, ITextComponent.getTextComponentOrEmpty(this.getDisplayName().getUnformattedComponentText() + " - " + this.job.getJobDisplayname().getUnformattedComponentText()), 1);
		AlienJobs j = AlienJobs.values()[getAlienType()];
		this.openMerchantContainer(player, new TranslationTextComponent(this.getDisplayName().getString() + " - " +  j.getJobDisplayname().getString()),1);
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
	public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
		double d0 = this.rand.nextDouble();
		AlienEntity villagerentity = new AlienEntity((EntityType<? extends AlienEntity>) ModInnet.ALIEN.get(), world);
		villagerentity.setAlienType(villagerentity.rand.nextInt(AlienJobs.values().length));
		return villagerentity;
	}

	public ResourceLocation getTexture() {
		return AlienJobs.values()[this.getAlienType()].TEXTURE;
	}
	@Override
	public void baseTick() {
		super.baseTick();
		double x = this.getPosX();
		double y = this.getPosY();
		double z = this.getPosZ();
		Entity entity = this;
		if (Config.AlienSpawing == false) {
			if (!entity.world.isRemote())
				entity.remove();
		}
	}
}