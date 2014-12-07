<?php
	session_start();
	
	try {
		$conn = new Mongo('localhost');
		$db = $conn->test;
		$collection = $db->items;
		$new_user = array(
			'username' => $_POST['username'],
			'password' => $_POST['password'],
			'firstname' => $_POST['firstname'],
			'lastname' => $_POST['lastname'],
			);
		$collection->insert($new_user);
		//$message = 'welcome' . ' ' . $new_user['firstname'] . '!';
		$_SESSION['valid_user'] = $new_user;
		header("Location:welcome.php");
		$conn->close();
	} catch(Exception $e) {
		$message = 'error';
	}
?>

<html>
  <head>
    <title> Register </title>
  </head>
  <body>
    <p><?php
	echo $message;
	?>
    </p>
  </body>
</html>
