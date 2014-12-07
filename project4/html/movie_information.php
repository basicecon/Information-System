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
		
		$flag = 0;
		if ($cursor != null) {
			foreach ($cursor as $obj) {
				$flag = 1;
				$info = '<br>' . $obj['genre'] . '</br>' .
					'<br>' . $obj['releaseYear'] . '</br>' .
					'<br>' . $obj['plotSummary'] . '</br>';
			}
		}
 		if ($cursor1 != null) {
			$info1 = "";
			foreach ($cursor1 as $obj) {
				$flag = 1;
				$info1 .= '<br>' . $obj['castMember'] . '</br>';
			}
		}
		if ($cursor2 != null) {
			$user_number = 0;
			$score = 0;
			foreach ($cursor2 as $obj) {
				$flag = 1;
				$score += $obj['rating'];
				$user_number ++;
			}
			$score = $score / $user_number;
			$info2 = '<br>' . $score . '</br>';
		}
		if ($flag == 0) {
			$message = 'No such movie';
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
