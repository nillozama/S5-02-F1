package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DiceRollDTO {
	
	private int id;
	private int dice1;
	private int dice2;
	private int score;
	//@JsonIgnore
	private boolean winningRoll;
	private String message;
	@JsonIgnore
	private PlayerDTO player;
	
	public DiceRollDTO() {
		
	}
	
	public DiceRollDTO(PlayerDTO player) {
		super();
		
		this.player=player;
		dice1=calculateDiceScore();
		dice2=calculateDiceScore();
		score=(dice1+dice2);
		winningRoll=score==7;
		message=generateMessage();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public PlayerDTO getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDTO player) {
		this.player = player;
	}
	
	public int getDice1() {
		return dice1;
	}

	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getWinningRoll() {
		return winningRoll;
	}

	public void setWinningRoll(boolean winningRoll) {
		this.winningRoll = winningRoll;
	}
	
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int calculateDiceScore(){
		
		return  (int) (6*Math.random()+1);
	}
	
	public String generateMessage() {
		
		return winningRoll?"YOU WIN!!":"YOU LOST!!";
	}
}
