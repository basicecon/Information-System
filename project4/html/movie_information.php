<?php
	session_start();
	try {
		$conn = new Mongo('localhost');
		$db = $conn->test;
		$collection = $db->items;
		$criteria = array('title' => $_SESSION['search_item']);
		$criteria1 = array('movie' => $_SESSION['search_item']);
		$criteria2 = array('movieTitle' => $_SESSION['search_item']);

		$message = $_SESSION['search_item'];
		$cursor = $collection->find($criteria);
 		$cursor1 = $collection->find($criteria1);
		$cursor2 = $collection->find($criteria2);
		
		if ($cursor == null && $cursor1 == null && $cursor2 == null) {
			$message = 'No such movie';
		} 
		if ($cursor != null) {
			foreach ($cursor as $obj) {
				$info = '<br>' . $obj['genre'] . '</br>' .
					'<br>' . $obj['releaseYear'] . '</br>' .
					'<br>' . $obj['plotSummary'] . '</br>';
			}
		}
 		if ($cursor1 != null) {
			foreach ($cursor1 as $obj) {
				$info1 = '<br>' . $obj['castMember'] . '</br>';
			}
		}
		if ($cursor2 != null) {
			$user_number = 0;
			$score = 0;
			foreach ($cursor2 as $obj) {
				$score += $obj['rating'];
				$user_number ++;
			}
			$score = $score / $user_number;
			$info2 = '<br>' . $score . '</br>';
		}
		
		$conn->close();
	} catch (Exception $e) {
		$message = "error";
	}
	
?>
<html>
  <head>
    <title>Movie Information</title>
  </head>
  <body>
    <p><?php
	echo $message;
	echo $info;
	echo $info1;
	echo $info2;
       ?>
    </p>
  </body>
</html>
