package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "pictures")
public class Picture {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nasa_id")
  private Long nasaId;

  @Column(name = "img_src")
  private String imgSrc;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDate createdAt;

  @ManyToOne
  @JoinColumn(name = "camera_id")
  private Camera camera;
}
