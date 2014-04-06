import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class FileSave {
	private final String fileName = "game.sav";
	public FileSave() {
		
	}
	
	public void save(GameState state) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		save(state,builder);
		String[] lines = builder.toString().split("\n");
		for (int i = 0;i< lines.length;i++) {
			//writer.append(lines[i]+"\n");
			writer.println(lines[i]);
		}
		writer.close();
		System.out.println("Game sucessfully saved.");
	}
	
	public void save(GameState state,StringBuilder writer) {
		writer.append("# ENCRYPTED FILE SAVE. PLEASE DO NOT PLAY WITH.\n");
		writer.append(store("loc"+state.playerLocation)+"\n");
		writer.append(store("guid"+state.playerGUID)+"\n");
		writer.append(store("stage"+state.playerStage)+"\n");
		writer.append(store("score"+(state.score))+"\n");
		writer.append(store("health"+(int)state.health)+"\n");
		writer.append(store("gEmS"+state.gems)+"\n");
		writer.append(store("DELTA"+state.playerDelta)+"\n");
		writer.append(store("COlor"+"(" + state.playerColor.getRed() + 
				"," + state.playerColor.getGreen() + "," + state.playerColor.getBlue() + ")")+"\n");
	}
	
	private String store(int value) {
		return store(value + "");
	}
	
	private String store(Object value) {
		if (value == null)
			return store("null");
		else
			return store(value.toString());
	}
	
	private String b64d(String data) {
		try {
			return new String(B64.decode(data));
		}
		catch (Throwable t) {
			return null;
		}
	}
	
	private String b64e(String data) {
		return B64.encode(data.getBytes());
	}
	
	private String store(String value) {
		value = b64e(value);
		for (int i = 0; i < 26; i++) {
			value = value.replace((char)(65 + i), (char)(i));
		}
		return b64e(value);
	}
	
	private String get(String value) {
		if (value == null)
			return null;
		value = b64d(value);
		for (int i = 0; i < 26; i++) {
			value = value.replace((char)(i),(char)(65 + i));
		}
		value = b64d(value);
		return value;
	}
	
	public GameState read(BufferedReader br) {
		GameState state = new GameState();
		String line;
		try {
			while ((line = br.readLine()) != null) {
			    if (!line.trim().startsWith("#")) {
			    	line = get(line);
			    	line = line.trim();
			    	//System.out.println(line);
			    	if (line.indexOf("\n") > -1)
		    			line = line.replace("\n", "");
			    	if (line.indexOf("loc") > -1) {
			    		line = line.replace("loc","");
			    		line = line.replace("(", "");
			    		line = line.replace(")", "");
			    		String[] pieces = line.split(",");
			    		state.playerLocation = new P3D(Float.parseFloat(pieces[0]),
			    				Float.parseFloat(pieces[1]),
			    				Float.parseFloat(pieces[2]));
			    	}
			    	else if (line.indexOf("guid") > -1) {
			    		state.playerGUID = line.replace("guid", "");
			    	}
			    	else if (line.indexOf("stage") > -1) {
			    		state.playerStage = Integer.parseInt(line.replace("stage", ""));
			    	}
			    	else if (line.indexOf("score") > -1) {
			    		state.score = Integer.parseInt(line.replace("score", ""));
			    	}
			    	else if (line.indexOf("health") > -1) {
			    		state.health = Integer.parseInt(line.replace("health", ""));
			    	}
			    	else if (line.indexOf("gEmS") > -1) {
			    		state.gems = Integer.parseInt(line.replace("gEmS",""));
			    	}
			    	else if (line.indexOf("DELTA") > -1) {
			    		state.playerDelta = Float.parseFloat(line.replace("DELTA",""));
			    	}
			    	else if (line.indexOf("COlor") > -1) {
			    		line = line.replace("COlor","");
			    		line = line.replace("(", "");
			    		line = line.replace(")", "");
			    		String[] pieces = line.split(",");
			    		state.playerColor = new Color(Integer.parseInt(pieces[0]),
			    				Integer.parseInt(pieces[1]),
			    				Integer.parseInt(pieces[2]));
			    	}
			    }
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return state;
	}
	
	public GameState load() {
		InputStream fis = null;
		BufferedReader br;
		GameState state = new GameState();
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		state = read(br);
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = null;
		fis = null;
		System.out.println("Read and decrypted previous file save.");
		return state;
	}
	
	public boolean exists() {
		try {
			File file = new File(fileName);
			return file.exists();
		}
		catch (Throwable t) {
			return false;
		}
	}
	
	public void delete() {
		try {
			File file = new File(fileName);
			file.delete();
		}
		catch (Throwable t) {
			
		}
	}
}
