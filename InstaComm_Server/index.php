
    <?php
    $mysqli = new mysqli("host", "username", "password", "database_name"); //TODO insert your database here
    if($mysqli -> connect_error)
        die("Connection Failed" . $mysqli -> connect_error);
    
    $result = $mysqli -> query("SELECT * FROM users");
    $numResults = msqli_num_rows($result);
    $counter = 0;
    if ($result->num_rows > 0) {
    // output data of each row
    echo "{users:[";
    while($row = $result->fetch_assoc()) {
        //Check for last element
        if($counter != $numResults) 
            echo json_encode($row)."," ;
        else
            echo json_encode ($row);
    }echo "]}";
    }
    $mysqli->close();
    ?>
