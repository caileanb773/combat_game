package Actor;

public class Zombie extends Enemy {
	
	public Zombie(int lvl, int str, int dex, int vit, int baseHP, int hpPerLvl, int hpPerVit, int expRwrd) {
		super("Zombie", "A decomposing zombie.", lvl, str, dex, vit, baseHP, hpPerLvl, hpPerVit, expRwrd);
	}

}
