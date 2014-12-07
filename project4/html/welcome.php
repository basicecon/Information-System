<html>
 <body>
  <?php 
	echo '<p>Navigation</p>';
	include("header.html");
	echo '<p>Welcome to the CS34800 movie database!</p>';
	session_start();
	if (isset($_SESSION['valid_user'])) {
		$curr_user = $_SESSION['valid_user'];
		echo $message = 'Hi' . ' ' . $curr_user['firstname'] . ' ' . $curr_user['lastname'] . '!';
		echo '<p><a href="logout_submit.php">Logout</a></p>';
	} else {
		$message = 'Hi!';
	}
  ?>
 </body>
</html>
