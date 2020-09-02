CREATE TABLE IF NOT EXISTS user (
	userId INT IDENTITY(1, 1) PRIMARY KEY,
	userName VARCHAR(50),
	email VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(50),
	role VARCHAR(50),
	permission BOOLEAN,
	frozen　BOOLEAN,
	requested_at VARCHAR(50)
);
