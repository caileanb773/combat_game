package Actor;

import java.util.List;
import java.util.Random;

import Usable.Equipment;
import Utility.GameUtility;

public class Actor {

	// name, description
	protected String name;
	protected String desc;
	protected List<Equipment> gear;

	// stats
	protected int level;
	protected int baseLvlExp;
	protected int experience;
	protected int expToNextLvl;
	protected int strength;
	protected double minAtkDamage;
	protected double maxAtkDamage;
	protected int dexterity;
	protected double baseChanceToHit;
	protected double chanceToHit;
	protected int defense;
	protected int vitality;
	protected int maxHP;
	protected int currentHP;
	protected int baseHP;
	protected int hpPerLevel;
	protected int hpPerVitality;

	public Actor() {

	}

	// parameterized constructor
	public Actor(String name, String desc, int lvl, int str, int dex, double baseChanceHit, int vit, int baseHP, int hpPerLvl, int hpPerVit) {
		this.name = name;
		this.desc = desc;
		this.level = lvl;
		this.experience = 0;
		this.strength = str;
		this.dexterity = dex; 
		this.baseChanceToHit = baseChanceHit;
		this.vitality = vit;
		this.baseHP = baseHP;
		this.hpPerLevel = hpPerLvl;
		this.hpPerVitality = hpPerVit;
		calculateDependentStats();
	}

	/* ==================== This section is dedicated to level up methods ==================== */

	// gain exp
	public void gainExp(int exp) {
		this.experience += exp;
		if (this.experience >= this.expToNextLvl) {
			levelUp();
		}
	}

	public void calculateDependentStats() {
		this.baseLvlExp = (level * 100);
		this.expToNextLvl = (baseLvlExp * (level * 2));
		this.minAtkDamage = (strength * 1.5);
		this.maxAtkDamage = (strength * 2);
		this.defense = (dexterity * 2);
		this.chanceToHit = (100 - baseChanceToHit) + (dexterity * 1.5);
		this.maxHP = baseHP + (hpPerLevel * level) + (vitality * hpPerVitality);
		this.currentHP = this.maxHP;
	}

	// TODO small bug here that shows player's XP as being -50 before showing the correct amount
	
	// leveling up
	public void levelUp() {
		this.level++; // increment player's level
		System.out.println(this.toString());
		levelUpUserPrompt(); // ask them which stat they would like to level up
		calculateDependentStats(); // Recalculate dependent stats
		System.out.println(this.toString());
	}

	public void levelUpUserPrompt() {

		// This could be changed later based on character level
		int pointsToSpend = 3;

		for (int i = 0; i < pointsToSpend; i ++) {
			while (true) {
				System.out.println("Which stat would you like to level up?");
				if (incrementStat(GameUtility.getUserInputStr(GameUtility.in))) {
					break;
				} else {
					continue;
				}
			}
		}
	}

	public void incrStatFeedback(String stat) {
		System.out.println("[" + stat + "] increased by 1.");
	}

	// TODO implement this with enums possibly
	public boolean incrementStat(String stat) {
		stat = stat.toLowerCase();
		switch (stat) {
		case "strength":
			incrStatFeedback("Strength");
			this.strength++;
			return true;
		case "str":
			incrStatFeedback("Strength");
			this.strength++;
			return true;
		case "dexterity":
			incrStatFeedback("Dexterity");
			this.dexterity++;
			return true;
		case "dex":
			incrStatFeedback("Dexterity");
			this.dexterity++;
			return true;
		case "vitality":
			incrStatFeedback("Vitality");
			this.vitality++;
			return true;
		case "vit":
			incrStatFeedback("Vitality");
			this.vitality++;
			return true;
		default:
			System.out.println("That's not a valid stat!");
			return false;
		}
	}

	/* ==================== This section is dedicated to attack and defenses modifiers (mostly for gear) ==================== */

	// TODO check that calculateDependentStats doesn't overwrite modifiers (i think it does)

	public void modifyStrength(int value) {
		this.strength += value;
		calculateDependentStats();
	}

	public void modifyDex(int value) {
		this.dexterity += value;
		calculateDependentStats();
	}

	public void modifyDefense(int value) {
		this.defense += value;
		calculateDependentStats();
	}

	/* ==================== This section is dedicated to combat methods ==================== */

	public void showCombatMenu() {
		System.out.println("[1] Attack\n"
				+ "[2] Use Item\n"
				+ "[3] Equip\n"
				+ "[4] Harden\n"
				+ "[5] Flee");

	}

	// select something from the combat menu
	public void combatMenuChoice(Enemy enemy) {
		int choice = GameUtility.getUserInputInt(GameUtility.in);

		switch (choice) {
		case 1:
			attack(enemy);
			break;
		case 2:
			System.out.println("Use item (unimplemented)");
			break;
		case 3:
			System.out.println("Equip (unimplemented)");
			break;
		case 4:
			System.out.println("Harden (unimplemented)");
			break;
		case 5:
			System.out.println("Flee (unimplemented)");
			break;
		default:
			System.out.println("Gabagool! combatMenuChoice default case.");
			break;
		}

	}

	// main combat method, calls almost every other combat method at some point
	public void combat(Actor player, Enemy enemy) {
		boolean isPlayerTurn = true; // player always goes first
		
		System.out.println(player.name + " encountered a " + enemy + "!"); // encounter message

		// combat loop
		do {
			
			if (isPlayerTurn) { // player's turn
				if (enemy.isAlive()) {
					combatStatus(player, enemy);
					showCombatMenu();
					combatMenuChoice(enemy);
					if (!(enemy.isAlive())) {
						break; // enemy died after last hit, break from loop
					}
					isPlayerTurn = false;
				} else {
					break; // enemy is dead, break from combat loop
				}
				
			} else { // enemy's turn
				if (player.isAlive()) {
					combatStatus(player, enemy);
					enemy.attack(player);
					if (!(player.isAlive())){
						break; // player died after last hit, break from loop
					}
					isPlayerTurn = true;
				} else {
					break; // player is dead, break from combat loop
				}
			}

		} while (player.isAlive() && enemy.isAlive()); // perform this loop while both the player and the enemy are alive

		if (player.isAlive()) {
			// clean up the combat state
			cleanupCombat(enemy);
		} else {
			System.out.println("Gabagool! You were defeated by " + enemy);
		}

	}
	
	public void combatStatus(Actor actor, Enemy enemy) {
		System.out.println(actor.showActorHP() + "\n"
						 + enemy.showActorHP());
	}

	// Award XP and loot, remove enemies from combat, etc
	public void cleanupCombat(Enemy enemy) {
		int xpReward = enemy.getExpReward();
		System.out.println("You defeated " + enemy + "!\n"
				+ "You gained " + xpReward + "xp!");
		gainExp(xpReward);
	}

	public boolean isAlive() {
		if (this.currentHP >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public String showActorHP() {
		return this.name + " HP: [" + this.currentHP + "/" + this.maxHP + "]";
	}

	// TODO probably find a better way than hard printing to console
	public int attack(Actor target) {
		System.out.println(this.name + " goes in for an attack!");
		// calculate if the hit connects based on chance to hit
		if (hitConnects(target)) {

			// probably find a better way than casting to an int here
			int damage = (int)calculateDamage();
			target.takeDamage(target, damage);
			System.out.println(this.name + " hit " + target.name + " for " + damage + " damage!");
			if (target.currentHP <= 0) {
				actorDeath(target);
			}
			return damage;
		} else {
			System.out.println(this.name + "'s attack missed!");
			return 0;
		}
	}

	public boolean hitConnects(Actor target) {
		Random r = new Random();
		double attackChance = this.chanceToHit;
		double targetDefense = target.defense;
		double randomValue = r.nextDouble() * 100;

		// Return true if attack connects, false if it doesn't
		return randomValue < (attackChance - targetDefense);
	}

	public double calculateDamage() {
		Random r = new Random();
		return r.nextDouble(minAtkDamage, maxAtkDamage);
	}

	public void takeDamage(Actor target, double value) {
		this.currentHP -= value;
	}

	public void actorDeath(Actor actor) {
		System.out.println(actor.name + " died.");
	}

	@Override
	public String toString() {
		return "Actor [name=" + name + ", desc=" + desc + ", gear=" + gear + ", level=" + level + ", baseLvlExp="
				+ baseLvlExp + ", experience=" + experience + ", expToNextLvl=" + expToNextLvl + ", strength="
				+ strength + ", minAtkDamage=" + minAtkDamage + ", maxAtkDamage=" + maxAtkDamage + ", dexterity="
				+ dexterity + ", baseChanceToHit=" + baseChanceToHit + ", chanceToHit=" + chanceToHit + ", defense="
				+ defense + ", vitality=" + vitality + ", maxHP=" + maxHP + ", currentHP=" + currentHP + ", baseHP="
				+ baseHP + ", hpPerLevel=" + hpPerLevel + ", hpPerVitality=" + hpPerVitality + "]";
	}

	/* ==================== Miscellaneous ==================== */


}
