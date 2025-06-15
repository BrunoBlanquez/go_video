package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query(value = "SELECT * FROM equipamento WHERE status <> 'INATIVO'", nativeQuery = true)
    Page<Equipment> findAtivos(Pageable paginacao);

    Page<Equipment> findByStatus(StatusEquipment statusEquipment, Pageable paginacao);

}
