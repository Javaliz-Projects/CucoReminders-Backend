INSERT INTO tb_role (authority) VALUES ('ROLE_USER');
INSERT INTO tb_user (full_Name,password) VALUES ('Ney','$2a$10$MDUNmQJkt4pVK8bp9J9un.dKIwkXiMUC69ailvoUZbcpboi/GSp96');
INSERT INTO tb_user_role(user_id,role_id) VALUES (1,1);
INSERT INTO tb_time_schedule(due_Date,criado_Em,atualizado_Em) VALUES ('2022-12-02T09:00:00',NOW(),null);
INSERT INTO tb_time_schedule(due_Date,criado_Em,atualizado_Em) VALUES ('2022-12-02T14:00:00',NOW(),null);
INSERT INTO tb_time_schedule(due_Date,criado_Em,atualizado_Em) VALUES ('2022-12-02T20:00:00',NOW(),null);
INSERT INTO tb_lembrete(titulo,mensagem,user_id,time_schedule_id) VALUES ('Prova de Matematica','Chegar mais cedo para revisar trignometria.',1,1);
INSERT INTO tb_lembrete(titulo,mensagem,user_id,time_schedule_id) VALUES ('Aula de Piano','Aula remota com o Prof.Messi',1,2);
INSERT INTO tb_lembrete(titulo,mensagem,user_id,time_schedule_id) VALUES ('Assistir o jogo do Brasil','Ligar a tv as 20h na Globo',1,3);