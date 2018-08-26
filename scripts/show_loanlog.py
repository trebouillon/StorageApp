#!/usr/bin/python

import socket
import json
import cgitb
cgitb.enable()

response = """Content-Type: text/html
	<!doctype html>
	<html>
	<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=0.6, user-scalable=no">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="../src/CSS/styles.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<title>aktueller Standort der Objekte</title>
	</head>

	<body class="default">
	  <header_left class="default" >
		  <a href="index.html" rel="home"><img align="center" src="../src/img/logo-hzb_228x96.gif" height="63"></a>
	  </header_left>
	  <header_mid class="default">
		  <h1><i>SRF-Storage</i></h1>
	  </header_mid>
	  <header_right>
		  <h1><i><font color="#becd00">SRF</font></i></h1>
	  </header_right>
	  <main_left>
		
	  </main_left>
	  <main_mid id="main" class="main_mid" role="main">
	  """

try:
	response += """
	Try Läuft
	"""
	soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	response += """Debug Point Connect"""
	soc.connect(("134.30.10.176", 28088))
	response += """Debug Point Connected"""
	clients_input = input("loanlog")
	soc.send(clients_input.encode("utf8")) # we must encode the string to bytes  
	result_bytes = soc.recv(4096) # the number means how the response can be in bytes  
	result_string = result_bytes.decode("utf8") # the return will be in bytes, so decode
	
	Liste = json.load(result_string)
	response += """
	{0}
	Liste?
	""".Liste
	response +="""
		<table>
			<tr>
				<th>Eintrag</th>
				<th>Objekt</th>
				<th>Nutzer</th>
				<th>Ablage</th>
				<th>Zeitpunkt</th>
			</tr>
			"""

	for lines in Liste:
		Eintrag = Liste['ID']
		Objekt = Liste['item']
		Nutzer = Liste['nutzer']
		Ablage = Liste['ablage']
		Zeitpunkt = Liste['Zeitpunkt']
		
		response +="""
			<tr>
				<td>{0}</td>
				<td>{1}</td>
				<td>{2}</td>
				<td>{3}</td>
				<td>{4}</td>
			</tr>""".format(Eintrag, Objekt, Nutzer, Ablage, Zeitpunkt)
		
	response += """
			</table>
			"""
except:
	print('Web-Socket oder Datenbank reagiert nicht')
response += """
		  </main>
		  <main_right>
			
		  </main_right>
		  <footer_le class="default">
			 
		  </footer_le>
		  
		  <footer_rl class="default">	
			<!--<a href="files/html/produkt.html"><h3 text="#000000">Weiter</h3></a>-->
		  </footer_rl>
		  
		</body>

		<!-- Scripts -->
		<!-- verlinkt -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

		<!-- eigene -->
		<script src="files/js/main.js"></script>
		</html>"""
print(response % vars())
