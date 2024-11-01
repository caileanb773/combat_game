package Actor;

public class Player extends Actor {

	public Player(String name, String desc, int lvl, int str, int dex, int vit, int baseHP, int hpPerLvl, int hpPerVit) {
		super(name, desc, lvl, str, dex, baseHP, vit, baseHP, hpPerLvl, hpPerVit);
	}

	@Override
	public String toString() {
		return "Player Stats:\n"
				+ "Name: \t" + this.name + "\n"
				+ "HP: \t[" + this.currentHP + "/" + this.maxHP + "] hp\n"
				+ "Lvl: \t[" + this.level + "]\n"
				+ "Str: \t[" + this.strength + "]\n"
				+ "Dex: \t[" + this.dexterity + "]\n"
				+ "Vit: \t[" + this.vitality + "]\n"
				+ "Exp: \t[" + this.experience + "] xp\n"
				+ "Next: \t[" + (this.expToNextLvl - this.experience) + "] xp\n";
	}
	
	

}
