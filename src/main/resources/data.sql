/*一般ユーザーテーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(1, 'いいちゅかはやちょ', 'itsuka@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-9-01 23:25:07');

INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(3, '富田', 'tomita@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-9-01 23:25:07');

/*管理者テーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(2, '管理太郎', 'kanri@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_ADMIN', 'true', 'false', '2020-9-01 23:25:07');

/*契約テーブル1-1*/
INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(1, 170, '09:00', '01:00', '18:00', '2020-9-01','LIM', '2021-8-31', 1); 

/*契約テーブル1-2*/
INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(2, 170, '09:00', '01:00', '18:00', '2021-9-01','KAN', '2022-8-31', 1);

/*契約テーブル2-1*/
INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(3, 170, '09:00', '01:00', '18:00', '2020-9-01','LIM', '2021-8-31', 2); 

/*契約テーブル2-2*/
INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(4, 170, '09:00', '01:00', '18:00', '2021-9-01','KAN', '2022-8-31', 2);


/*仕事詳細テーブル1-1（8月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(1, '2020-8-01', '2020-8-01 09:00:00', '12:00:00', '2020-8-01 17:00:00', 420, 1);

/*仕事詳細テーブル1-2（9月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(2, '2020-9-01', '2020-9-01 09:00:00', '12:00:00', '2020-9-01 17:00:00', 42000, 1);

/*仕事詳細テーブル1-3（9月2日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(3, '2020-9-02', '2020-9-02 09:00:00', '12:00:00', '2020-9-02 17:00:00', 420, 1);

/*仕事詳細テーブル1-4（9月3日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(4, '2020-9-03', '2020-9-03 09:00:00', '12:00:00', '2020-9-03 17:00:00', 420, 1);

/*仕事詳細テーブル1-5（10月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(5, '2020-10-02', '2020-10-02 09:00:00', '12:00:00', '2020-10-02 17:00:00', 420, 1);


/*仕事詳細テーブル2-1（8月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(6, '2020-8-01', '2020-8-01 09:00:00', '12:00:00', '2020-8-01 17:00:00', 420, 2);

/*仕事詳細テーブル2-2（9月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(7, '2020-9-01', '2020-9-01 09:00:00', '12:00:00', '2020-9-01 17:00:00', 42000, 2);

/*仕事詳細テーブル2-3（9月2日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(8, '2020-9-02', '2020-9-02 09:00:00', '12:00:00', '2020-9-02 17:00:00', 420, 2);

/*仕事詳細テーブル2-4（9月3日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(9, '2020-9-03', '2020-9-03 09:00:00', '12:00:00', '2020-9-03 17:00:00', 420, 2);

/*仕事詳細テーブル2-5（10月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId)
VALUES(10, '2020-10-02', '2020-10-02 09:00:00', '12:00:00', '2020-10-02 17:00:00', 420, 2);