package com.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class DevApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);

		String serverHost = "localhost"; // Replace with the server's host address
		int serverPort = 8181; // Replace with the server's port

		try (Socket socket = new Socket(serverHost, serverPort);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

			System.out.println("Connected to TCP server at " + serverHost + ":" + serverPort);

			String message = "Hello, server!";
			writer.println(message);
			System.out.println("Sent message to server: " + message);

			String response = reader.readLine();
			System.out.println("Received response from server: " + response);

		} catch (IOException e) {
			System.err.println("Error connecting to TCP server: " + e.getMessage());
			e.printStackTrace();
		}


		try (ServerSocket serverSocket = new ServerSocket(8080)) {
			System.out.println("TCP Server started on port 8080");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

				handleClient(clientSocket);
			}
		} catch (IOException e) {
			System.err.println("Error in TCP Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket clientSocket) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

			String request = reader.readLine();
			System.out.println("Received message from client: " + request);

			// Process the request and generate a response
			String response = "Hello, client! Your message was: " + request;

			// Send the response back to the client
			writer.println(response);
			System.out.println("Sent response to client: " + response);

			// Close the client socket
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Error handling client: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
