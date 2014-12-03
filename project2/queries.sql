
rem Query 1
SELECT	m.movie_title, m.release_year, count(decode(a.gender, 'Female', 1)) cnt_female_cast
FROM 	MOVIE m
	LEFT JOIN CAST_MEMBER c
		ON (m.movie_title = c.movie_title AND m.release_year = c.release_year)
	JOIN ACTOR a
		ON (a.actor_name = c.actor_name)
GROUP BY m.movie_title, m.release_year;



rem Query 2
SELECT	a.actor_name, a.date_of_birth
FROM 	ACTOR a
WHERE 
	(
	SELECT 	count(c.actor_name)
	FROM	CAST_MEMBER c, NOMINATION n
	WHERE 	((a.actor_name = c.actor_name AND c.movie_title = n.movie_title AND c.release_year = n.release_year AND c.actor_role = 'lead actress' AND n.category = 'best lead actress') 
		  OR (a.actor_name = c.actor_name AND c.movie_title = n.movie_title AND c.release_year = n.release_year AND c.actor_role = 'supporting actress' AND n.category = 'best supporting actress'))
	) >= ALL
	(
	SELECT 	count(cc.actor_name)
	FROM	CAST_MEMBER cc, NOMINATION nn
	WHERE 	((cc.movie_title = nn.movie_title AND cc.release_year = nn.release_year AND cc.actor_role = 'lead actress' AND nn.category = 'best lead actress') 
		  OR (cc.movie_title = nn.movie_title AND cc.release_year = nn.release_year AND cc.actor_role = 'supporting actress' AND nn.category = 'best supporting actress'))
	GROUP BY  cc.actor_name
	);



rem Query 3
SELECT	n.movie_title, n.release_year, COUNT(n.movie_title)
	FROM	NOMINATION n
		WHERE
			(
			SELECT 	COUNT(n1.movie_title)
			FROM 	NOMINATION n1, MOVIE m1 
			WHERE 	(n.movie_title = n1.movie_title AND n.release_year = n1.release_year AND n1.movie_title = m1.movie_title AND n1.release_year = m1.release_year AND m1.genre = 'Comedy')
			) >= ALL
			(
			SELECT 	COUNT(n2.movie_title)
			FROM 	NOMINATION n2, MOVIE m2
			WHERE	(n2.movie_title = m2.movie_title AND n2.release_year = m2.release_year AND m2.genre = 'Comedy')
			GROUP BY n2.movie_title, n2.release_year
			)
		GROUP BY n.movie_title, n.release_year;


rem Query 4
SELECT  DISTINCT c.actor_name
FROM	CAST_MEMBER c
WHERE 	NOT EXISTS (
		SELECT DISTINCT e.venue
		FROM	AWARDS_EVENT e
		WHERE e.venue 
			NOT IN (
				SELECT	ae.venue
				FROM	AWARDS_EVENT ae, NOMINATION n
				WHERE 	(ae.event_name = n.event_name AND
					 ae.event_year = n.event_year AND
					 c.movie_title = n.movie_title AND
					 c.release_year = n.release_year)));



rem Query 5
SELECT	tmpa.movie_title, tmpa.release_year
FROM	(
	SELECT	tmp.movie_title, tmp.release_year 
	FROM	(
		SELECT	m.movie_title, m.release_year, count(*) AS num_won
		FROM 	MOVIE m, NOMINATION n
		WHERE	(m.movie_title = n.movie_title AND m.release_year = n.release_year AND n.won = 'Yes')
		GROUP BY m.movie_title, m.release_year) tmp WHERE tmp.num_won > 2)
	tmpa	FULL JOIN (
	SELECT	tmpp.movie_title, tmpp.release_year
	FROM 	(
		SELECT 	m.movie_title, m.release_year, count(*) AS num_cast
		FROM	MOVIE m, CAST_MEMBER c
		WHERE	(m.movie_title = c.movie_title AND m.release_year = c.release_year)
		GROUP BY m.movie_title, m.release_year) tmpp WHERE tmpp.num_cast > 2)
	tmpb
	ON	tmpa.movie_title = tmpb.movie_title AND tmpa.release_year = tmpb.release_year 
WHERE	(tmpa.movie_title is NULL AND tmpa.release_year is NULL) OR (tmpb.movie_title is NULL AND tmpb.release_year is NULL);



rem Query 6
/*
SELECT	userid, movie_title, release_year, rating FROM (
	SELECT	r.userid, r.movie_title, r.release_year, r.rating
	FROM 	MOVIE_RATING r
INNER JOIN (
	SELECT * FROM (
		SELECT	m.movie_title, m.release_year, avg(rating) AS avg_rating 
		FROM 	MOVIE_RATING m
		GROUP BY m.movie_title, m.release_year 
		ORDER BY avg_rating ASC
		)  
	WHERE ROWNUM = 1
	) tmp 
	ON	r.movie_title = tmp.movie_title AND r.release_year = tmp.release_year 
	ORDER BY rating DESC
	) 
WHERE ROWNUM = 1;
*/
SELECT 	mr.userid, mr.movie_title, mr.release_year, mr.rating
FROM	MOVIE_RATING mr, 
		(SELECT	m.movie_title, m.release_year
			FROM	MOVIE m
			WHERE 	(
				SELECT	AVG(mr.rating)
				FROM 	MOVIE_RATING mr
				WHERE 	m.movie_title = mr.movie_title AND m.release_year = mr.release_year
			) <= ALL
			(
			SELECT	AVG(mr1.rating)
			FROM	MOVIE_RATING mr1, MOVIE m1
			WHERE 	mr1.movie_title = m1.movie_title AND mr1.release_year = m1.release_year
			GROUP BY m.movie_title, m.release_year
			)
		) tmp
WHERE	mr.movie_title = tmp.movie_title AND mr.release_year = tmp.release_year AND mr.rating = 
	(
	SELECT	MAX(mr2.rating) 
	FROM 	MOVIE_RATING mr2
	WHERE 	mr2.movie_title = mr.movie_title AND mr2.release_year = mr.release_year
	);


rem Query 7
SELECT	m.genre, m.movie_title, m.release_year, m.movie_length 
FROM 	MOVIE m
INNER JOIN (
	SELECT	l.genre, max(l.movie_length) as max_length
	FROM	MOVIE l
	GROUP BY l.genre
	) tmp 
ON	m.genre = tmp.genre AND m.movie_length = tmp.max_length 
ORDER BY m.movie_length ASC;
