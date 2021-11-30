package com.bilgeadam.marathon.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.bilgeadam.marathon.client.dao.ActorDao;
import com.bilgeadam.marathon.client.dto.ActorDto;

public class Server {
	public static void main(String[] args) {
		startServer();
	}
	public static void startServer() {
			try (ServerSocket server = new ServerSocket(4711)) {
			System.out.println("Server is listening...");
			Socket socket = server.accept();
			System.out.println("A Client connected to the server");
			DataInputStream input = new DataInputStream(socket.getInputStream());
			String inputMessage = "";
			while (true) {
				inputMessage = input.readUTF().toString();
					if (inputMessage != null && !inputMessage.isEmpty()) {
						ActorDao actorDao = new ActorDao();
						ActorDto actorDto = new ActorDto();
						actorDto.setPrimaryName(inputMessage);
						ActorDto temp = actorDao.read(actorDto);
						
						ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
						out.writeObject(temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

