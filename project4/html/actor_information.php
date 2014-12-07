<?php
	session_start();
	try {
		$conn = new Mongo('localhost');
		$db = $conn->test;
		$collection = $db->items;
		
		$criteria = array('actorName' => $_SESSION['search_item']);
		$criteria1 = array('castMember' => $_SESSION['search_item']);
		$criteria2 = array('actor' => $_SESSION['search_item']);
		
		$message = $_SESSION['search_item'];
		$cursor = $collection->find($criteria);
		$cursor1 = $collection->find($criteria1);
		$cursor2 = $collection->find($criteria2);
		
		if ($cursor == null && $cursor1 == null && $cursor2 == null) {
			$message = 'No such actor';
		}

		if ($cursor != null) {
			foreach ($cursor as $obj) {
				$info = '<br>' . $obj['birthdate'] . '</br>';	
			}
		}
		if ($cursor1 != null) {
			$info1 = "";
			foreach ($cursor1 as $obj) {
				$info1 .= '<br>' . $obj['movie'] . '</br>';
			}
		}
		if ($cursor2 != null) {
			$info2 = "";
			foreach ($cursor2 as $obj) {
				$info2 .= '<p><img src="/images/' . $obj['fileName'] 
					. '"' . ' alt=""></p>';
			}
		}	
	} catch (Exception $e) {
		$message = 'error';
	}
?>
<html>
  <head>
    <title>Actor Information</title>
  </head>
  <body>
    <p><?php
   	echo $message;
	echo $info;
	echo $info1;    
	?>
    </p>
       <?php
	echo $info2;
	?>
  </body>
</html>
