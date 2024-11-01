package Actor;

public class Player extends Actor {

	public Player(String name, String desc, int lvl, int str, int dex, int vit, int baseHP, int hpPerLvl, int hpPerVit) {
		super(name, desc, lvl, str, dex, baseHP, vit, baseHP, hpPerLvl, hpPerVit);
	}

	@Override
	public String toString() {
		return "Player Info:\n"
				+ "Name: " + this.name + "\n"
				+ "Lvl: [" + this.level + "]\n"
				+ "Str: [" + this.strength + "]\n"
				+ "Dex: [" + this.dexterity + "]\n"
				+ "Vit: [" + this.vitality + "]\n"
				+ "Exp: [" + this.experience + "]\n"
				+ "Next Lvl: [" + (this.expToNextLvl - this.experience) + "]\n";
	}
	
	

}
