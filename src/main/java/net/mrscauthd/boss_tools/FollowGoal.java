/**
 * This mod element is always locked. Enter your code in the methods below.
 * If you don't need some of these methods, you can remove them as they
 * are overrides of the base class BossToolsModElements.ModElement.
 *
 * You can register new events in this class too.
 *
 * As this class is loaded into mod element list, it NEEDS to extend
 * ModElement class. If you remove this extend statement or remove the
 * constructor, the compilation will fail.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser - New... and make sure to make the class
 * outside net.mrscauthd.boss_tools as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
*/
package net.mrscauthd.boss_tools;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.mrscauthd.boss_tools.entity.AlienEntity;
//import space_traveler.com.space_traveler.entitys.alien.AlienEntity;

public class FollowGoal extends Goal {
    private static final EntityPredicate ENTITY_PREDICATE = (new EntityPredicate()).setDistance(10.0D).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks().setLineOfSiteRequired();
    protected final AlienEntity creature;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double pitch;
    private double yaw;
    protected PlayerEntity closestPlayer;
    private int delayTemptCounter;
    private boolean isRunning;
    //private final Ingredient temptItem;
    private final boolean scaredByPlayerMovement;

    public FollowGoal(AlienEntity creatureIn, double speedIn, boolean scaredByPlayerMovementIn) {
        this.creature = creatureIn;
        this.speed = speedIn;
        //this.temptItem = temptItemsIn;
        this.scaredByPlayerMovement = scaredByPlayerMovementIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(creatureIn.getNavigator() instanceof GroundPathNavigator) && !(creatureIn.getNavigator() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        /*if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
        } else {
            this.closestPlayer = this.creature.world.getClosestPlayer(ENTITY_PREDICATE, this.creature);
            if (this.closestPlayer == null) {
                return false;
            } else {
                System.out.println(this.isTempting(this.creature,this.closestPlayer));
                return this.isTempting(this.creature,this.closestPlayer);
            }
        }
    }

    protected boolean isTempting(AlienEntity entity,PlayerEntity playerEntity) {
        if(!entity.hasCustomer()){
            return false;
        }
        return creature.getCustomer().getUniqueID().toString() == playerEntity.getUniqueID().toString();*/
        return creature.hasCustomer();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        //if (this.isScaredByPlayerMovement()) {
        //if (this.creature.getDistanceSq(this.creature.getCustomer()) < 36.0D) {
        if (this.creature.getCustomer() != null) {
            if (this.creature.getCustomer().getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) {
                return false;
            }

            if (Math.abs((double) this.creature.getCustomer().rotationPitch - this.pitch) > 5.0D || Math.abs((double) this.creature.getCustomer().rotationYaw - this.yaw) > 5.0D) {
                return false;
            }
            //} else {
            this.targetX = this.creature.getCustomer().getPosX();
            this.targetY = this.creature.getCustomer().getPosY();
            this.targetZ = this.creature.getCustomer().getPosZ();
            //}

            this.pitch = (double) this.creature.getCustomer().rotationPitch;
            this.yaw = (double) this.creature.getCustomer().rotationYaw;
            //}

        }
        return this.shouldExecute();
    }

    protected boolean isScaredByPlayerMovement() {
        return this.scaredByPlayerMovement;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.targetX = this.creature.getCustomer().getPosX();
        this.targetY = this.creature.getCustomer().getPosY();
        this.targetZ = this.creature.getCustomer().getPosZ();
        this.isRunning = true;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        // this.closestPlayer = null;
        this.creature.getNavigator().clearPath();
        this.delayTemptCounter = 100;
        this.isRunning = false;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.creature.getLookController().setLookPositionWithEntity(this.creature.getCustomer(), (float)(this.creature.getHorizontalFaceSpeed() + 20), (float)this.creature.getVerticalFaceSpeed());
        if (this.creature.getDistanceSq(this.creature.getCustomer()) < 6.25D) {
            this.creature.getNavigator().clearPath();
        } else {
            this.creature.getNavigator().tryMoveToEntityLiving(this.creature.getCustomer(), this.speed);
        }

    }

    /**
     * @see #isRunning
     */
    public boolean isRunning() {
        return this.isRunning;
    }
}