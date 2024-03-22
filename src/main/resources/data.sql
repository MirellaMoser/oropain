insert into USER_TABLE (LOGIN_NAME, PASSWORD_HASH) values ('user', '$2a$10$TI3vcVwae772ZW1W9ZkDPe4x/n/CV/G8Q.dXI9TtHwVa/FjIX3iw2');

insert into SITUATION (PAIN_LEVEL, STRESS_LEVEL, DATE_TIME, TIME_OF_DAY) values (2,1, '2024-03-01 15:36:00','MORNING');
insert into SITUATION (PAIN_LEVEL, STRESS_LEVEL, DATE_TIME, TIME_OF_DAY) values (3,5, '2024-03-02 18:17:00', 'AFTERNOON');

insert into USER_TABLE_SITUATIONS (SITUATIONS_ID, USER_LOGIN_NAME) values (1, 'user');
insert into USER_TABLE_SITUATIONS (SITUATIONS_ID, USER_LOGIN_NAME) values (2, 'user');

insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Lärm', 'Physikalisch-sensorisch', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Hitze, Kälte, Nässe', 'Physikalisch-sensorisch', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Reizüberflutung', 'Physikalisch-sensorisch', true); 

insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Hunger, Durst', 'Körperlich', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Schlafmangel', 'Körperlich', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Verletzung, Schmerz', 'Körperlich', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Starke Funktionseinschränkungen', 'Körperlich', true); 

insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Zeitdruck', 'Leistung und Sozial', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Über- und Unterforderung', 'Leistung und Sozial', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Konkurrenz', 'Leistung und Sozial', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Zwischenmenschliche Konflikte', 'Leistung und Sozial', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Isolation, Trennung', 'Leistung und Sozial', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Verlust von Bezugspersonen, wichtigen Rollen oder dem Arbeitsplatz', 'Life Events', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Plötzliche Einschränkung von Gesundheit und Leistungsfähigkeit', 'Life Events', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Starke Bedrohung der eigenen Sicherheit', 'Life Events', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Ängste', 'Chronische Belastungen', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Rollenkonflikte in Beruf und Familie', 'Chronische Belastungen', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('andauernde Krankheiten, Funktionseinschränkungen oder Behinderungen', 'Chronische Belastungen', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Zeitverlust durch Stau oder Schlange stehen', 'Daily hassles', true); 
insert into STRESSOR (NAME,CATEGORY, IS_DEFAULT) values ('Unzufriedenheit mit dem eigenen Äusseren', 'Daily hassles', true); 

insert into SYMPTOM (NAME, IS_DEFAULT) values ('Kieferschmerzen', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Kiefergeräusche', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Kieferblockaden', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Zahnschmerzen', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Mundbrennen', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Kopfschmerzen', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Migräne', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Nackenschmerzen', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Ohrenschmerzen', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Tinnitus', true); 

insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Selbstkontrolle (bewusstes Vermeiden von Parafunktionen)', true); 
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Entspannungsübungen', true); 
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Massage', true);
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Kieferdehnung', true);
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Wärme- und/oder Kälteumschläge', true);
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Verordnete Medikamente', true);
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Körperliche Bewegung', true);

insert into SITUATION_COUNTER_MEASURES (SITUATION_ID, COUNTER_MEASURES_NAME) values (1, 'Massage');
insert into SITUATION_COUNTER_MEASURES (SITUATION_ID, COUNTER_MEASURES_NAME) values (1, 'Kieferdehnung');
insert into SITUATION_STRESSORS (SITUATION_ID, STRESSORS_NAME) values (1, 'Schlafmangel');
insert into SITUATION_SYMPTOMS (SITUATION_ID, SYMPTOMS_NAME) values (1, 'Migräne');

insert into SITUATION_COUNTER_MEASURES (SITUATION_ID, COUNTER_MEASURES_NAME) values (2, 'Entspannungsübungen');
insert into SITUATION_STRESSORS (SITUATION_ID, STRESSORS_NAME) values (2, 'Konkurrenz');
insert into SITUATION_SYMPTOMS (SITUATION_ID, SYMPTOMS_NAME) values (2, 'Zahnschmerzen');
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Zungenbrennen', false); 
insert into SITUATION_SYMPTOMS (SITUATION_ID, SYMPTOMS_NAME) values (2, 'Zungenbrennen');

