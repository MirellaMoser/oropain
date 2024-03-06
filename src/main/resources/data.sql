insert into USER_TABLE (LOGIN_NAME, PASSWORD_HASH) values ('user', '$2a$10$TI3vcVwae772ZW1W9ZkDPe4x/n/CV/G8Q.dXI9TtHwVa/FjIX3iw2');

insert into SITUATION (PAIN_LEVEL, STRESS_LEVEL, DATE_TIME) values (0,0, '2024-03-01 15:36:00');
insert into SITUATION (PAIN_LEVEL, STRESS_LEVEL, DATE_TIME) values (0,0, '2024-03-02 18:17:00');

insert into USER_TABLE_SITUATIONS (SITUATIONS_ID, USER_LOGIN_NAME) values (1, 'user');
insert into USER_TABLE_SITUATIONS (SITUATIONS_ID, USER_LOGIN_NAME) values (2, 'user');

insert into STRESSOR (NAME, IS_DEFAULT) values ('Arbeit', true); 
insert into STRESSOR (NAME, IS_DEFAULT) values ('Beziehung', true); 
insert into STRESSOR (NAME, IS_DEFAULT) values ('Schmerz', true); 

insert into SYMPTOM (NAME, IS_DEFAULT) values ('Migräne', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Nackenschmerzen', true); 
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Zahnschmerzen', true); 

insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Massage', true); 
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Entspannungsübungen', true); 
insert into COUNTER_MEASURE (NAME, IS_DEFAULT) values ('Bewegung', true);

insert into SITUATION_COUNTER_MEASURES (SITUATION_ID, COUNTER_MEASURES_NAME) values (1, 'Massage');
insert into SITUATION_COUNTER_MEASURES (SITUATION_ID, COUNTER_MEASURES_NAME) values (1, 'Bewegung');
insert into SITUATION_STRESSORS (SITUATION_ID, STRESSORS_NAME) values (1, 'Beziehung');
insert into SITUATION_SYMPTOMS (SITUATION_ID, SYMPTOMS_NAME) values (1, 'Migräne');

insert into SITUATION_COUNTER_MEASURES (SITUATION_ID, COUNTER_MEASURES_NAME) values (2, 'Bewegung');
insert into SITUATION_STRESSORS (SITUATION_ID, STRESSORS_NAME) values (2, 'Arbeit');
insert into SITUATION_SYMPTOMS (SITUATION_ID, SYMPTOMS_NAME) values (2, 'Zahnschmerzen');
insert into SYMPTOM (NAME, IS_DEFAULT) values ('Zungenbrennen', false); 
insert into SITUATION_SYMPTOMS (SITUATION_ID, SYMPTOMS_NAME) values (2, 'Zungenbrennen');

