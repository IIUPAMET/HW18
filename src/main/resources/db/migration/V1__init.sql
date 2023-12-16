CREATE TABLE IF NOT EXISTS cameras
(
    id         BIGSERIAL,
    nasa_id    BIGINT,
    NAME       TEXT,
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT PR_cameras PRIMARY KEY (id),
    CONSTRAINT UQ_cameras_nasa_id UNIQUE (nasa_id)
);

CREATE TABLE IF NOT EXISTS pictures
(
    id         BIGSERIAL,
    nasa_id    BIGINT,
    camera_id  BIGINT NOT NULL,
    img_src    TEXT,
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT PR_pictures PRIMARY KEY (id),
    CONSTRAINT FK_pictures_cameras FOREIGN KEY (camera_id) REFERENCES cameras (id),
    CONSTRAINT UQ_pictures_nasa_id UNIQUE (nasa_id)
);