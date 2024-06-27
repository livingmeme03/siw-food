insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Cipolla')
insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Aglio')
insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Pomodoro')
insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Farina')
insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Lievito di birra')
insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Zucchero')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Amore di mamma', 'amoredimamma.jpeg')
insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Sale')
insert into ingrediente(id, nome) values(nextval('ingrediente_seq'), 'Mozzarella')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Burro', 'burro.jpg')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Uova', 'uova.jpg')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1965-10-8', 'Cracco', 'Carlo', 'cracco.png')
insert into ricetta(id, cuoco_id, descrizione, titolo) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Carlo'), 'Davvero un bellissimo uovo', 'Uovo')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1962-01-12', 'Barbieri', 'Bruno', 'barbieri.jpg')
insert into ricetta(id, cuoco_id, descrizione, titolo) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Carlo'), 'Ottima pizza, 10/10', 'Pizza')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1975-04-16', 'Cannavacciuolo', 'Antonino', 'cannavacciuolo.jpg')
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (4, (SELECT id FROM ingrediente WHERE nome='Uova'), (SELECT id FROM ricetta WHERE titolo='Uovo'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (4, (SELECT id FROM ingrediente WHERE nome='Burro'), (SELECT id FROM ricetta WHERE titolo='Uovo'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (200, (SELECT id FROM ingrediente WHERE nome='Farina'), (SELECT id FROM ricetta WHERE titolo='Pizza'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (30, (SELECT id FROM ingrediente WHERE nome='Pomodoro'), (SELECT id FROM ricetta WHERE titolo='Pizza'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (3, (SELECT id FROM ingrediente WHERE nome='Sale'), (SELECT id FROM ricetta WHERE titolo='Pizza'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (20, (SELECT id FROM ingrediente WHERE nome='Mozzarella'), (SELECT id FROM ricetta WHERE titolo='Pizza'))