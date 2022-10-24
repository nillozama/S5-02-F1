package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.model.domain.DiceRoll;

@Repository
public interface DiceRollRepository extends JpaRepository<DiceRoll, Integer>{

	
	@Query(value = "SELECT id FROM tiradas WHERE jugador_id=:id", nativeQuery = true)
	public List<Integer> findPlaysByPlayer(int id);
	
	@Query(value = "SELECT count(tiradas.id) as numero_tiradas from tiradas where jugador_id=:id", nativeQuery = true)
	public int selectDiceRollsPlayer(int id);
	
	@Query(value = "SELECT count(tiradas.id) as numero_tiradas from tiradas where puntuacion=7 and jugador_id=:id", nativeQuery = true)
	public int selectWinnersDiceRollsPlayer(int id);
	
	@Query(value = "SELECT count(id) from tiradas", nativeQuery = true)
	public int selectDiceRolls();
	
	@Query(value = "SELECT count(id) from tiradas where puntuacion=7", nativeQuery = true)
	public int selectWinnersDiceRolls();
	
}