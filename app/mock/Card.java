package mock;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import data.CardCategory;

public class Card implements contracts.game.ICard {

    List<contracts.game.ICardCategory> categories;

    public Card() {
	this.categories = new LinkedList<>();

	this.categories.add(new CardCategory(0, "population", "120.000"));
	this.categories.add(new CardCategory(1, "area", "40"));
	this.categories.add(new CardCategory(2, "indebtedness", "20%"));
	this.categories.add(new CardCategory(3, "overnightstays", "102.233"));
	this.categories.add(new CardCategory(4, "sportfacilities", "47"));
    }

    public String getName() {
	return "Innsbruck";
    }

    @Override
    public String getImageUrl() {
	return "images/tirol.jpg";
    }

    @Override
    public List<contracts.game.ICardCategory> getCategories() {
	return this.categories;
    }

    @Override
    public UUID getID() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int getPopulation() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public float getArea() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public float getIndebtedness() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getNights() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getSportFields() {
	// TODO Auto-generated method stub
	return 0;
    }
}
