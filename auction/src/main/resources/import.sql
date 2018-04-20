insert into category(name) values ('prevoz robe');
insert into category(name) values ('marketing');
insert into category(name) values ('gradjevina');

insert into user(address, city, confirmation_code, confirmed, email, first_name, last_name, password, role, username, zip_code,longitude,latitude,avg_rank) values ('Vuka Karadzica bb', 'Beograd', null, true, 'jankoupp@gmail.com', 'Janko', 'Jankovic', '$2a$10$mJudhgdVugjuWJ7/LIF5Genf3uBRLS5/qvozEfsGGTTEsLYRutUye', 'ROLE_USER', 'janko', '11000',20,40,0)
insert into user(address, city, confirmation_code, confirmed, email, first_name, last_name, password, role, username, zip_code,longitude,latitude,avg_rank) values ('Stepe Stepanovica 23', 'Novi Sad', null, true, 'uppauctionmain@gmail.com', 'Marko', 'Markovic', '$2a$10$mJudhgdVugjuWJ7/LIF5Genf3uBRLS5/qvozEfsGGTTEsLYRutUye', 'ROLE_USER', 'marko', '21000',19,45,0)

insert into user(address, city, confirmation_code, confirmed, email, first_name, last_name, password, role, username, zip_code,longitude,latitude,avg_rank) values ('Vuka Karadzica bb', 'Beograd', null, true, 'danilo.acimovic@yahoo.com', 'pasa', 'mrkalj', '$2a$10$mJudhgdVugjuWJ7/LIF5Genf3uBRLS5/qvozEfsGGTTEsLYRutUye', 'ROLE_FIRM', 'pasa', '11000',20,40,0)
insert into user(address, city, confirmation_code, confirmed, email, first_name, last_name, password, role, username, zip_code,longitude,latitude,avg_rank) values ('Stepe Stepanovica 23', 'Novi Sad', null, true, 'daniloacimovic68@gmail.com', 'Scepan', 'Scekic', '$2a$10$mJudhgdVugjuWJ7/LIF5Genf3uBRLS5/qvozEfsGGTTEsLYRutUye', 'ROLE_FIRM', 'scepan', '21000',19,45,0)
insert into user(address, city, confirmation_code, confirmed, email, first_name, last_name, password, role, username, zip_code,longitude,latitude,avg_rank) values ('Stepe Stepanovica 23', 'Mali Zvornik', null, true, 'uppauctionmain@gmail.com', 'Mika', 'mikic', '$2a$10$mJudhgdVugjuWJ7/LIF5Genf3uBRLS5/qvozEfsGGTTEsLYRutUye', 'ROLE_FIRM', 'mika', '21000',19,44,0)
insert into user(address, city, confirmation_code, confirmed, email, first_name, last_name, password, role, username, zip_code,longitude,latitude,avg_rank) values ('Stepe Stepanovica 23', 'Nis', null, true, 'uppauctionmain@gmail.com', 'Pera', 'Peric', '$2a$10$mJudhgdVugjuWJ7/LIF5Genf3uBRLS5/qvozEfsGGTTEsLYRutUye', 'ROLE_FIRM', 'pera', '21000',21,43,02368)


insert into firm(distance_area, name, category_id,user_id,avg_rank) values (40, 'firma 1', 3,3,0);
insert into firm(distance_area, name, category_id,user_id,avg_rank) values (10, 'firma 2', 3,4,0);
insert into firm(distance_area, name, category_id,user_id,avg_rank) values (10, 'firma 3', 3,5,0);
insert into firm(distance_area, name, category_id,user_id,avg_rank) values (10, 'firma 4', 1,6,0);







insert into firm_ranks(firm_id, ranks) values (1,3);
insert into firm_ranks(firm_id, ranks) values (1,3);
insert into firm_ranks(firm_id, ranks) values (1,4);
insert into firm_ranks(firm_id, ranks) values (1,5);
insert into firm_ranks(firm_id, ranks) values (1,3);
insert into firm_ranks(firm_id, ranks) values (2,4);
insert into firm_ranks(firm_id, ranks) values (2,5);
insert into firm_ranks(firm_id, ranks) values (2,5);
insert into firm_ranks(firm_id, ranks) values (2,5);
insert into firm_ranks(firm_id, ranks) values (4,1);
insert into firm_ranks(firm_id, ranks) values (3,1);
insert into firm_ranks(firm_id, ranks) values (3,4);
insert into firm_ranks(firm_id, ranks) values (4,3);