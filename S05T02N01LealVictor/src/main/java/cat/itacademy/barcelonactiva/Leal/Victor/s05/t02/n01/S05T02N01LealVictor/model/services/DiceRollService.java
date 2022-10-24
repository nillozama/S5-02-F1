package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.services;

import java.util.List;


import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto.DiceRollDTO;

public interface DiceRollService {
	
	public List <DiceRollDTO> getAllDiceRolls();
	public List<DiceRollDTO> findByPlayer(int id);
	public void saveOrUpdate(DiceRollDTO diceRollDTO);
	public void deleteAllPlaysByPlayer(int id);
	public DiceRollDTO playGame();
	public float getTotalAverage();

}
