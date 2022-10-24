package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.domain.DiceRoll;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.domain.Player;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{
	
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public List<PlayerDTO> getAllPlayers() {
		
		List <Player> players=new ArrayList<Player>();
		playerRepository.findAll().forEach(p->players.add(p));
		List<PlayerDTO> playersDTO=new ArrayList<PlayerDTO>();
		
		if (!players.isEmpty()) {
		      
		    players.forEach(x->updateAverageService(x));
		    
		    /*for(Player p:players) {
		    	
		    	System.out.println(p.getAveragePlays());
		    }*/

			players.forEach(p->playersDTO.add(modelMapper.map(p, PlayerDTO.class)));
		}
		
		return playersDTO;
	}
	
	@Override
	public PlayerDTO getPlayerById(int id) {
		
		Player player=playerRepository.findById(id).get();
		updateAverageService(player);
		PlayerDTO playerDTO=modelMapper.map(player,PlayerDTO.class);
		
		return playerDTO;
	}
	
	@Override
	public void save(PlayerDTO playerDTO) {
		
		Player player=modelMapper.map(playerDTO, Player.class);

		playerRepository.save(player);
		
	}
	
	@Override
	public void update(PlayerDTO playerDTO) {
		
		Player player=modelMapper.map(playerDTO, Player.class);

		playerRepository.save(player);
		
	}
	
	@Override
	public void delete(int id) {
		
		playerRepository.deleteById(id);
	}
	
	public void updateAverageService(Player player) {
		
		float average = 0;
		int count = 0;
		
		if (player.getDiceRolls().size() != 0) {
			for (DiceRoll d : player.getDiceRolls()) {

				if (d.getWinningRoll()) {

					count++;
				}
			}
			average = (float) count * 100 / player.getDiceRolls().size();
		}
		System.out.println(player.getId()+" "+average);
		average=Math.round(average*100);
		player.setAveragePlays(average/100);
		
		//playerRepository.updateAverage(player.getId(), average);
	}
	
	@Override
	public PlayerDTO getWinner() {
		
		Player player=playerRepository.findById(playerRepository.selectBestPlayer()).get();
		PlayerDTO playerDTO=modelMapper.map(player,PlayerDTO.class);
		
		return playerDTO;
	}
	
	@Override
	public PlayerDTO getLoser() {
		
		Player player=playerRepository.findById(playerRepository.selectWorstPlayer()).get();
		PlayerDTO playerDTO=modelMapper.map(player,PlayerDTO.class);
		
		return playerDTO;
	}
	
	@Override
	public float getTotalAverage() {
		
		float totalAverage=Math.round(playerRepository.selectTotalAverage()*100);

		return totalAverage/100;
	}
	
	@Override
	public boolean findPlayerByName(String name) {
		
		boolean result=false;
		
		if (playerRepository.getName(name)!=null) {

			result=true;
		}
		System.out.println(result);
		return result;
	}
}
