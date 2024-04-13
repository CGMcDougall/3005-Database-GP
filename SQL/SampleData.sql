-- Sample data for UserTable
INSERT INTO UserTable (user_name, password, user_type) VALUES
('member1', '123', 1), -- Member
('member2', '123', 1), -- Member
('member3', '123', 1), -- Member
('admin1', '123', 3),  -- Admin
('trainer1', '123', 2); -- Trainer

-- Sample data for Member
INSERT INTO Member (first_name, last_name, user_name, password, balance) VALUES
('John', 'Doe', 'member1', '123', 500),
('Alice', 'Smith', 'member2', '123', 700),
('Bob', 'Johnson', 'member3', '123', 1000);

-- Sample data for Admin
INSERT INTO Admin (user_name, password, first_name, last_name) VALUES
('admin1', '123', 'Admin', 'One');

-- Sample data for Trainer
INSERT INTO Trainer (first_name, last_name, user_name, password) VALUES
('Trainer', 'One', 'trainer1', '123');

-- Sample data for Schedule
INSERT INTO Schedule (session_id, member_id, room_number, trainer_id, session_date, start_time, end_time) VALUES
(1, 1, 101, 1, '2024-04-13', '08:00', '09:00'),
(2, 2, 102, 1, '2024-04-14', '09:00', '10:00'),
(3, 3, 103, 1, '2024-04-15', '10:00', '11:00');

-- Sample data for Stats
INSERT INTO Stats (member_id, cur_bench_press, cur_deadlift, cur_squat, goal_bench_press, goal_deadlift, goal_squat) VALUES
(1, 100, 150, 120, 150, 200, 160),
(2, 80, 120, 100, 100, 150, 120),
(3, 120, 180, 150, 140, 200, 180);

-- Sample data for Trainer_Schedule
INSERT INTO Trainer_Schedule (trainer_id, monday_start_time, tuesday_start_time, wednesday_start_time, thursday_start_time, friday_start_time, saturday_start_time, sunday_start_time, monday_end_time, tuesday_end_time, wednesday_end_time, thursday_end_time, friday_end_time, saturday_end_time, sunday_end_time) VALUES
(1, '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '11:00', '17:00', '17:30', '18:00', '18:30', '19:00', '19:30', '20:00');

-- Inserting sample data into Equipment
INSERT INTO equipment (maintenance_status, equipment_type)
VALUES
    (1, 'Treadmill'),
    (0, 'Dumbbells'),
    (1, 'Stationary Bike');
