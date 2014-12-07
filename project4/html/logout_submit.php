<?php
	session_start();
	try {
		$conn = new Mongo('localhost');
		$db = $conn->test;
 		$collection = $db->items;
		$_SESSION['valid_user'] = null;
		$message = "You've logged out";
		header("Location:welcome.php");
		$conn->close();
	} catch (Exception $e) {
		$message = "error";
	}
	echo $message;

?>
