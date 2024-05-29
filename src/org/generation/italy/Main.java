package org.generation.italy;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.generation.italy.model.libro;

public class Main {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/biblioteca2";
		Scanner sc = new Scanner(System.in);
		String sql, sql2;
		libro l, libsel;
		String risposta;
		String conferma;

		System.out.println("Tentativo di connessione al db...");

		try (Connection conn = DriverManager.getConnection(url, "root", "")) { // connessione
			System.out.println("connessione riuscita");
			do {
				ArrayList<libro> elencolibri = new ArrayList<libro>();
				sql = "SELECT * FROM libri"; // oppure, in caso di parametri: "SELECT * FROM movimenti WHERE id=?";
				try (PreparedStatement ps = conn.prepareStatement(sql)) { // provo a creare l'istruzione sql
					try (ResultSet rs = ps.executeQuery()) { // il ResultSet mi consente di scorrere il risultato della
																// SELECT una riga alla volta

						// scorro tutte le righe
						while (rs.next()) { // rs.next() restituisce true se c'è ancora qualche riga da leggere, falso
											// altrimenti
							l = new libro();
							l.id = rs.getInt("id"); // recupero il valore della colonna "id"
							l.titolo = rs.getString("titolo");
							l.autore = rs.getString("autore");
							l.genere = rs.getString("genere");
							l.qnt = rs.getInt("qnt");
							elencolibri.add(l);
							// if (l.id > idlib)
							// idlib = l.id;
						}
					}
				}
				System.out.println();
				System.out.println(
						"selezione: (1-inserimento),(2-visualizzazione),(3-modifica),(4-cancellazione)(5-esci)");
				risposta = sc.nextLine();
				while (!(risposta.equals("1") || risposta.equals("2") || risposta.equals("3") || risposta.equals("4")
						|| risposta.equals("5"))) {
					System.out.println("(1-inserimento),(2-visualizzazione),(3-modifica),(4-cancellazione)(5-esci)");
					risposta = sc.nextLine();
				} // check risposta per il ciclo

				switch (risposta) {
				case ("1"):// inserimento
					System.out.println("(Inserimento) Premi B per tornare indietro o qualsiasi tasto per continuare");
					conferma = sc.nextLine().toLowerCase();
					if (conferma.equals("b"))
						break;
					l = new libro();
					// idlib++;
					// l.id = idlib;
					// leggo i dati del movimento

					System.out.print("titolo: ");
					l.titolo = sc.nextLine();

					System.out.print("autore: ");
					l.autore = sc.nextLine();

					System.out.print("genere: ");
					l.genere = sc.nextLine();

					System.out.print("Quantità: ");
					l.qnt = sc.nextInt();
					sc.nextLine();
					// elencolibri.add(l);

					sql = "INSERT INTO libri(titolo,autore,genere,qnt) " + "VALUES(?, ?, ?, ?)"; // inserire

					System.out.println("Tentativo di esecuzione INSERT");

					try (PreparedStatement ps = conn.prepareStatement(sql)) { // provo a creare l'istruzione sql

						// imposto i valori dei parametri
						ps.setString(1, l.titolo);
						ps.setString(2, l.autore);
						ps.setString(3, l.genere);
						ps.setInt(4, l.qnt);

						int righeInteressate = ps.executeUpdate(); // eseguo l'istruzione
						System.out.println("Righe inserite: " + righeInteressate);

					} catch (Exception e) { // catch che gestisce tutti i tipi di eccezione
						// si è verificato un problema. L'oggetto e (di tipo Exception) contiene
						// informazioni sull'errore verificatosi
						System.err.println("Si è verificato un errore: " + e.getMessage());
					}
					break;
				case ("2"):// visualizzazione
					System.out
							.println("(Visualizzazione) Premi B per tornare indietro o qualsiasi tasto per continuare");
					conferma = sc.nextLine().toLowerCase();
					if (conferma.equals("b"))
						break;
					System.out.println(elencolibri.size() + " libri caricati");

					for (int i = 0; i < elencolibri.size(); i++)
						System.out.println(elencolibri.get(i).toString());

					break;
				case ("3"):// modifica
					System.out.println("(Modifica) Premi B per tornare indietro o qualsiasi tasto per continuare");
					conferma = sc.nextLine().toLowerCase();
					if (conferma.equals("b"))
						break;

					System.out.println("inserisci id da modificare: ");
					int id = sc.nextInt();
					sc.nextLine();
					System.out.print("titolo: ");
					String titolo = sc.nextLine();

					System.out.print("autore: ");
					String autore = sc.nextLine();

					System.out.print("genere: ");
					String genere = sc.nextLine();

					System.out.print("Quantità: ");
					int qnt = sc.nextInt();
					sc.nextLine();

					sql = "UPDATE libri SET titolo = ?,autore = ?,genere = ?,qnt = ? WHERE libri.id = ?";
					System.out.println("Tentativo di esecuzione UPDATE");

					try (PreparedStatement ps = conn.prepareStatement(sql)) { // provo a creare l'istruzione sql

						// imposto i valori dei parametri
						ps.setString(1, titolo);
						ps.setString(2, autore);
						ps.setString(3, genere);
						ps.setInt(4, qnt);
						ps.setInt(5, id);

						int righeInteressate = ps.executeUpdate(); // eseguo l'istruzione
						System.out.println("Righe inserite: " + righeInteressate);

					} catch (Exception e) { // catch che gestisce tutti i tipi di eccezione
						// si è verificato un problema. L'oggetto e (di tipo Exception) contiene
						// informazioni sull'errore verificatosi
						System.err.println("Si è verificato un errore: " + e.getMessage());
					}

					break;
				case ("4"): // cancellazione
					System.out.println("(Cancellazione) Premi B per tornare indietro o qualsiasi tasto per continuare");
					conferma = sc.nextLine().toLowerCase();
					if (conferma.equals("b"))
						break;

					System.out.println("inserisci id da cancellare");
					int libCanc = sc.nextInt();
					sc.nextLine();

					sql = "DELETE FROM libri WHERE id=?";
					sql2 = "SELECT * FROM libri WHERE id=?";

					System.out.println("Tentativo di esecuzione DELETE");

					try (PreparedStatement ps = conn.prepareStatement(sql)) { // provo a creare l'istruzione sql

						// imposto i valori dei parametri
						ps.setInt(1, libCanc);

						try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
							ps2.setInt(1, libCanc);
							try (ResultSet rs = ps2.executeQuery()) {
								while (rs.next()) {
									libsel = new libro();
									libsel.id = rs.getInt("id"); // recupero il valore della colonna "id"
									libsel.titolo = rs.getString("titolo");
									libsel.autore = rs.getString("autore");
									libsel.genere = rs.getString("genere");
									libsel.qnt = rs.getInt("qnt");
									System.out.println("Sicuro di volere cancellare : \n" + libsel.toString());
								}

							}
						}
						System.out.println("Premi 'si' per confermare");
						conferma = sc.nextLine().toLowerCase();
						if (conferma.equals("si")) {

							int righeInteressate = ps.executeUpdate(); // eseguo l'istruzione
							System.out.println("Righe cancellate: " + righeInteressate);
						} else
							break;

					} catch (Exception e) { // catch che gestisce tutti i tipi di eccezione
						// si è verificato un problema. L'oggetto e (di tipo Exception) contiene
						// informazioni sull'errore verificatosi
						System.err.println("Si è verificato un errore: " + e.getMessage());
					}

					break;
				case ("5"):
					break;
				}
			} while (!(risposta.equals("5")));// se uguale 4 chiudi ciclo
			System.out.println("Arrivederci");
			sc.close();

		} catch (Exception e) { // catch che gestisce tutti i tipi di eccezione
			// si è verificato un problema. L'oggetto e (di tipo Exception) contiene
			// informazioni sull'errore verificatosi
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}

	}// fine main

}// fine class
