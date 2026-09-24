-- Estos datos de ejemplo solo se insertan si las tablas están vacías
-- (para no duplicarlos cada vez que reinicias la app con la BD persistente).

INSERT INTO videojuegos (titulo, plataforma, genero, precio, stock, imagen_url)
SELECT * FROM (VALUES
    ('The Legend of Zelda: Tears of the Kingdom', 'Switch', 'Aventura', 69.99, 15, 'https://placehold.co/300x400/1a5276/white?text=Zelda+TOTK'),
    ('God of War Ragnarok', 'PS5', 'Acción', 59.99, 10, 'https://placehold.co/300x400/7b241c/white?text=God+of+War'),
    ('EA Sports FC 26', 'Xbox Series X', 'Deportes', 49.99, 20, 'https://placehold.co/300x400/145a32/white?text=EA+FC+26'),
    ('Elden Ring', 'PC', 'RPG', 39.99, 12, 'https://placehold.co/300x400/4a235a/white?text=Elden+Ring'),
    ('Spider-Man 2', 'PS5', 'Acción', 54.99, 8, 'https://placehold.co/300x400/922b21/white?text=Spider-Man+2')
) AS datos(titulo, plataforma, genero, precio, stock, imagen_url)
WHERE NOT EXISTS (SELECT 1 FROM videojuegos);

INSERT INTO clientes (nombre, email, telefono)
SELECT * FROM (VALUES
    ('Ayleen Martínez', 'ayleen@example.com', '3001234567'),
    ('Adán Pérez', 'adan@example.com', '3007654321')
) AS datos(nombre, email, telefono)
WHERE NOT EXISTS (SELECT 1 FROM clientes);
