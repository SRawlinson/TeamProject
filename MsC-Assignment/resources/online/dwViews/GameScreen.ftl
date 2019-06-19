<html>
<!-- The game screen uses a simple layout of a minimal number of points where text and buttons appear. These change 
throughout each round, displaying the information relevant ot the player and prompting them the press a button to 
continue - usually the 'gameButton' object but also the various buttons associated with the user selecting an attribute. 
Unfortunately, as we were still learning JavaScript and using the API, the methods associated with each button press are 
rudimentary and occasionally quite repetitive. While we were able to pass information back up to the views via the API, we 
had difficulty passing parameters down the API from the user input. As a result of this individual methods had ot be written
for the user selecting each attribute, and for the game showing the cards and scores of each player - each method was hard 
coded to only correspond to one player/attribute, rather than have one method which could take different arguments. This has 
resulted in a lengthy and often difficult to follow document. With more time, we expect we could have drastically reduced
the functions used, and included more CSS and aesthetic elements to improve the look of the final document. --> 
	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">
		<link rel="stylesheet" href="https://raw.githack.com/IsBeIsBe/MsC-Assignment/master/resources/online/dwViews/toptrumpsCSS.css">


	</head>

   <!-- <body onload="initalize()"> -->
    <h1>Top Trumps</h1>

    <hr />

    <div>
		<a href="http://localhost:7777/toptrumps/">Go Back</a>
	</div>

    <hr />

    <div>
        <button id=myButton onclick="playGame()" value="Play Game"></button>
    </div>
	<script type="text/javascript">
		document.getElementById("myButton").innerHTML = myButton.value;
	</script>

	<div>
		<p id="rounds"></p>
	</div>

    <hr />

    <div>
        <p id="playerCardInfo"></p>
		<button id=SizeButton onclick="selectedSize()">Size</button>
		<button id=RarityButton onclick="selectedRarity()">Rarity</button>
		<button id=TemperButton onclick="selectedTemper()">Good Temper</button>
		<button id=CutenessButton onclick="selectedCute()">Cuteness</button>
		<button id=MischiefButton onclick="selectedMischief()">Mischief Rating</button>

        <p id="playerCardCountInfo"></p>
    </div>
    <script type="text/javascript">
        document.getElementById("playerCardInfo").style.visibility = "hidden";
        document.getElementById("playerCardCountInfo").style.visibility = "hidden";
        document.getElementById("SizeButton").style.visibility = "hidden";
        document.getElementById("RarityButton").style.visibility = "hidden";
        document.getElementById("TemperButton").style.visibility = "hidden";
        document.getElementById("CutenessButton").style.visibility = "hidden";
        document.getElementById("MischiefButton").style.visibility = "hidden";

    </script>


    <div>
        <p id="gameInfo"></p>
    </div>
    <script type="text/javascript">
        document.getElementById("gameInfo").style.visibility = "hidden";
    </script>

	<div>
		<p id="AI1Card"></p>
		<p id="AI2Card"></p>
		<p id="AI3Card"></p>
		<p id="AI4Card"></p>
	</div>
	<script type="text/javascript">
		document.getElementById("AI1Card").style.visibility = "hidden";
		document.getElementById("AI2Card").style.visibility = "hidden";
		document.getElementById("AI3Card").style.visibility = "hidden";
		document.getElementById("AI4Card").style.visibility = "hidden";
	</script>

    <hr />

    <div>
        <button id=gameButton onclick="buttonsFunctions()" value=""></button>
    </div>
	<script type="text/javascript">
		document.getElementById("gameButton").innerHTML = gameButton.value;
	</script>

    <hr />

    <div>
		<p id="humanCardCount"></p>
		<p id="AI1CardCount"></p>
		<p id="AI2CardCount"></p>
		<p id="AI3CardCount"></p>
		<p id="AI4CardCount"></p>
	</div>
	<script type="text/javascript">
		document.getElementById("humanCardCount").style.visibility = "hidden";
		document.getElementById("AI1CardCount").style.visibility = "hidden";
		document.getElementById("AI2CardCount").style.visibility = "hidden";
		document.getElementById("AI3CardCount").style.visibility = "hidden";
		document.getElementById("AI4CardCount").style.visibility = "hidden";
	</script>

	<div>
		<p id="commonPileCount"></p>
	</div>
	<script type="text/javascript">
		document.getElementById("commonPileCount").style.visibility = "hidden";
	</script>

    <script type="text/javascript">

		function playGame() {
	//	document.getElementById("myButton").innerHTML = "Okay!";	 
		document.getElementById("myButton").style.visibility = "hidden";
		startUp();
		getRounds();
		}

		function buttonsFunctions() {

			var buttonValue = document.getElementById("gameButton").getAttribute("value");
			//document.getElementById("gameInfo").innerHTML = buttonValue;

			if (buttonValue === "Okay! Let's see what they choose!") {
				getAICards();
				chooseAttribute();

			} else if (buttonValue === "Okay! Let's see who won!") {
				findWinner();

			} else if (buttonValue === "Great! Let's update the scores!") {
				hideAICards();
				getCardCounts();

			} else if (buttonValue === "Play next Round") {
				//alert("Button Pressed!");
				startNewRound();

			} else if (buttonValue === "Click to Play Again!") {
				startUp();

			} else if (buttonValue === "Click to see final scores!") {
				endTheGame();

			} else if (buttonValue === "Check for a draw too!"){
				showAICards();
				endTheRound();

			} else if (buttonValue === "Click to decide what to choose!") {
				document.getElementById("SizeButton").style.visibility = "visible";
       			document.getElementById("RarityButton").style.visibility = "visible";
        		document.getElementById("TemperButton").style.visibility = "visible";
        		document.getElementById("CutenessButton").style.visibility = "visible";
        		document.getElementById("MischiefButton").style.visibility = "visible";
				document.getElementById("gameInfo").style.visibility = "hidden";
				document.getElementById("gameButton").style.visibility = "hidden";
			}

		}

        function startUp() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/startAndSelect");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
				var responseText = xhr.response;
				playerIndex = JSON.parse(responseText);	
				if (playerIndex === 0) {
					document.getElementById("gameInfo").innerHTML = "It's your turn to play!";
					document.getElementById("gameInfo").style.visibility = "visible";
					document.getElementById("gameButton").value = "Click to decide what to choose!";
					document.getElementById("gameButton").innerHTML = gameButton.value;
				} else {
				document.getElementById("gameInfo").innerHTML = "It's AIPlayer" + playerIndex + "'s turn to play!";
				document.getElementById("gameInfo").style.visibility = "visible";
                document.getElementById("gameButton").value = "Okay! Let's see what they choose!";
				document.getElementById("gameButton").innerHTML = gameButton.value;

				}				
				getCardInfo();

			}

			xhr.send();	
        }

		function chooseAttribute() {
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/chosenAttribute");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				chosenAttribute = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = chosenAttribute;
			//	document.getElementById("chosenAttribute").style.visibility = "visible";
				document.getElementById("gameButton").value = "Okay! Let's see who won!";
				document.getElementById("gameButton").innerHTML = gameButton.value;

			}
			xhr.send();
		}

		function hidePlayerButtons() {
			document.getElementById("SizeButton").style.visibility = "hidden";
        	document.getElementById("RarityButton").style.visibility = "hidden";
        	document.getElementById("TemperButton").style.visibility = "hidden";
       		document.getElementById("CutenessButton").style.visibility = "hidden";
        	document.getElementById("MischiefButton").style.visibility = "hidden";
		}

		function selectedSize() {
			hidePlayerButtons();
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/size");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				chosenAttribute = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = chosenAttribute;
			//	document.getElementById("chosenAttribute").style.visibility = "visible";
				document.getElementById("gameButton").value = "Okay! Let's see who won!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				document.getElementById("gameInfo").style.visibility = "visible";
				document.getElementById("gameButton").style.visibility = "visible";

			}
			xhr.send();
		}

		function selectedRarity() {
			hidePlayerButtons();
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/rarity");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				chosenAttribute = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = chosenAttribute;
			//	document.getElementById("chosenAttribute").style.visibility = "visible";
				document.getElementById("gameButton").value = "Okay! Let's see who won!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				document.getElementById("gameInfo").style.visibility = "visible";
				document.getElementById("gameButton").style.visibility = "visible";

			}
			xhr.send();
		}

		function selectedTemper() {
			hidePlayerButtons();
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/temper");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				chosenAttribute = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = chosenAttribute;
			//	document.getElementById("chosenAttribute").style.visibility = "visible";
				document.getElementById("gameButton").value = "Okay! Let's see who won!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				document.getElementById("gameInfo").style.visibility = "visible";
				document.getElementById("gameButton").style.visibility = "visible";

			}
			xhr.send();
		}

		function selectedCute() {
			hidePlayerButtons();
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/cute");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				chosenAttribute = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = chosenAttribute;
			//	document.getElementById("chosenAttribute").style.visibility = "visible";
				document.getElementById("gameButton").value = "Okay! Let's see who won!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				document.getElementById("gameInfo").style.visibility = "visible";
				document.getElementById("gameButton").style.visibility = "visible";

			}
			xhr.send();
		}

		function selectedMischief() {
			hidePlayerButtons();
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/mischief");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				chosenAttribute = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = chosenAttribute;
			//	document.getElementById("chosenAttribute").style.visibility = "visible";
				document.getElementById("gameButton").value = "Okay! Let's see who won!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				document.getElementById("gameInfo").style.visibility = "visible";
				document.getElementById("gameButton").style.visibility = "visible";

			}
			xhr.send();
		}

		function findWinner() {
			//document.getElementById("attributeButton").style.visibility = "hidden";
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/findWinner");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				roundMessage = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = roundMessage;
				document.getElementById("gameButton").value = "Check for a draw too!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
			}
			xhr.send();
		}

		function endTheRound() {
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/checkForDraws");
			if(!xhr) {
    			alert("CORS not supported");
			}

			xhr.onload = function(e) {
			
				var responseText = xhr.response;
				roundMessage = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = roundMessage;
				if (roundMessage === "There was a draw! All cards have been moved to the common pile! Now let's play again!") {
					commonPileCount();
				} else {
					document.getElementById("commonPileCount").style.visibility = "hidden";
				}

				document.getElementById("gameButton").value = "Great! Let's update the scores!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				
			}
			xhr.send();
		}

		function getCardCounts(){
			//document.getElementById("getCardCountButton").style.visibility = "hidden";
			getCardCount();
			getCardCountforAI1();
			getCardCountforAI2();
			getCardCountforAI3();
			getCardCountforAI4();
			document.getElementById("gameInfo").innerHTML = "Great! Let's play another round!";
            document.getElementById("gameButton").value = "Play next Round";
			document.getElementById("gameButton").innerHTML = gameButton.value;

		}

		function commonPileCount() {
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCommonPile");
			
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
				var responseText = xhr.response;
				commonPileCountValue = JSON.parse(responseText);
				document.getElementById("commonPileCount").innerHTML =  "Common Pile: " + commonPileCountValue;
				document.getElementById("commonPileCount").style.visibility = "visible";
			}
			xhr.send()
		}

		function startNewRound() {

			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/winnerCheck");
			if(!xhr) {
				alert("CORS not supported");
			}
			xhr.onload = function(e) {
				var responseText = xhr.response;
				winnerCheck = JSON.parse(responseText);

				if (winnerCheck === "winner") {
					document.getElementById("gameInfo").innerHTML = "The Game has Ended!";
					document.getElementById("gameButton").value = "Click to see final scores!";
					document.getElementById("gameButton").innerHTML = gameButton.value;

				} else if (winnerCheck === "noWinner"){
					getSelector();
				}
			}
			xhr.send();

		}
			
		function getSelector() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getSelector");
			
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
				var responseText = xhr.response;
				playerIndex = JSON.parse(responseText);

				if (playerIndex === 0) {
					document.getElementById("gameInfo").innerHTML = "It's your turn to play!";
					document.getElementById("gameInfo").style.visibility = "visible";
					document.getElementById("gameButton").value = "Click to decide what to choose!";
					document.getElementById("gameButton").innerHTML = gameButton.value;
				} else {
				document.getElementById("gameInfo").innerHTML = "It's AI Player " + playerIndex + "'s turn to play!";
				document.getElementById("gameInfo").style.visibility = "visible";
                document.getElementById("gameButton").value = "Okay! Let's see what they choose!";
				document.getElementById("gameButton").innerHTML = gameButton.value;

				}					

				getRounds();
				getCardInfo();
					
				
					
			}
				
			xhr.send();	
				
				
		}
		function getCardCount() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCount");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);
			if (count == "0"){
				document.getElementById("humanCardCount").innerHTML = "You are no longer in play!";
			} else {
			document.getElementById("humanCardCount").innerHTML = "Human Player's Card Count: " + count;
			document.getElementById("humanCardCount").style.visibility = "visible";
			}
			}				
			xhr.send();	
		}
		function getCardCountforAI1() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI1");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);
			if (count == "0"){
				document.getElementById("AI1CardCount").innerHTML = "AI Player 1 is no longer in play!";
			} else {
			document.getElementById("AI1CardCount").innerHTML = "AI Player One's Card Count: " + count;
			document.getElementById("AI1CardCount").style.visibility = "visible";
			}
			}				
			xhr.send();	
		}		
		function getCardCountforAI2() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI2");
			
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			if (count == "0"){
				document.getElementById("AI2CardCount").innerHTML = "AI Player 2 is no longer in play!";
			} else {
			document.getElementById("AI2CardCount").innerHTML = "AI Player 2's Card Count: " + count;
			document.getElementById("AI2CardCount").style.visibility = "visible";
			}
			}				
			xhr.send();	
		}	

		function getCardCountforAI3() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI3");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			if (count == "0"){
				document.getElementById("AI3CardCount").innerHTML = "AI Player 3 is no longer in play!";
			} else {
			document.getElementById("AI3CardCount").innerHTML = "AI Player 3's Card Count: " + count;
			document.getElementById("AI3CardCount").style.visibility = "visible";
			}
			}				
			xhr.send();	
		}	
		function getCardCountforAI4() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI4");
			
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			if (count == "0"){
				document.getElementById("AI4CardCount").innerHTML = "AI Player 4 is no longer in play!";
			} else {
			document.getElementById("AI4CardCount").innerHTML = "AI Player 4's Card Count: " + count;
			document.getElementById("AI4CardCount").style.visibility = "visible";
			}
			}				
			xhr.send();	
		}	

		function getAICards() {
			showAI1Card();
			showAI2Card();
			showAI3Card();
			showAI4Card();
		}

		function showAICards() {
			document.getElementById("AI1Card").style.visibility = "visible";
			document.getElementById("AI2Card").style.visibility = "visible";
			document.getElementById("AI3Card").style.visibility = "visible";
			document.getElementById("AI4Card").style.visibility = "visible";

		}

		function hideAICards(){
			document.getElementById("AI1Card").innerHTML = "";
			document.getElementById("AI2Card").innerHTML = "";
			document.getElementById("AI3Card").innerHTML = "";
			document.getElementById("AI4Card").innerHTML = "";

			document.getElementById("AI1Card").style.visibility = "hidden";
			document.getElementById("AI2Card").style.visibility = "hidden";
			document.getElementById("AI3Card").style.visibility = "hidden";
			document.getElementById("AI4Card").style.visibility = "hidden";

		}

		function showAI1Card() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getAI1Card");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					AI1playerCard = JSON.parse(responseText);					
					document.getElementById("AI1Card").innerHTML = "AI Player 1's card was: "+ AI1playerCard;
					
				}
				
				xhr.send();	
				
		}

		function showAI2Card() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getAI2Card");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					AI2playerCard = JSON.parse(responseText);					
					document.getElementById("AI2Card").innerHTML = "AI Player 2's card was: "+ AI2playerCard;
					
				}
				
				xhr.send();	
				
		}

		function showAI3Card() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getAI3Card");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					AI3playerCard = JSON.parse(responseText);					
					document.getElementById("AI3Card").innerHTML = "AI Player 3's card was: "+ AI3playerCard;
					
				}
				
				xhr.send();	
				
		}

		function showAI4Card() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getAI4Card");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					AI4playerCard = JSON.parse(responseText);					
					document.getElementById("AI4Card").innerHTML = "AI Player 4's card was: "+ AI4playerCard;
					
				}
				
				xhr.send();	
				
		}		

            //not sure if this needs an argument?
			function getCardInfo() {
				var humanCardCountValue = (document.getElementById("humanCardCount").getAttribute("innerHTML"));

				if (humanCardCountValue === "You are no longer in play!"){
					document.getElementById("playerCardInfo").innerHTML = "You have no cards left!";
				} else {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCard");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					playerCard = JSON.parse(responseText);					
					document.getElementById("playerCardInfo").innerHTML = "Your cards is: "+ playerCard;
					document.getElementById("playerCardInfo").style.visibility = "visible";

					
					
				}
				
				xhr.send();	
				}
			}

			function getRounds() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getRoundNum");

				if (!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e){
					var responseText = xhr.response;
					roundNum = JSON.parse(responseText);
					document.getElementById("rounds").innerHTML = "Round: " + roundNum;
                    document.getElementById("rounds").style.visibility = "visible";
				}

				xhr.send();
			}

			function endTheGame() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/endGame");

				if (!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e){
					var responseText = xhr.response;
					finalScores = JSON.parse(responseText);
					document.getElementById("gameInfo").innerHTML = finalScores + "Your scores have been inserted into the database!";
					document.getElementById("gameButton").getAttribute.value = "Click to Play Again!";
					document.getElementById("gameButton").innerHTML = gameButton.value;
				}

				xhr.send();
			}

			function createCORSRequest(method, url) {
  				var xhr = new XMLHttpRequest();
  				if ("withCredentials" in xhr) {

    				// Check if the XMLHttpRequest object has a "withCredentials" property.
    				// "withCredentials" only exists on XMLHTTPRequest2 objects.
    				xhr.open(method, url, true);

  				} else if (typeof XDomainRequest != "undefined") {

    				// Otherwise, check if XDomainRequest.
    				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    				xhr = new XDomainRequest();
    				xhr.open(method, url);

 				 } else {

    				// Otherwise, CORS is not supported by the browser.
    				xhr = null;

  				 }
  				 return xhr;
			}
	</script>


    </body>
</html>
