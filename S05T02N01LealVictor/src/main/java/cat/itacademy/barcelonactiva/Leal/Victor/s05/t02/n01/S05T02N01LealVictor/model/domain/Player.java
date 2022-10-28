package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table (name="jugadores")
public class Player {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	@Column (name="id_jugador")
	private int id;
	@Column (name="nombre_de_usuario")
	private String userName;
	//@Column (name="password")
	//private String password;
	@CreationTimestamp
	@Column (name="fecha_registro", updatable = false)
	private Date registrationDate;
	@OneToMany(mappedBy = "player")
	private List<DiceRoll> diceRolls;
	@Column (name="average")
	private float averagePlays;

	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}*/

	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public float getAveragePlays() {
		return averagePlays;
	}
	public void setAveragePlays(float averagePlays) {
		this.averagePlays = averagePlays;
	}
	public List<DiceRoll> getDiceRolls() {
		return diceRolls;
	}
	public void setDiceRolls(List<DiceRoll> diceRolls) {
		this.diceRolls = diceRolls;
	}
}
