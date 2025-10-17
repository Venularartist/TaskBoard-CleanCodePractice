package com.ven.taskboard.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<BoardEntity, UUID> { }
