DROP SEQUENCE user_id_seq;
DROP SEQUENCE user_session_id_seq;
DROP SEQUENCE favorite_photo_id_seq;
DROP SEQUENCE photo_view_id_seq;
DROP TABLE user_info;
DROP TABLE user_login;
DROP TABLE user_session;
DROP TABLE animal;
DROP TABLE photo;


/************************************
Tables and sequences
************************************/
-- we can do this multiple ways, Have a user login through Email, OR a username, OR both. 
-- display name is for sending messages
CREATE SEQUENCE user_id_seq;
CREATE SEQUENCE user_session_id_seq;
CREATE SEQUENCE favorite_photo_id_seq;
CREATE SEQUENCE photo_view_id_seq;

CREATE TABLE user_info (
    user_id number PRIMARY KEY,
    current_ip varchar2(50) NOT NULL,
    current_ip_location varchar2(50) NOT NULL,
    display_name varchar2(50) UNIQUE
);

CREATE TABLE user_login (
    email varchar2(50) PRIMARY KEY,
    secure_key varchar2(50) NOT NULL,
    salt varchar2(50) NOT NULL,
    user_id number NOT NULL REFERENCES user_info(user_id)
);

CREATE TABLE user_session (
  session_id number PRIMARY KEY,
  user_id number NOT NULL REFERENCES user_info(user_id),
  ip varchar2(50) NOT NULL,
  ip_location varchar2(50) NOT NULL,
  session_start TIMESTAMP,
  session_end TIMESTAMP
);


CREATE TABLE photo (
  photo_id number PRIMARY KEY,
  animal_id REFERENCES animal(animal_id),
  url varchar2(200);
);




commit;