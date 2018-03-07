package com.game.dice.persistance;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.game.dice.utils.NbrTours;

public class XmlEntityManager implements EntityManger {

	static XmlEntityManager xmlentityManager;
	private static String FILE_NAME = "dice_game.xml";

	public static EntityManger getInstance() {
		if (xmlentityManager == null)
			xmlentityManager = new XmlEntityManager();
		return xmlentityManager;
	}
		
	@SuppressWarnings("unchecked")
	public Map<String, String> charger() {
		Map<String, HashMap<String, String>> map = null;
		//ouverture de decodeur
		XMLDecoder decoder;
		try {
			decoder = new XMLDecoder(new FileInputStream(FILE_NAME));
			// deserialisation de l'objet
			map = (Map<String, HashMap<String, String>>) decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
				System.out.println("Fichier XML inexistant !");
				return null;
		}
		if (map != null && map.containsKey(String.valueOf(NbrTours.NOMBRE_MAX_TOURS))) 
			return map.get(String.valueOf(NbrTours.NOMBRE_MAX_TOURS));
			else 
				return null;
	}
	@SuppressWarnings("unchecked")
	public void save(int score, String pseudo) {
		Map<String, HashMap<String, String>> map = new HashMap<String, HashMap<String,String>>();
		XMLDecoder decoder;
		try {
			decoder = new XMLDecoder(new FileInputStream(FILE_NAME));
			// deserialisation de l'objet
			map = (Map<String, HashMap<String,String>>) decoder.readObject();
			decoder.close();
			
			if (map == null) {
				map = new HashMap<String, HashMap<String,String>>();
			}
		} catch (FileNotFoundException e2) {
			System.out.println(e2.getMessage());
		}
		Map<String, String> valeur = new HashMap<String,String>();
		valeur.put("score", String.valueOf(score));
		valeur.put("pseudo", pseudo);
		map.put(String.valueOf(NbrTours.NOMBRE_MAX_TOURS), (HashMap<String, String>) valeur );
		// ouverture de l'encodeur vers le fichier 
		XMLEncoder encoder ;
		try {
			encoder = new XMLEncoder(new FileOutputStream(FILE_NAME));
			encoder.writeObject(map);
			encoder.flush();
			encoder.close();
			System.out.println("Success");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}
