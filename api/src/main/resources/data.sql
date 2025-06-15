INSERT INTO usuario(criado_em, email, nome, senha, status) VALUES
    (current_timestamp, 'admin@email.com', 'Administrador', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'ATIVO'),
    (current_timestamp, 'colaborador@email.com', 'Colaborador', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'ATIVO'),
    (current_timestamp, 'aline.fagundes@email.com', 'Aline Fagundes', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'INATIVO'),
    (current_timestamp, 'bruno.blanquez@email.com', 'Bruno Blanquez', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'ATIVO'),
    (current_timestamp, 'diego.peixoto@email.com', 'Diego Peixoto', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'INATIVO'),
    (current_timestamp, 'haline.tamaoki@email.com', 'Haline Tamaoki', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'ATIVO'),
    (current_timestamp, 'nayane.maia@email.com', 'Nayane Maia', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'INATIVO'),
    (current_timestamp, 'rafael.fiorini@email.com', 'Rafael Fiorini', '$2a$10$k9xs8MP5YnRS2dHqtUfZe.Am.Z8A2cqxKKPtceIVoeFydAFgSukVi', 'ATIVO');

INSERT INTO perfil(id, perfil) VALUES
    (1, 'ROLE_ADMINISTRADOR'),
    (2, 'ROLE_COLABORADOR');

INSERT INTO usuario_perfis(usuario_id, perfis_id) VALUES
    (1,1),
    (2,2),
    (3,2),
    (4,2),
    (5,2),
    (6,2),
    (7,2),
    (8,2);

INSERT INTO equipment(criado_em, categoria, descricao, marca, modelo, status, url_foto) VALUES
	(current_timestamp, 'Câmera', 'Câmera Fuji X-H2S - FUJIFILM', 'FUJIFILM', 'Fuji X-H2S', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2022/05/sgew_x-h2s_thum-1.png'),
    (current_timestamp, 'Câmera', 'Câmera Fuji X-T30II - FUJIFILM', 'FUJIFILM' , 'Fuji X-T30II', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2021/09/x-t30-ii_thum_bxyg.jpg'),
    (current_timestamp, 'Câmera', 'Câmera GFX 50R - FUJIFILM', 'FUJIFILM', 'GFX 50R', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2018/09/0_thum_gfx50r.jpg'),
    (current_timestamp, 'Câmera', 'Câmera GFX100S - FUJIFILM', 'FUJIFILM', 'GFX100S', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2021/01/gfx100s_thum_egsv.jpg'),
    (current_timestamp, 'Lente', 'Lente XF14mmF2.8 R - FUJINON', 'FUJINON' , 'XF14mmF2.8 R', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2016/10/1_thum_xf14mmf2-8-r.jpg'),
	(current_timestamp, 'Lente', 'Lente MKX18-55mmT2.9 - FUJINON', 'FUJINON' , 'MKX18-55mmT2.9', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2018/02/1_thum_mkx18-55mmt2-9.jpg'),
	(current_timestamp, 'Lente', 'Lente GF110mmF2 - FUJINON', 'FUJINON' , 'GF110mmF2', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2017/04/3_thum_gf63mmf2-8-r-wr.jpg'),
    (current_timestamp, 'Lente', 'Lente MGF250mmF4 - FUJINON', 'FUJINON' , 'GF250mmF4', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2018/04/6_thum_gf250mmf4-r-lm-ois-wr.jpg'),
	(current_timestamp, 'Microfone', 'Microfone MIC-ST1 - FUJIFILM', 'FUJIFILM' , 'MIC-ST1', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2019/10/stereo-microphone_thum.jpg'),
	(current_timestamp, 'Microfone', 'Microfone Lapela RR-100 - FUJIFILM', 'FUJIFILM' , 'RR-100', 'AVAILABLE', 'https://fujifilm-x.com/wp-content/uploads/2019/08/remote-release-rr-100_main01.png');

INSERT INTO emprestimo(criado_em, data_inicio, data_fim, equipment_id, usuario_id) VALUES
    (current_timestamp, current_timestamp, null, 1, 2),
--    (current_timestamp, current_timestamp, current_timestamp, 10, 2),
    (current_timestamp, current_timestamp, current_timestamp, 2, 3),
    (current_timestamp, current_timestamp, current_timestamp, 9, 3),
    (current_timestamp, current_timestamp, null, 3, 4),
    (current_timestamp, current_timestamp, current_timestamp, 8, 4),
    (current_timestamp, current_timestamp, current_timestamp, 4, 5),
    (current_timestamp, current_timestamp, current_timestamp, 7, 5),
    (current_timestamp, current_timestamp, null, 5, 6),
    (current_timestamp, current_timestamp, current_timestamp, 6, 6),
    (current_timestamp, current_timestamp, current_timestamp, 6, 7),
    (current_timestamp, current_timestamp, current_timestamp, 5, 7),
    (current_timestamp, current_timestamp, null, 7, 8),
    (current_timestamp, current_timestamp, current_timestamp, 4, 8);

UPDATE equipament
SET status = 'UNAVAILABLE'
WHERE id = 1 OR id = 3 OR id = 5 OR id = 7;