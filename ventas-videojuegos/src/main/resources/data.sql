-- Estos datos de ejemplo solo se insertan si las tablas están vacías
-- (para no duplicarlos cada vez que reinicias la app con la BD persistente).

INSERT INTO videojuegos (titulo, plataforma, genero, precio, stock)
SELECT * FROM (VALUES
    ('The Legend of Zelda: Tears of the Kingdom', 'Switch', 'Aventura', 69.99, 15),
    ('God of War Ragnarok', 'PS5', 'Acción', 59.99, 10),
    ('EA Sports FC 26', 'Xbox Series X', 'Deportes', 49.99, 20),
    ('Elden Ring', 'PC', 'RPG', 39.99, 12),
    ('Spider-Man 2', 'PS5', 'Acción', 54.99, 8)
) AS datos(titulo, plataforma, genero, precio, stock)
WHERE NOT EXISTS (SELECT 1 FROM videojuegos);

INSERT INTO clientes (nombre, email, telefono)
SELECT * FROM (VALUES
    ('Ayleen Martínez', 'ayleen@example.com', '3001234567'),
    ('Adán Pérez', 'adan@example.com', '3007654321')
) AS datos(nombre, email, telefono)
WHERE NOT EXISTS (SELECT 1 FROM clientes);
