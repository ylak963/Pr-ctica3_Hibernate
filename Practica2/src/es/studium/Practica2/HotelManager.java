package es.studium.Practica2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HotelManager 
{
public static void main(String[] args) 
	{
		SessionFactory sessionFactory = new Configuration().addAnnotatedClass(ClientePOJO.class).configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		int id = ClientePersistencia.createCliente("María José", "Martínez", "mjmartinez@grupostudium.com", "12345678Z",
				"Studium2020");
		System.out.println(ClientePersistencia.readCliente(id , "apellidosCliente"));
 
		ClientePersistencia.updateCliente(id, "apellidosCliente", "Martínez Navas");
		System.out.println(ClientePersistencia.readCliente(id, "apellidosCliente"));
	                                                                     
		System.out.println(ClientePersistencia.readCliente(id, "apellidosCliente"));
		ClientePersistencia.deleteCliente(id);                               
		                                                                     
	}  
}
