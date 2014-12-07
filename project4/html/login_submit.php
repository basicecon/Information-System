<?php
	session_start();
	
	if (isset($_SESSION['username'])) {
		$message = 'You already logged in';
	}
	// check that both username and password has been submitted
	if (!isset($_POST['username'], $_POST['password'])) {
		header('Location:error.php');
		$message = 'Please enter a valid username and password';
	} else {
		try {
			$conn = new Mongo('localhost');
			$db = $conn->test;
			$collection = $db->items;
			$criteria = array(
				'username' => $_POST['username'],
				'password' => $_POST['password'],
				);
			
			$curr_user = $collection->findOne($criteria);
			//print_r($curr_user);
			if ($curr_user != null) {
				$message = 'hello' . ' ' . $curr_user['firstname'];
				$_SESSION['valid_user'] = $curr_user;
				header('Location:welcome.php');
			} else {
				header('Location:error.php');
				$message = 'please register';
			}
			$conn->close();
		} catch (Exception $e) {
			$message = 'error';
		}
	}
?>

<html>
  <head>
    <title> Login </title>
  </head>
  <body>
    <p><?php 
	echo $message;
       ?>
    </p>
  </body>
</html>	
