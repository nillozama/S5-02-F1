package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto.DiceRollDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.services.DiceRollServiceImpl;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.services.PlayerServiceImpl;

//Notación para indicar que es un controlador de tipo Rest
@RestController
//Notación para indicar el contexto de nuestros endpoint
@RequestMapping("/players")
public class GameControllerImpl implements GameController {

	// Inyección de dependencias
	@Autowired
	private PlayerServiceImpl playerService;
	@Autowired
	private DiceRollServiceImpl diceRollService;

	@Override
	@PostMapping()
	public ResponseEntity<String> addPlayer(@RequestParam(defaultValue = "ANÒNIM") String name) { // crea un jugador/a

		ResponseEntity<String> responseEntity;
		
		//String cadenaNormalize = Normalizer.normalize(name, Normalizer.Form.NFD);   
		//String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");

		if (name.equalsIgnoreCase("ANÒNIM") || !playerService.findPlayerByName(name)) {

			PlayerDTO playerDTO = new PlayerDTO(name);
			try {
				playerService.save(playerDTO);
				responseEntity = new ResponseEntity<>("S'ha creat el jugador " + name, HttpStatus.CREATED);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		else {

			responseEntity = new ResponseEntity<>("Aquest nom d'usuari ja existeix.", HttpStatus.IM_USED);
		}

		return responseEntity;
	}
	
	@Override
	@PutMapping()
	public ResponseEntity<String> updatePlayer(@RequestBody PlayerDTO playerDTO) { //modifica el nom del jugador/a

		ResponseEntity<String> responseEntity;
		String name=playerDTO.getUserName();

		try{
			playerService.getPlayerById(playerDTO.getId());
			
			if (name.equalsIgnoreCase("ANÒNIM") || !playerService.findPlayerByName(name)) {

				playerService.update(playerDTO);
				
				responseEntity = new ResponseEntity<>("S'ha modificat el nom a " +name , HttpStatus.OK);
			}
			else {

				responseEntity = new ResponseEntity<>("Aquest nom d'usuari ja existeix.", HttpStatus.IM_USED);
			}
			

		}
		catch (Exception e) {

			responseEntity = new ResponseEntity<>("No hi ha un usuari amb aquest id.",HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@Override
	@PostMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> playGame(@PathVariable("id") int idPlayer) { // un jugador/a específic realitza una
																				// tirada dels daus

		ResponseEntity<String> responseEntity;

		try {
			playerService.getPlayerById(idPlayer);

			PlayerDTO playerDTO = playerService.getPlayerById(idPlayer);
			//DiceRollDTO diceRollDTO = new DiceRollDTO(playerDTO);
			DiceRollDTO diceRollDTO=diceRollService.playGame(playerDTO);

			try {
				diceRollService.saveOrUpdate(diceRollDTO);
				playerService.update(playerDTO, diceRollDTO);

				responseEntity = new ResponseEntity<>(diceRollDTO.generateMessage(), HttpStatus.CREATED);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		catch (Exception e){

			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@Override
	@DeleteMapping("/{id}/games")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteDiceRolls(@PathVariable("id") int idPlayerDTO) {//elimina les tirades del jugador
																							
		ResponseEntity<HttpStatus> responseEntity;
		
		try {
			PlayerDTO playerDTO=playerService.getPlayerById(idPlayerDTO);

			try {
				diceRollService.deleteAllPlaysByPlayer(idPlayerDTO);
				playerService.deleteAllPlaysByPlayer(playerDTO);

				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		catch (Exception e) {

			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/")
	public ResponseEntity<List<PlayerDTO>> getAllPlayers() { // retorna el llistat de tots els jugadors/es del sistema
																// amb el seu percentatge mitjà d’èxits

		ResponseEntity<List<PlayerDTO>> responseEntity;

		try {
			List<PlayerDTO> playersDTO = new ArrayList<PlayerDTO>();

			playersDTO = playerService.getAllPlayers();

			if (playersDTO.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			responseEntity = new ResponseEntity<List<PlayerDTO>>(playersDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/{id}/games")
	public ResponseEntity<List<DiceRollDTO>> getAllDiceRolls(@PathVariable("id") int idPlayerDTO) { //retorna el llistat dejugades per unjugador/a

		ResponseEntity<List<DiceRollDTO>> responseEntity;

		try {

			List<DiceRollDTO> playerPlaysDTO = new ArrayList<DiceRollDTO>();

			playerPlaysDTO = diceRollService.findByPlayer(idPlayerDTO);

			responseEntity = new ResponseEntity<List<DiceRollDTO>>(playerPlaysDTO, HttpStatus.OK);

		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking")
	public ResponseEntity<Float> getAverageRanking() { // retorna el ranking mig de tots els jugadors/es del

		ResponseEntity<Float> responseEntity = null;
		float totalAverage = 0;

		totalAverage = playerService.getTotalAverage();

		try {

			responseEntity = new ResponseEntity<Float>(totalAverage, HttpStatus.OK);

		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/rankingList")
	public ResponseEntity<List<PlayerDTO>> getAverageRankingList() { // retorna el ranking mig de tots els jugadors/es

		ResponseEntity<List<PlayerDTO>> responseEntity = null;

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking/loser")
	public ResponseEntity<PlayerDTO> getWorstPlayer() { // retorna el jugador/a amb pitjor percentatge d’èxit

		ResponseEntity<PlayerDTO> responseEntity;

		try {

			PlayerDTO playerDTO = playerService.getLoser();

			responseEntity = new ResponseEntity<PlayerDTO>(playerDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking/winner")
	public ResponseEntity<PlayerDTO> getBestPlayer() { // retorna el jugador/a amb mitjor percentatge d’èxit

		ResponseEntity<PlayerDTO> responseEntity;

		try {

			PlayerDTO playerDTO = playerService.getWinner();

			responseEntity = new ResponseEntity<PlayerDTO>(playerDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
	
	/*
	 * 
	@Override
	@PostMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> playGame(@PathVariable("id") int idPlayer) { // un jugador/a específic realitza una
																				// tirada dels daus

		ResponseEntity<String> responseEntity;

		if (playerService.getPlayerById(idPlayer) != null) {

			PlayerDTO playerDTO = playerService.getPlayerById(idPlayer);
			DiceRollDTO diceRollDTO = new DiceRollDTO(playerDTO);

			try {
				diceRollService.saveOrUpdate(diceRollDTO);
				playerService.update(playerDTO, diceRollDTO);

				responseEntity = new ResponseEntity<>(diceRollDTO.generateMessage(), HttpStatus.CREATED);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		else {

			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
	
	
	@Override
	@DeleteMapping("/{id}/games")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteDiceRolls(@PathVariable("id") int idPlayerDTO) {//elimina les tirades del jugador
																							
		ResponseEntity<HttpStatus> responseEntity;
		PlayerDTO playerDTO=playerService.getPlayerById(idPlayerDTO);

		if ( playerDTO!= null) {

			try {
				diceRollService.deleteAllPlaysByPlayer(idPlayerDTO);
				playerService.deleteAllPlaysByPlayer(playerDTO);

				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		else {

			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
	
	@Override
	@PutMapping()
	public ResponseEntity<String> updatePlayer(@RequestBody PlayerDTO playerDTO) { //modifica el nom del jugador/a

		ResponseEntity<String> responseEntity;
		String name=playerDTO.getUserName();

		if (playerService.getPlayerById(playerDTO.getId()) != null) {
			
			if (name.equalsIgnoreCase("ANÒNIM") || !playerService.findPlayerByName(name)) {

				playerService.update(playerDTO);
				
				responseEntity = new ResponseEntity<>("S'ha modificat el nom a " +name , HttpStatus.OK);
			}
			else {

				responseEntity = new ResponseEntity<>("Aquest nom d'usuari ja existeix.", HttpStatus.IM_USED);
			}
			

		} else {

			responseEntity = new ResponseEntity<>("No hi ha un usuari amb aquest id.",HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}*/
}
