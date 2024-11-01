package Actor;

public class Enemy extends Actor {
	
	protected int expReward;

	public Enemy(String name, String desc, int lvl, int str, int dex, int vit, int baseHP, int hpPerLvl, int hpPerVit, int expRwrd) {
		super(name, desc, lvl, str, dex, baseHP, vit, baseHP, hpPerLvl, hpPerVit);
		this.expReward = expRwrd;
	}
	
	public int getExpReward() {
		return this.expReward;
	}

	@Override
	public String toString() {
		return "Lvl. [" + this.level + "] " + this.name;
	}

}
