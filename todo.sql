CREATE DATABASE todos;

CREATE USER 'todos'@'localhost' identified by '1234';

grant all privileges on todos.* TO 'todos'@'localhost';

flush privileges;


create table User (
	id bigint not null auto_increment,
    email varchar(200) not null,
    password varchar(200) not null,
    nickname varchar(100) not null,
    primary key (id)
);

create table Todo (
	id bigint not null auto_increment,
	complete enum("COMPLETE", "INCOMPLETE"),
    content varchar(200) not null,
    create_at date not null,
    userid bigint not null,
    primary key (id),
    foreign key (userid) references User(id)
);

create table Team (
	id bigint auto_increment not null,
teamname varchar(100) not null,
    teamleader bigint not null,
	primary key (id),
    foreign key (teamleader) references User(id)
);

Create table TeamMember(
	id bigint auto_increment not null,
	team bigint not null,
	userid bigint not null,
	primary key (id),
    foreign key (team) references Team(id),
    foreign key (userid) references User(id)
);

create table TeamTodo (
	id bigint auto_increment not null,
	teamid bigint not null,
    content varchar(200) not null,
    create_at date not null,
    complete enum("COMPLETE", "INCOMPLETE"),
    author bigint not null,
    primary key (id),
    foreign key (teamid) references Team(id),
    foreign key (author) references User(id)
);

/* test data*/
insert into user(email, password, nickname)
values ("test1@test.com", "1234", "user1"); 

INSERT INTO User (email, password, nickname) VALUES
('john.doe@example.com', 'password123', 'JohnDoe'),
('jane.smith@example.com', 'password456', 'JaneSmith'),
('alice.wonderland@example.com', 'password789', 'AliceWonder'),
('bob.builder@example.com', 'builderpass', 'BobBuilder'),
('charlie.brown@example.com', 'password321', 'CharlieBrown'),
('daisy.duck@example.com', 'password654', 'DaisyDuck'),
('evelyn.smith@example.com', 'password987', 'EvelynSmith'),
('frank.jones@example.com', 'password000', 'FrankJones');

INSERT INTO Todo (complete, content, create_at, userid) VALUES
('INCOMPLETE', 'Buy groceries', '2024-09-10', 1),
('COMPLETE', 'Finish project report', '2024-09-08', 2),
('INCOMPLETE', 'Call plumber', '2024-09-09', 3),
('COMPLETE', 'Book flight tickets', '2024-09-07', 4),
('INCOMPLETE', 'Prepare presentation slides', '2024-09-10', 5),
('COMPLETE', 'Submit expense report', '2024-09-09', 6),
('INCOMPLETE', 'Order office supplies', '2024-09-08', 7),
('COMPLETE', 'Schedule team workshop', '2024-09-07', 8);

INSERT INTO Team (teamname, teamleader) VALUES
('Development Team', 1),
('Marketing Team', 2),
('Support Team', 3),
('Operations Team', 4),
('Design Team', 5),
('Finance Team', 6),
('Admin Team', 7),
('HR Team', 8);

INSERT INTO TeamMember (team, userid) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 4),
(4, 1),
(2, 5),
(2, 6),
(3, 7),
(3, 8),
(4, 8),
(4, 5);

INSERT INTO TeamTodo (teamid, content, create_at, complete, author) VALUES
(1, 'Fix login issue', '2024-09-10', 'INCOMPLETE', 1),
(1, 'Update project documentation', '2024-09-09', 'COMPLETE', 2),
(2, 'Plan social media campaign', '2024-09-08', 'INCOMPLETE', 2),
(3, 'Resolve ticket #453', '2024-09-07', 'COMPLETE', 3),
(4, 'Organize team meeting', '2024-09-06', 'INCOMPLETE', 4),
(2, 'Audit quarterly financials', '2024-09-10', 'INCOMPLETE', 5),
(2, 'Reconcile bank statements', '2024-09-09', 'COMPLETE', 6),
(3, 'Update database records', '2024-09-08', 'INCOMPLETE', 7),
(3, 'Setup new user accounts', '2024-09-07', 'COMPLETE', 8),
(4, 'Conduct performance reviews', '2024-09-06', 'INCOMPLETE', 8),
(4, 'Plan team-building event', '2024-09-05', 'COMPLETE', 5);
