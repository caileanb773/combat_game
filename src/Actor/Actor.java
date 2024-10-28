package Actor;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Usable.Equipment;
import Utility.User;

public class Actor {
	
	// name, description
	private String name;
	private String desc;
	List<Equipment> gear;
	
	// stats
	private int level;
	private int baseLvlExp;
	private int experience;
	private int expToNextLvl;
	private int strength;
	private double minAtkDamage;
	private double maxAtkDamage;
	private int dexterity;
	private double baseChanceToHit;
	private double chanceToHit;
	private int defense;
	private int vitality;
	private int maxHP;
	private int currentHP;
	private int baseHP;
	private int hpPerLevel;
	private int hpPerVitality;
	
	// utility
	User u = new User();
	// this may need to be deleted
	Scanner in = new Scanner(System.in);
	
	public Actor() {
		
	}
	
	// parameterized constructor
	public Actor(String name, String desc, int str, int dex, double baseChanceHit, int vit, int baseHP, int hpPerLvl, int hpPerVit) {
		this.name = name;
		this.desc = desc;
		this.level = 1;
		this.experience = 0;
		this.strength = str;
		this.dexterity = dex; 
		this.baseChanceToHit = baseChanceHit;
		this.vitality = vit;
		this.baseHP = baseHP;
		this.hpPerLevel = hpPerLvl;
		this.hpPerVitality = hpPerVit;
		this.currentHP = maxHP;
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
	}
	
	// leveling up
	public void levelUp() {
		this.level++;
		
		// Somewhere here need to determine which stats the user would like to increase
		
		// Recalculate dependent stats
		calculateDependentStats();
		
		// Reset HP to max
		this.currentHP = maxHP;
	}
	
	public void levelUpUserPrompt() {
		for (int i = 0; i < 3; i ++) {
			while (true) {
				System.out.println("Which stat would you like to level up?");
				if (incrementStat(u.getUserIn(in))) {
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
	
	// TODO check that calculateDependentStats doesn't overwrite modifiers
	
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
	
	public boolean hitConnects(Actor target) {
		Random r = new Random();
		double attackChance = this.chanceToHit;
		double targetDefense = target.defense;
		double randomValue = r.nextDouble() * 100;
		
		// Return true if attack connects, false if it doesn't
 		return randomValue < (attackChance - targetDefense);
	}
	
	public void attack(Actor target) {
		// calculate if the hit connects based on chance to hit
		if (hitConnects(target)) {
			double damage = calculateDamage();
			target.takeDamage(damage);
		} else {
			System.out.println("Attack missed! GABAGOOL THIS IS A PLACEHOLDER DELETE ME GABAGOOL");
		}
	}
	
	public void actorDeath() {
		System.out.println(this.name + " died.");
	}
	
	/* Miscellaneous */
	@Override
	public String toString() {
		return "Name: " + this.name + "Description: " + this.desc;
	}
	
}
