package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto.DiceRollDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto.PlayerDTO;

public interface PlayerService {
	
	public List <PlayerDTO> getAllPlayers();
	public PlayerDTO getPlayerById(int id);
	public void save(PlayerDTO playerDTO);
	public void update(PlayerDTO playerDTO);
	public void update(PlayerDTO playerDTO, DiceRollDTO diceRollDTO);
	public void delete(int id);
	public PlayerDTO getWinner();
	public PlayerDTO getLoser();
	public float getTotalAverage();
	public boolean findPlayerByName(String name);
	public void restartAverage(PlayerDTO playerDTO);
	public void deleteAllPlaysByPlayer(PlayerDTO playerDTO);
}
