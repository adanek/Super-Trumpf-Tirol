package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import play.Play;

public class DatabaseController {

    public static boolean flagLoaded = false;
    public static int g = 0;

    public static void load() {

	if (flagLoaded)
	    return;
	File myfile = Play.application().getFile("/app/data/Daten.csv");
	if (!myfile.exists())
	    throw new IllegalArgumentException("wrong path");

	try (Scanner s = new Scanner(myfile);) {
	    long i = 0;
	    while (s.hasNextLine()) {
		Scanner line = new Scanner(s.nextLine());
		line.useDelimiter(";");
		
		Commune c = new Commune();
		c.setId(i);
		c.setName(line.next());
		c.setPopulation(line.nextInt());
		c.setArea(line.nextFloat());
		c.setIndebtedness(line.nextFloat());
		c.setNights(line.nextInt());
		c.setSportFields(line.nextInt());
		c.save();
		
		Ranking rank = new Ranking();
		rank.setId(i);
		rank.setName(c.getName());
		rank.setRankPopulation(line.nextInt());
		rank.setRankArea(line.nextInt());
		rank.setRankIndebtedness(line.nextInt());
		rank.setRankNights(line.nextInt());
		rank.setRankSportFields(line.nextInt());
		rank.save();
		line.close();
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

    }

    public static List<Commune> getData() {
	return Commune.find.all();
    }
}
