<?php	
	session_start();

	try {
		$conn = new Mongo('localhost');
		$db = $conn->test;
		$collection = $db->items;
		$_SESSION['search_item'] = $_GET['query'];
		
		if ($_SESSION['target'] == 'movie') {
			header("Location:movie_information.php");
			exit();
		} else if ($_SESSION['target'] == 'actor') {
			header("Location:actor_information.php");
			exit();
		} else {
			$message = 'else';
		}
		$conn->close();
	} catch (Exception $e) {
		$message = 'fdfsd error';
	}
?>

<html>
  <body>
    <p><?php
	echo $message;
 	?>
    </p>
  </body>
</html>
