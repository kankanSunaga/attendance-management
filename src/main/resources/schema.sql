CREATE TABLE IF NOT EXISTS user (
	userId INT IDENTITY(1, 1) PRIMARY KEY,
	userName VARCHAR(50),
	email VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(200),
	role VARCHAR(50),
	permission BOOLEAN,
	frozen BOOLEAN,
	requestedAt VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS contract (
	contractId INT IDENTITY(1, 1) PRIMARY KEY,
	contractTime INT,
	startTime TIME,
	breakTime TIME,
	endTime TIME,
	startDate DATE,
	officeName VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS workTime (
	workTimeId INT IDENTITY(1, 1) PRIMARY KEY,
	workDay DATE,
	startTime TIMESTAMP,
	breakTime TIME,
	endTime TIMESTAMP,
	workTimeMinute INT
);
