package com.example.demo.repository;

import com.example.demo.entity.Camera;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {

  Optional<Camera> findByNasaId(Long nasaId);
}
