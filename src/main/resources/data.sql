/*一般ユーザーテーブル*/
INSERT INTO user (userId, userName, email, password, role, permission)
VALUES(1, 'いいちゅかはやちょ', 'itsuka@xxx.co.jp', 'password', 'ROLE_GENERAL', 'false');

/*管理者テーブル*/
INSERT INTO user (userId, userName, email, password, role, permission)
VALUES(2, '管理太郎', 'kanri@xxx.co.jp', 'password', 'ROLE_ADMIN', 'true');
