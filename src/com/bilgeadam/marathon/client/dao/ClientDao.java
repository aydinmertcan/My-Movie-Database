package com.bilgeadam.marathon.client.dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.bilgeadam.marathon.client.dto.ActorDto;

public class ClientDao {
	private static ClientDao instance = null;
	
	private ClientDao() {
		super();
	}


	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}


	public ActorDto requestActorName(String actorName) {
		ActorDto obj = null;
		try (Socket socket = new Socket("localhost", 4711)) {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(actorName); // Server'a Tom Cruz ismi yollandı.
			
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			obj = (ActorDto) input.readObject(); // Server'dan cevap bekleniyor.
			
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Server a bağlanamadı. hata: " + e.getMessage());
			return null;
		}
		return obj;
	}
}
