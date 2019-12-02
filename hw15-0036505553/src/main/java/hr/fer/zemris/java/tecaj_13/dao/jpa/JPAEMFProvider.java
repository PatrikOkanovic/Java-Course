package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;
/**
 * Implements a provider for JPA {@link EntityManagerFactory}
 * 
 * @author Patrik Okanovic
 *
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;
	
	/**
	 * Returns {@link EntityManagerFactory}
	 * @return
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	/**
	 * Sets the {@link EntityManagerFactory}
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}