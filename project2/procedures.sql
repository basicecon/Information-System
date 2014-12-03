set serveroutput on size 32000

CREATE OR REPLACE PROCEDURE pro_actor_stats AS
act_name	ACTOR.ACTOR_NAME % TYPE;
cast_name	CAST_MEMBER.ACTOR_NAME % TYPE;
act_num		number;
max_n 		number := 100;
min_n		number := 0;

CURSOR cur IS SELECT ACTOR_NAME FROM ACTOR;
iterate 	cur % ROWTYPE;

BEGIN 
	FOR iterate IN cur LOOP
		BEGIN 
			act_name := iterate.ACTOR_NAME;
			SELECT	COUNT(ACTOR_NAME) INTO act_num
			FROM	CAST_MEMBER 
			WHERE	ACTOR_NAME = act_name 
			GROUP BY ACTOR_NAME;

			EXCEPTION
			WHEN NO_DATA_FOUND THEN
			act_num := 0;
			END;
			IF act_num > max_n THEN
			max_n := act_num;
			END IF;
			IF act_num < min_n THEN
			act_num := min_n;
			END IF;
	END LOOP;

	IF max_n > (min_n+3*mod(max_n-min_n,3)) THEN
	dbms_output.put_line(rpad('Actor name ', 19)||rpad('#Movies ', 9)||'   {'||min_n||'}, {>'||min_n||', <='||(min_n+mod(max_n-min_n,3))||'}, {>'||(min_n+mod(max_n-min_n,3))||', <='||(min_n+2*mod(max_n-min_n,3))||'}, {>'||(min_n+2*mod(max_n-min_n,3))||', <='||(min_n+3*mod(max_n-min_n,3))||'}, {>'||(min_n+3*mod(max_n-min_n,3))||'}');
	ELSE
	dbms_output.put_line(rpad('Actor name ', 19)||rpad('#Movies ', 9)||'   {'||min_n||'}, {>'||min_n||', <='||(min_n+mod(max_n-min_n,3))||'}, {>'||(min_n+mod(max_n-min_n,3))||', <='||(min_n+2*mod(max_n-min_n,3))||'}, {>'||(min_n+2*mod(max_n-min_n,3))||', <='||(min_n+3*mod(max_n-min_n,3))||'}');
	END IF;

	FOR iterate IN cur LOOP
		BEGIN
			act_name := iterate.ACTOR_NAME;
			SELECT	COUNT(ACTOR_NAME) INTO act_num 
			FROM	CAST_MEMBER
			WHERE	ACTOR_NAME = act_name
			GROUP BY ACTOR_NAME;

			EXCEPTION
			WHEN NO_DATA_FOUND THEN
			act_num := 0;
			END;
			IF LENGTH(act_name) >= 15 THEN
				IF act_num = min_n THEN
					dbms_output.put_line(act_name || chr(9) || '       X');
				ELSIF act_num <= (min_n + mod(max_n-min_n, 3)) THEN
					dbms_output.put_line(act_name || chr(9) || '                 X');
				ELSIF act_num <= (min_n + 2*mod(max_n-min_n, 3)) THEN  
					dbms_output.put_line(act_name || chr(9) || '                           X');
				ELSIF act_num <= (min_n + 3*mod(max_n-min_n, 3)) THEN
					dbms_output.put_line(act_name || chr(9) || '                                    X ');
				ELSE
					dbms_output.put_line(act_name || chr(9) || '                                               X');
				END IF;
			ELSE
				IF act_num = min_n THEN
					dbms_output.put_line(act_name || chr(9) || chr(9) || '     X');
				ELSIF act_num <= (min_n + mod(max_n-min_n, 3)) THEN
					dbms_output.put_line(act_name || chr(9) || chr(9) || '                X');
				ELSIF act_num <= (min_n + 2*mod(max_n-min_n, 3)) THEN
					dbms_output.put_line(act_name || chr(9) || chr(9) || '                          X');
				ELSIF act_num <= (min_n + 3*mod(max_n-min_n, 3)) THEN
					dbms_output.put_line(act_name || chr(9) || chr(9) || '                                    X ');
				ELSE
					dbms_output.put_line(act_name || chr(9) || chr(9) || '                                              X');
				END IF;
			END IF;
	END LOOP;
END pro_actor_stats;
/

BEGIN
	pro_actor_stats;
END;
/


CREATE OR REPLACE PROCEDURE pro_add_rating 
(
	userid_in	IN	MOVIE_RATING.USERID % TYPE,
	movie_title_in 	IN	MOVIE_RATING.MOVIE_TITLE % TYPE,
	release_year_in	IN	MOVIE_RATING.RELEASE_YEAR % TYPE,
	rating_in 	IN	MOVIE_RATING.RATING % TYPE
) AS
BEGIN 
	INSERT INTO MOVIE_RATING VALUES(userid_in, movie_title_in, release_year_in, rating_in);
END pro_add_rating;
/


SELECT * FROM MOVIE_RATING;
BEGIN
	pro_add_rating('user1', 'American Hustle', 2013, 7);
END;
/
SELECT * FROM MOVIE_RATING; 

DELETE FROM MOVIE_RATING WHERE userid = 'user1' AND movie_title = 'American Hustle' AND release_year = 2013 AND rating = 7;
