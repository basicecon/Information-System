CREATE	VIEW VIEWA AS
SELECT	a.actor_name, COUNT(c.movie_title) AS num_movie
FROM 	ACTOR a
LEFT JOIN  CAST_MEMBER c
ON 	a.actor_name = c.actor_name
GROUP BY a.actor_name;



CREATE	VIEW VIEWB AS
SELECT 	c.actor_name as names, n.event_name, n.event_year, n.movie_title, n.release_year, n.category
FROM	CAST_MEMBER c
INNER JOIN NOMINATION n
on 	c.movie_title = n.movie_title AND 
	c.release_year = n.release_year AND
	(
	(c.actor_role = 'lead actress' AND n.category = 'best lead actress') OR
	(c.actor_role = 'lead actor' AND n.category = 'best lead actor') OR
	(c.actor_role = 'supporting actress' AND n.category = 'best supporting actress') OR
	(c.actor_role = 'supporting actor' AND n.category = 'best supporting actor')
	)
ORDER BY names ASC;


SELECT * FROM VIEWA;
SELECT * FROM VIEWB;
