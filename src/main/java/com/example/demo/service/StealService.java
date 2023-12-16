package com.example.demo.service;

import com.example.demo.entity.Camera;
import com.example.demo.entity.Picture;
import com.example.demo.repository.CameraRepository;
import com.example.demo.repository.PictureRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class StealService {

  private final CameraRepository cameraRepository;
  private final PictureRepository pictureRepository;
  private final RestTemplate restTemplate;
  @Value("${nasa.photos.url}")
  private String NASA_URL;
  @Value("${nasa.api-key}")
  private String NASA_API_KEY;

  public StealService(CameraRepository cameraRepository, PictureRepository pictureRepository,
      RestTemplate restTemplate) {
    this.cameraRepository = cameraRepository;
    this.pictureRepository = pictureRepository;
    this.restTemplate = restTemplate;
  }

  @Transactional
  public void stealPicturesFromNasa(Integer sol) {
    URI uri = UriComponentsBuilder.fromHttpUrl(NASA_URL)
        .queryParam("sol", sol)
        .queryParam("api_key", NASA_API_KEY)
        .build()
        .toUri();

    NasaResult pictures = restTemplate.getForObject(uri, NasaResult.class);
    if (pictures != null) {
      pictures.nasaPictures
          .forEach(this::savePicture);
      return;
    }
    log.info("Pictures not found for sol : \"" + sol + "\"");
  }

  private void savePicture(NasaPicture nasaPicture) {
    Camera camera = findOrCreateCameraIfAbsent(nasaPicture.camera);
    Picture picture = mapFromNasaPicture(nasaPicture);
    picture.setCamera(camera);
    pictureRepository.save(picture);
  }

  private Camera findOrCreateCameraIfAbsent(NasaCamera nasaCamera) {
    Camera camera = cameraRepository.findByNasaId(nasaCamera.id)
        .orElseGet(() -> mapFromNasaCamera(nasaCamera));
    if (camera.getId() == null) {
      cameraRepository.save(camera);
    }
    return camera;
  }

  private Camera mapFromNasaCamera(NasaCamera nasaCamera) {
    Camera camera = new Camera();
    camera.setName(nasaCamera.name);
    camera.setNasaId(nasaCamera.id);
    return camera;
  }

  private Picture mapFromNasaPicture(NasaPicture nasaPicture) {
    Picture picture = new Picture();
    picture.setNasaId(nasaPicture.id);
    picture.setImgSrc(nasaPicture.imgSrc);
    return picture;
  }

  @Setter
  private static class NasaResult {

    @JsonProperty("photos")
    private List<NasaPicture> nasaPictures;
  }

  @Setter
  private static class NasaPicture {

    private Long id;
    @JsonProperty("img_src")
    private String imgSrc;
    private NasaCamera camera;
  }

  @Setter
  private static class NasaCamera {

    private Long id;
    private String name;
  }
}
