
/**
 * A product in the inventory.
 * 4 attributes:
 * 	name,
 * 	amount,
 * 	unit,
 * 	unknownState.
 * @author edvinlun
 *
 */
public class Product {

	private String name;
	private float amount;
	private String unit;
	private boolean uncertain;
	
	public Product(String name, float amount, String unit, boolean knownState) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.setKnown(knownState);
	}
	
	/**
	 * Get the current (known) amount of requested product.
	 * 
	 * @return 
	 */
	public float getAmount(String prodName) {
		//TODO		
		return 0;
	}
	
	/**
	 * @return True if the amount of given product is exactly known,
	 * 			otherwise false.
	 */
	public boolean amountKnown(String prodName) {
		//TODO
		return false;
	}

	public String getName() {
		return name;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public boolean uncertain() {
		return uncertain;
	}

	public void setKnown(boolean uncertain) {
		this.uncertain = uncertain;
	}
	
	public String toString(){
		return name + "," + amount + unit + "," + uncertain; 
	}
	
	
}
