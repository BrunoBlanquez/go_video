package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRentRepository extends JpaRepository<Emprestimo, Long> {

    @Query(value = "SELECT * FROM emprestimo WHERE data_fim is not null AND usuario_id = :idUsuario", nativeQuery = true)
    Page<Emprestimo> findEncerradosByUsuario(Long idUsuario, Pageable paginacao);

    @Query(value = "SELECT * FROM emprestimo WHERE data_fim is null AND usuario_id = :idUsuario", nativeQuery = true)
    Page<Emprestimo> findVigentesByUsuario(Long idUsuario, Pageable paginacao);

    Page<Emprestimo> findByDataFimIsNotNull(Pageable paginacao);

    Page<Emprestimo> findByDataFimIsNull(Pageable paginacao);

    Page<Emprestimo> findByEquipment(Equipment equipment, Pageable paginacao);

    Page<Emprestimo> findByUsuario(Usuario usuario, Pageable paginacao);

}
