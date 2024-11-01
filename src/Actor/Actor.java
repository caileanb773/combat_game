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
	
	// utility
	GameUtility gUtil = new GameUtility();
	// this may need to be deleted
	
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
	
	/* This section is dedicated to level up methods */
	
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
	
	// leveling up
	public void levelUp() {
		this.level++;
		
		// Somewhere here need to determine which stats the user would like to increase
		
		// Recalculate dependent stats
		calculateDependentStats();
	}
	
	public void levelUpUserPrompt() {
		
		for (int i = 0; i < 3; i ++) {
			while (true) {
				System.out.println("Which stat would you like to level up?");
				if (incrementStat(gUtil.getUserInputStr(GameUtility.in))) {
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
	
	/* This section is dedicated to attack and defenses modifiers (mostly for gear)*/
	
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
	
	/* This section is dedicated to combat methods */
	
	public boolean isAlive() {
		if (this.currentHP > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String showActorHP() {
		return "[" + this.name + "] HP: " + this.currentHP;
	}
	
	// TODO probably find a better way than hard printing to console
	public int attack(Actor target) {
		// calculate if the hit connects based on chance to hit
		if (hitConnects(target)) {
			
			// probably find a better way than casting to an int here
			int damage = (int)calculateDamage();
			target.takeDamage(damage);
			System.out.println(this.name + " hit " + target.name + " for " + damage + " damage!");
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
	
	public void takeDamage(double value) {
		this.currentHP -= value;
		if (currentHP <= 0) {
			actorDeath();
		}
	}
	
	public void actorDeath() {
		System.out.println(this.name + " died.");
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
	
	/* Miscellaneous */

	
}
