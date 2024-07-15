insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Cipolla', 'cipolla.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Aglio', 'spicchi', 'aglio.png')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Pomodoro', 'pomodori.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Farina', 'g', 'farina.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Lievito di birra', 'g', 'lievitodibirra.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Zucchero', 'g', 'zucchero.jpg')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Amore di mamma', 'amoredimamma.jpeg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Sale', 'g', 'sale.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Mozzarella', 'g', 'mozzarella.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Burro', 'g', 'burrobellissimo.jpg')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Uova', 'uova.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Lievito per dolci', 'g', 'lievitoperdolci.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Yogurt greco', 'g', 'yogurtgreco.jpeg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Pangrattato', 'g', 'pangrattato.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Passata di pomodoro', 'g', 'salsa-pomodoro.jpg')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Savoiardi', 'savoiardi.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Latte', 'l', 'latte.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Acqua', 'l', 'acqua.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Mascarpone', 'g', 'mascarpone.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Patate', 'g', 'patate.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Olio', 'cucchiai', 'olio-di-oliva.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Parmigiano', 'g', 'parmigiano.png')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Lonza di maiale', 'g', 'lonzadimaiale.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Prosciutto', 'g', 'prosciutto.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Olio di semi di girasole', 'ml', 'oliodisemi.jpeg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Acciughe', 'g', 'acciughe.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Cacao', 'g', 'cacaoamaro.jpg')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Limone', 'limoni.jpg')
insert into ingrediente(id, nome, path_immagine) values(nextval('ingrediente_seq'), 'Peperoncino', 'peperoncino.jpg')
insert into ingrediente(id, nome, unità_di_misura, path_immagine) values(nextval('ingrediente_seq'), 'Riso carnaroli', 'g', 'risocarnaroli.jpg')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1965-10-8', 'Cracco', 'Carlo', 'cracco.png')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1962-01-12', 'Barbieri', 'Bruno', 'BrunoBarbieri.jpg')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1975-04-16', 'Cannavacciuolo', 'Antonino', 'antoninocannavacciuolo.jpg')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1963-04-7', 'Locatelli', 'Giorgio', 'giorgiolocatelliimmagineintrovabile.jpg')
insert into cuoco(id, data_nascita, cognome, nome, path_fotografia) values(nextval('cuoco_seq'), '1942-08-29', 'Massari', 'Iginio', 'iginiomassariditino.jpg')
insert into ricetta(id, cuoco_id, descrizione, titolo, tutti_path_delle_immagini) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Carlo'), 'Risotto con acciughe, limone e cacao', 'Risotto alle acciughe', 'mhanz_IA.png,risottinovalerio.png,risottoacciughe1.jpg')
insert into ricetta(id, cuoco_id, descrizione, titolo, tutti_path_delle_immagini) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Carlo'), 'Una delle ricette iconiche di Carlo Cracco, tuorlo fritto dalla realizzazione estremamente difficile dati i tempi di cottura molto precisi', 'Uovo fritto', 'uovofritto1.png,uovofritto2.jpg,uovofritto3.png')
insert into ricetta(id, cuoco_id, descrizione, titolo, tutti_path_delle_immagini) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Bruno'), 'Classici tortellini della tradizione emiliana', 'Tortellini', 'tortellini1.png,tortellini2.jpg,tortellini3.png')
insert into ricetta(id, cuoco_id, descrizione, titolo, tutti_path_delle_immagini) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Antonino'), 'Gnocchi al pomodoro tipici campani, con mozzarella e formaggio grattugiato', 'Gnocchi alla sorrentina', 'gnocchisorrentina1.png,gnocchisorrentina2.jpg,gnocchisorrentina3.jpeg')
insert into ricetta(id, cuoco_id, descrizione, titolo, tutti_path_delle_immagini) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Iginio'), 'Plumcake in diverse versioni, perfetto da servire a colazione o come dessert', 'Plumcake', 'plumquadro.png,plumcione.png,plumcioco.png')
insert into ricetta(id, cuoco_id, descrizione, titolo, tutti_path_delle_immagini) values(nextval('ricetta_seq'), (SELECT id FROM cuoco WHERE nome = 'Iginio'), 'Il dolce italiano per eccellenza', 'Tiramisù goloso', 'tiramisu1.png,tiramisu2.png,tiramisu3.jpg')
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (300, (SELECT id FROM ingrediente WHERE nome='Farina'), (SELECT id FROM ricetta WHERE titolo='Plumcake'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (200, (SELECT id FROM ingrediente WHERE nome='Burro'), (SELECT id FROM ricetta WHERE titolo='Plumcake'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (5, (SELECT id FROM ingrediente WHERE nome='Uova'), (SELECT id FROM ricetta WHERE titolo='Plumcake'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (150, (SELECT id FROM ingrediente WHERE nome='Yogurt greco'), (SELECT id FROM ricetta WHERE titolo='Plumcake'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (4, (SELECT id FROM ingrediente WHERE nome='Sale'), (SELECT id FROM ricetta WHERE titolo='Plumcake'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (15, (SELECT id FROM ingrediente WHERE nome='Lievito per dolci'), (SELECT id FROM ricetta WHERE titolo='Plumcake'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (400, (SELECT id FROM ingrediente WHERE nome='Mascarpone'), (SELECT id FROM ricetta WHERE titolo='Tiramisù goloso'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (100, (SELECT id FROM ingrediente WHERE nome='Zucchero'), (SELECT id FROM ricetta WHERE titolo='Tiramisù goloso'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (100, (SELECT id FROM ingrediente WHERE nome='Cacao'), (SELECT id FROM ricetta WHERE titolo='Tiramisù goloso'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (20, (SELECT id FROM ingrediente WHERE nome='Savoiardi'), (SELECT id FROM ricetta WHERE titolo='Tiramisù goloso'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (600, (SELECT id FROM ingrediente WHERE nome='Passata di pomodoro'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (1000, (SELECT id FROM ingrediente WHERE nome='Patate'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (1, (SELECT id FROM ingrediente WHERE nome='Uova'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (300, (SELECT id FROM ingrediente WHERE nome='Farina'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (1, (SELECT id FROM ingrediente WHERE nome='Aglio'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (10, (SELECT id FROM ingrediente WHERE nome='Sale'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (10, (SELECT id FROM ingrediente WHERE nome='Olio'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (250, (SELECT id FROM ingrediente WHERE nome='Mozzarella'), (SELECT id FROM ricetta WHERE titolo='Gnocchi alla sorrentina'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (300, (SELECT id FROM ingrediente WHERE nome='Farina'), (SELECT id FROM ricetta WHERE titolo='Tortellini'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (3, (SELECT id FROM ingrediente WHERE nome='Uova'), (SELECT id FROM ricetta WHERE titolo='Tortellini'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (100, (SELECT id FROM ingrediente WHERE nome='Lonza di maiale'), (SELECT id FROM ricetta WHERE titolo='Tortellini'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (100, (SELECT id FROM ingrediente WHERE nome='Prosciutto'), (SELECT id FROM ricetta WHERE titolo='Tortellini'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (150, (SELECT id FROM ingrediente WHERE nome='Parmigiano'), (SELECT id FROM ricetta WHERE titolo='Tortellini'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (5, (SELECT id FROM ingrediente WHERE nome='Sale'), (SELECT id FROM ricetta WHERE titolo='Tortellini'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (4, (SELECT id FROM ingrediente WHERE nome='Uova'), (SELECT id FROM ricetta WHERE titolo='Uovo fritto'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (100, (SELECT id FROM ingrediente WHERE nome='Pangrattato'), (SELECT id FROM ricetta WHERE titolo='Uovo fritto'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (5, (SELECT id FROM ingrediente WHERE nome='Sale'), (SELECT id FROM ricetta WHERE titolo='Uovo fritto'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (240, (SELECT id FROM ingrediente WHERE nome='Riso carnaroli'), (SELECT id FROM ricetta WHERE titolo='Risotto alle acciughe'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (50, (SELECT id FROM ingrediente WHERE nome='Acciughe'), (SELECT id FROM ricetta WHERE titolo='Risotto alle acciughe'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (1, (SELECT id FROM ingrediente WHERE nome='Limone'), (SELECT id FROM ricetta WHERE titolo='Risotto alle acciughe'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (50, (SELECT id FROM ingrediente WHERE nome='Cacao'), (SELECT id FROM ricetta WHERE titolo='Risotto alle acciughe'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (50, (SELECT id FROM ingrediente WHERE nome='Mascarpone'), (SELECT id FROM ricetta WHERE titolo='Risotto alle acciughe'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (45, (SELECT id FROM ingrediente WHERE nome='Burro'), (SELECT id FROM ricetta WHERE titolo='Risotto alle acciughe'))
insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) VALUES (1, (SELECT id FROM ingrediente WHERE nome='Peperoncino'), (SELECT id FROM ricetta WHERE titolo='Risotto alle acciughe'))