/*一般ユーザーテーブル*/
INSERT INTO generalUser (generalUser_id, generalUser_mail, generalUser_passwerd, role)
VALUES('itsuka@xxx.co.jp', 'password', 'いいちゅかはやちょ', 'ROLE_GENERAL');

/*管理者テーブル*/
INSERT INTO adminUser (adminUser_id, generalUser_mail, adminUser_passwerd, role)
VALUES('kanri@xxx.co.jp', 'password', '管理太郎', 'ROLE_ADMIN');
