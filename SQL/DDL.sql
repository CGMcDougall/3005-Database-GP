Create table if not exists UserTable(
user_name varchar(255) PRIMARY KEY, 
password varchar(255) not null,
user_type int not null	
);

Create table if not exists Member(
	member_id serial PRIMARY KEY,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	user_name varchar(255) not null,
	password varchar(255) not null,
	balance int not null
);

Create table if not exists Admin(
	admin_id serial PRIMARY KEY,
	user_name varchar(255) not null,
	password varchar(255) not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null
);

create table if not exists equipment(
	equipment_id serial primary key,
	maintenance_status integer not null,
	equipment_type varchar(255)
);

create table if not exists Trainer(
trainer_id serial primary key,
first_name varchar(255) not null,
last_name varchar(255) not null,
user_name varchar(255) not null,
password varchar(255) not null
);

Create table if not exists Schedule(
	session_id int not null,
	member_id int REFERENCES Member(member_id),
	room_number int not null,
	trainer_id int REFERENCES Trainer(trainer_id),
	session_date date not null,
	start_time time not null,
	end_time time not null,
	PRIMARY KEY (session_id, member_id)
);

create table if not exists Stats (
	member_id integer not null primary key references Member,
cur_bench_press integer not null,
cur_deadlift integer not null,
cur_squat integer not null,
goal_bench_press integer not null,
goal_deadlift integer not null,
goal_squat integer not null
);


Create table if not exists Trainer_Schedule(
	trainer_id int REFERENCES Trainer(trainer_id),
	monday_start_time time,
	tuesday_start_time time,
	wednesday_start_time time,
	thursday_start_time time,
	friday_start_time time,
	saturday_start_time time,
	sunday_start_time time,
	monday_end_time time,
	tuesday_end_time time,
	wednesday_end_time time,
	thursday_end_time time,
	friday_end_time time,
	saturday_end_time time,
	sunday_end_time time
);
