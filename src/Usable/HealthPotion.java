package Usable;

public class HealthPotion extends Consumable implements Potion {

	public int pointsToHeal;
	public String potSize;
	public HealthPotion(String size) {
		this.potSize = size;
		switch (size) {
		case "small":
			this.pointsToHeal = 50;
			break;
		case "medium":
			this.pointsToHeal = 75;
			break;
		case "large":
			this.pointsToHeal = 100;
			break;
		default:
			break;
		}
		
		this.itemName = "A " + size + " health potion.";
		this.itemDesc = "Recovers " + pointsToHeal + " health points.";
	}

	@Override
	public void use() {
		System.out.println("Drank a potion and recovered " + this.pointsToHeal + " HP.");
	}

	@Override
	public String getDescription() {
		return this.itemDesc;
	}
	
	@Override
	public String toString() {
		return this.potSize + " potion.";
	}

}
