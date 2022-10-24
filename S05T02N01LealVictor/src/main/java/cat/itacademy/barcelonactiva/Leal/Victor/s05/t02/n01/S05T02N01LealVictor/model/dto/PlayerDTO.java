package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String userName;
	//@JsonIgnore
	//private List<DiceRoll> diceRolls;
	private float averagePlays;
	@JsonIgnore
	private Date registrationDate;
	
	public PlayerDTO() {

		//diceRolls = new ArrayList<DiceRoll>();

	}
	
	public PlayerDTO(String userName) {
		super();
		this.userName = userName;	
	}

	public PlayerDTO(int id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
		averagePlays = 0;
	}

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

	/*public List<DiceRoll> getDiceRolls() {
		return diceRolls;
	}

	public void setDiceRolls(List<DiceRoll> diceRolls) {
		this.diceRolls = diceRolls;
	}*/

	public float getAveragePlays() {
		return averagePlays;
	}

	public void setAveragePlays(float averagePlays) {
		this.averagePlays = averagePlays;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}
