INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(1, 'いいちゅかはやちょ', 'itsuka@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-09-01 23:25:07');

INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(3, '富田', 'tomita@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-09-01 23:25:07');

INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(2, '管理太郎', 'kanri@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_ADMIN', 'true', 'false', '2020-09-01 23:25:07');


INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(1, 170, '09:00', '01:00', '18:00', '2020-08-01','LIM', '2021-03-31', 1); 

INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(2, 170, '09:00', '01:00', '18:00', '2021-09-01','KAN', '2022-08-31', 1);

INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(3, 170, '07:00', '01:00', '18:00', '2020-09-01','YUE', '2021-08-31', 3); 


INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(1, 2020, 8, 'false', 'false',1);

INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(2, 2020, 9, 'true', 'false',1);
 
INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(3, 2020, 10, 'false', 'true',1);
 
INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(4, 2020, 11, 'true', 'false',1);

INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(5, 2020, 12, 'true', 'true',1);

INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(6, 2021, 1, 'true', 'true',1);

INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(7, 2021, 2, 'true', 'true',1);
 
INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(8, 2021, 3, 'false', 'false',1);
 
INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
VALUES(9, 2020, 9, 'false', 'true',3);
 
-- 2020-08
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(1, '2020-08-01', '2020-08-01 09:00:00', '12:00:00', '2020-08-01 17:00:00', 420, 1, 1);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(2, '2020-08-05', '2020-08-05 09:00:00', '12:00:00', '2020-08-05 17:00:00', 420, 1, 1);
-- 2020-09
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(3, '2020-09-01', '2020-09-01 09:00:00', '12:00:00', '2020-09-01 17:00:00', 420, 1, 2);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(4, '2020-09-05', '2020-09-05 09:00:00', '12:00:00', '2020-09-05 17:00:00', 420, 1, 2);
-- 2020-10
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(5, '2020-10-01', '2020-10-01 09:00:00', '12:00:00', '2020-10-01 17:00:00', 420, 1, 3);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(6, '2020-10-05', '2020-10-05 09:00:00', '12:00:00', '2020-10-05 17:00:00', 420, 1, 3);
-- 2020-11
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(7, '2020-11-01', '2020-11-01 09:00:00', '12:00:00', '2020-11-01 17:00:00', 420, 1, 4);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(8, '2020-11-05', '2020-11-05 09:00:00', '12:00:00', '2020-11-05 17:00:00', 420, 1, 4);
-- 2020-12
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(9, '2020-12-01', '2020-12-01 09:00:00', '12:00:00', '2020-12-01 17:00:00', 4200, 1, 5);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(10, '2020-12-05', '2020-12-05 09:00:00', '12:00:00', '2020-12-05 17:00:00', 4200, 1, 5);
-- 2021-01
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(11, '2021-01-01', '2021-01-01 09:00:00', '12:00:00', '2021-01-01 17:00:00', 420, 1, 6);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(12, '2021-01-05', '2021-01-05 09:00:00', '12:00:00', '2021-01-05 17:00:00', 420, 1, 6);
-- 2021-02
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(13, '2021-02-01', '2021-02-01 09:00:00', '12:00:00', '2021-02-01 17:00:00', 420, 1, 7);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(14, '2021-02-05', '2021-02-05 09:00:00', '12:00:00', '2021-02-05 17:00:00', 420, 1, 7);
-- 2021-03
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(15, '2021-03-01', '2021-03-01 09:00:00', '12:00:00', '2021-03-01 17:00:00', 420, 1, 8);
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(16, '2021-03-05', '2021-03-01 09:00:00', '12:00:00', '2021-03-05 17:00:00', 420, 1, 8);