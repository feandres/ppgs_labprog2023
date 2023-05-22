package br.ufma.sppg.repo;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 01882d2a994acab8547edbd4c3315cf71c3d3597
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Orientacao;

<<<<<<< HEAD
public interface OrientacaoRepository extends JpaRepository<Orientacao, Integer> {

=======
public interface OrientacaoRepository extends JpaRepository<Orientacao,Integer> {
    List<Orientacao> findAllById(Integer id);
>>>>>>> 01882d2a994acab8547edbd4c3315cf71c3d3597
}
