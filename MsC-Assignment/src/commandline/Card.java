package commandline;

import java.util.Arrays;

/**
 * This is the Card class, which will be objects stored within a static array of
 * 40 cards per deck. The name and attributes are separated to make identifying
 * each card easier.
 */
public class Card {

	String name;
	int[] attributes = new int[5];
	String[] attributeNames = new String[5];

	/**
	 * The constructor is actually accessed through the 'createACard' method, which
	 * handles separating the name of the card from the rest of the attributes.
	 */
	public Card(String name, int[] attributes, String[] attributeNames) {
		this.name = name;
		this.attributes = attributes;
		this.attributeNames = attributeNames;
	}

	public Card() {
	}

	@Override
	/**
	 * The toString is formatted to make reading the Test Log and related files
	 * easier.
	 */
	public String toString() {
		String card = "Card [Name = " + name + ": Attributes = " + this.attributeNames[0] + "(" + this.attributes[0]
				+ "), " + this.attributeNames[1] + "(" + this.attributes[1] + "), " + this.attributeNames[2] + "("
				+ this.attributes[2] + "), " + this.attributeNames[3] + "(" + this.attributes[3] + "), "
				+ this.attributeNames[4] + "(" + this.attributes[4] + ")]";

		return card;
	}

	public int[] getAttributes() {
		return attributes;
	}

	public String getName() {
		return name;
	}

	public void setAttributes(int[] attributes) {
		this.attributes = attributes;
	}

	/**
	 * This create a card method recieves the raw data from the Top Trumps deck and
	 * creates Card objects by separating the attributes from the Card's name.
	 */
	public static Card createCard(String[] a, String[] names) {

		int[] b = new int[5];
		String name = a[0];
		for (int i = 0; i < a.length - 1; i++) {

			b[i] = Integer.parseInt(a[i + 1]);
		}

		return new Card(name, b, names);
	}

	public String toStringAPI() {

		String card = "Name: " + name;
		for (int i = 0; i < 5; i++) {
			card += ", " + attributeNames[i] + ": " + attributes[i];

		}
		return card;

	}

}
